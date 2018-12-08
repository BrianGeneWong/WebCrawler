import java.util.*;

public class Page {

    private  String address;
    private Set<String> links;

    public Page(){
        address=null;
        links=new HashSet<>();
    }

    public Page(String address,Set<String> links){
        this.address=address;
        this.links=links;
    }

    public String getAddress() {
        return address;
    }

    public Set<String> getLinks() {
        return links;
    }

}
