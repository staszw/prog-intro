package markup;

import java.util.List;

public class Code extends AbstractContainer implements PartOfHighlight {
    public Code(List<PartOfHighlight> list) {
        super(list);
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb, "\'", "\'");
    }

    @Override
    public void toHtml(StringBuilder sb) {
        super.toHtml(sb, "<code>", "</code>");
    }
}
