
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class WebCrawler {


    public void ParseFile(){

    }

    public static void main(String[] args){
        Gson gson= new Gson();
        Pages pages= new Pages();
        BufferedReader br;

       try {
           br = new BufferedReader(new FileReader("testdata/test1.json"));
           pages= gson.fromJson(br,Pages.class);

           for(Page p: pages.getPages()){
               System.out.print(p.getAddress());
               for(String s: p.getLinks()){
                   System.out.print(" "+ s);
               }
               System.out.println();
           }


       }catch (FileNotFoundException e){
           System.out.println("File Not Found");
       }


       // Page page= gson.fromJson(,Page.class);
    }

}
