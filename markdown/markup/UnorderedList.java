package markup;

import java.util.List;

public class UnorderedList extends AbstractContainer implements PartOfItem {
    public UnorderedList(List<ListItem> list) {
        super(list);
    }

    @Override
    public void toHtml(StringBuilder sb) {
        super.toHtml(sb, "<ul>", "</ul>");
    }

    @Override
    public void toMarkdown(StringBuilder sb) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("UnorderedList doesn't support markdown");
    }
}
