
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class WebCrawler {

   // static Set<String> valid= new HashSet<>();
    static Set<String> success= new HashSet<>();
    static Set<String> skipped= new HashSet<>();
    static Set<String> invalid= new HashSet<>();

    BlockingQueue<String> queue= new LinkedBlockingQueue<>();


    public static Pages getPageFromFile(String filename){
        Gson gson= new Gson();
        Pages pages= new Pages();
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader("testdata/"+ filename));
            pages= gson.fromJson(br,Pages.class);

        }catch (FileNotFoundException e){
            System.out.println("File Not Found");
        }
        return pages;

    }

    /** Takes in work queue
     * Adds skipped
     *
     * @param queue , the work queue
     */
    public static void crawl(Queue<Page> queue) {
        for (Page p : queue) {
            success.add(p.getAddress());
        }

            for (Page p : queue) {
                for (String link : p.getLinks()) {
                /*
                if(!valid.contains(link))
                    invalid.add(link);
                else{
                    if(success.add(link)==false){
                        skipped.add(link);
                    }
                }
                */
                    if (!success.contains(link))
                        invalid.add(link);
                    else
                        skipped.add(link);
                }
            }


    }

    public static void printOutput(){
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
        Pages pages= getPageFromFile("test1.json");
        Queue<Page> q= new LinkedList<>(pages.getPages());
        crawl(q);
        printOutput();


    }

}
