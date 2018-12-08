import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pages {

    private List<Page> pages;

    Pages(){
        pages=new ArrayList<>();
    }

    public Pages(List<Page> pages){
        this.pages=pages;
    }

    public List<Page> getPages() {
        return pages;
    }

    public Set<String> getValidAddress(){
        Set<String> s= new HashSet<>();
        for(Page p:pages){
            s.add(p.getAddress());
        }
        return s;
    }
}
