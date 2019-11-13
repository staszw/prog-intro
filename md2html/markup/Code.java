package markup;

import java.util.List;

public class Code extends ContainerOfHighlight {
    public Code(List<PartOfHighlight> list) {
        super(list);
    }
    @Override
    public StringBuilder toHtml(StringBuilder sb) {
        super.toHtml(sb, "<code>", "</code>");
        return sb;
    }
}
