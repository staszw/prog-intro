package markup;

import java.util.List;

public class Strong extends ContainerOfHighlight {
    public Strong(List<PartOfHighlight> list) {
        super(list);
    }
    @Override
    public StringBuilder toHtml(StringBuilder sb) {
        super.toHtml(sb, "<strong>", "</strong>");
        return sb;
    }
}
