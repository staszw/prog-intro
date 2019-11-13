package markup;

import java.util.List;

public class Link extends ContainerOfHighlight {
    private String address;
    public Link(List<PartOfHighlight> list) {
        super(list);
    }

    public void setAddress(String address){
        this.address = address;
    }

    @Override
    public StringBuilder toHtml(StringBuilder sb) {
        super.toHtml(sb, "<a href=\'" + address + "\'>", "</a>");
        return sb;
    }
}
