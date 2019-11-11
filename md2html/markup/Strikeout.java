package markup;

import java.util.List;

public class Strikeout extends AbstractContainer implements PartOfHighlight {
    public Strikeout(List<PartOfHighlight> list) {
        super(list);
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb, "~", "~");
    }

    @Override
    public void toHtml(StringBuilder sb) {
        toHtml(sb, "<s>", "</s>");
    }
}
