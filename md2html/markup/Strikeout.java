package markup;

import java.util.List;

public class Strikeout extends ContainerOfHighlight {
    public Strikeout(List<PartOfHighlight> list) {
        super(list);
    }
    @Override
    public StringBuilder toHtml(StringBuilder sb) {
        toHtml(sb, "<s>", "</s>");
        return sb;
    }
}
