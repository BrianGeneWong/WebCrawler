
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.*;


public class WebCrawler {


    Set<String> valid ;
    Set<String> success;
    Set<String> skipped;
    Set<String> invalid;

    Page firstPage;
    BlockingQueue<Page> queue;
    Map<String,List<String>> map;


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
        valid= Collections.synchronizedSet(pages.getValidAddress());
        firstPage= queue.remove();
        map= new ConcurrentHashMap<>();
        map.put(firstPage.getAddress(),firstPage.getLinks());
        while(!queue.isEmpty()){
            Page p= queue.remove();
            map.put(p.getAddress(),p.getLinks());
        }

    }



    public void crawl() {
        //start at first page
        //Use some amount of threads to process the queue more quickly.
        BlockingQueue<String> q= new LinkedBlockingQueue<>();
        q.add(firstPage.getAddress());
        ExecutorService exector= Executors.newFixedThreadPool(3);
        while(!q.isEmpty()){
            exector.submit(new Runnable() {
                @Override
                public void run() {
                    String p = q.remove();
                   // System.out.println(p);
                    if(valid.contains(p)) {
                        if(success.add(p))
                            q.addAll(map.get(p));
                        else
                            skipped.add(p);
                    }
                    else{
                        invalid.add(p);
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

        /*
        // Pages pages= getPageFromFile(test1);
        WebCrawler crawler=new WebCrawler();
        crawler.getPageFromFile(test1);
        crawler.crawl();
        crawler.printOutput();
        */


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
