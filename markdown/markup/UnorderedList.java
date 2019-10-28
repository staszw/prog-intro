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
    @Deprecated
    public void toMarkdown(StringBuilder sb) {
        //List doesn't support markdown
    }
}
