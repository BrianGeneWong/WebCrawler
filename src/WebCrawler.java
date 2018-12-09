
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;



public class WebCrawler {


    Set<String> valid ;
    Set<String> success;
    Set<String> skipped;
    Set<String> invalid;

    BlockingQueue<Page> queue;



    public WebCrawler(){
        valid = Collections.synchronizedSet(new HashSet<>());
        success= Collections.synchronizedSet(new HashSet<>());
        skipped= Collections.synchronizedSet(new HashSet<>());
        invalid= Collections.synchronizedSet(new HashSet<>());
        queue=new LinkedBlockingQueue<>();
    }


    public void getPageFromFile(String filename){
        Gson gson= new Gson();
        Pages pages= new Pages();
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader("testdata/"+ filename));
            pages= gson.fromJson(br,Pages.class);

        }catch (FileNotFoundException e){
            System.out.println("File Not Found");
        }
        queue= new LinkedBlockingQueue<>(pages.getPages());

    }


    public void crawl() {
        for (Page p : queue) {
            valid.add(p.getAddress());
        }
        //Use some amount of threads to process the queue more quickly.
        ExecutorService exector= Executors.newFixedThreadPool(3);
        while(!queue.isEmpty()){
            exector.submit(new Runnable() {
                @Override
                public void run() {
                    Page p = queue.remove();
                    for (String link : p.getLinks()) {

                        if (!valid.contains(link))
                            invalid.add(link);
                        else {
                            if (success.add(link) == false) {
                                skipped.add(link);
                            }
                        }
                    }
                }
            });

        }
        exector.shutdown();



    }

    public void printOutput(){
        //System.out.println(test+" Output");
        System.out.println("Success:[");
        for(String s : success){
            System.out.println(s);
        }
        System.out.print("]");

        System.out.println("Skipped:[");
        for(String s : skipped){
            System.out.println(s);
        }
        System.out.print("]");

        System.out.println("Invalid:[");
        for(String s : invalid){
            System.out.println(s);
        }
        System.out.print("]");

    }




    public static void main(String[] args){
        String test1="test1.json";
        String test2="test2.json";

        ArrayList<String> list= new ArrayList<>();
        list.add(test1);
        list.add(test2);
        // Pages pages= getPageFromFile(test1);

       // Queue<Page> q  = new LinkedList<>(pages.getPages());

        ExecutorService exec= Executors.newFixedThreadPool(2);

        for(String s:list){
            exec.submit(() -> {
                WebCrawler crawler=new WebCrawler();
                crawler.getPageFromFile(s);
                crawler.crawl();
                crawler.printOutput();

            });
        }
        exec.shutdown();




    }



}
