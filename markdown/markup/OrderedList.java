package markup;

import java.util.List;

public class OrderedList extends AbstractContainer implements PartOfItem {
    public OrderedList(List<ListItem> list) {
        super(list);
    }

    @Override
    public void toHtml(StringBuilder sb) {
        super.toHtml(sb, "<ol>", "</ol>");
    }

    @Override
    @Deprecated
    public void toMarkdown(StringBuilder sb) {
        //List doesn't support markdown
    }
}
