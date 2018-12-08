import com.google.gson.annotations.SerializedName;

import java.util.*;

public class Page {


    //@SerializedName("address")
    private  String address;
    //@SerializedName("links")
    private ArrayList<String> links;

    public Page(){
        address=null;
        links= new ArrayList<>();
    }

    public Page(String address,ArrayList<String> links){
        this.address=address;
        this.links=links;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<String> getLinks() {
        return links;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void setLinks(ArrayList<String> links) {
        this.links = links;
    }

}
