package markup;

import java.util.List;

public abstract class AbstractContainer implements Element {
    private final List<? extends Element> list;

    public AbstractContainer(List<? extends Element> list) {
        this.list = list;
    }

    public void toHtml(StringBuilder sb, String leftMargin, String rightMargin) {
        fillStringBuilder(sb, leftMargin, rightMargin, "html");
    }

    public void toMarkdown(StringBuilder sb, String leftMargin, String rightMargin) {
        fillStringBuilder(sb, leftMargin, rightMargin, "markdown");
    }

    private void fillStringBuilder(StringBuilder sb, String leftMargin, String rightMargin, String type){
        sb.append(leftMargin);
        for (Element current : list) {
            if (type.equals("markdown")){
                current.toMarkdown(sb);
            } else {
                current.toHtml(sb);
            }
        }
        sb.append(rightMargin);
    }
}
