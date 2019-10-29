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
    public void toMarkdown(StringBuilder sb) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("OrderedList doesn't support markdown");
    }
}
