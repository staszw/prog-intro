package markup;

import java.util.List;

public class Emphasis extends ContainerOfHighlight {
    public Emphasis(List<PartOfHighlight> list) {
        super(list);
    }
    @Override
    public StringBuilder toHtml(StringBuilder sb) {
        super.toHtml(sb, "<em>", "</em>");
        return sb;
    }
}
