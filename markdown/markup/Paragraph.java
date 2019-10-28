package markup;

import java.util.List;

public class Paragraph extends AbstractContainer implements PartOfItem {
    public Paragraph(List<PartOfHighlight> list) {
        super(list);
    }

    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb, "", "");
    }

    public void toHtml(StringBuilder sb) {
        super.toHtml(sb, "", "");
    }
}
