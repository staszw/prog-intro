package markup;

import java.util.List;

public abstract class AbstractContainer implements Element {
    private final List<? extends Element> list;

    public AbstractContainer(List<? extends Element> list) {
        this.list = list;
    }

    public void toHtml(StringBuilder sb, String leftMargin, String rightMargin) {
        sb.append(leftMargin);
        for (Element current : list) {
            current.toHtml(sb);
        }
        sb.append(rightMargin);
    }

    public void toMarkdown(StringBuilder sb, String leftMargin, String rightMargin) {
        sb.append(leftMargin);
        for (Element current : list) {
            current.toMarkdown(sb);
        }
        sb.append(rightMargin);
    }
}
