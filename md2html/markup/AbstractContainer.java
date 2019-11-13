package markup;

import java.util.List;

public abstract class AbstractContainer implements Element {
    protected List<PartOfHighlight> list;

    public AbstractContainer(List<PartOfHighlight> list) {
        this.list = list;
    }

    public StringBuilder toHtml(StringBuilder sb, String leftMargin, String rightMargin) {
        sb.append(leftMargin);
        for (Element current : list) {
            current.toHtml(sb);
        }
        sb.append(rightMargin);
        return sb;
    }

    public void add(PartOfHighlight newElement){
        list.add(newElement);
    }
}
