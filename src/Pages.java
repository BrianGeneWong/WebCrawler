import java.util.ArrayList;
import java.util.List;

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
}
