package markup;

import java.util.List;

public class Header extends AbstractContainer implements ExternalClass {
    private int level;
    public Header(List<PartOfHighlight> list, int level) {
        super(list);
        this.level = level;
    }

    public StringBuilder toHtml(StringBuilder sb) {
        super.toHtml(sb, "<h" + level + ">", "</h" + level + ">");
        return sb;
    }
}
