package markup;

import java.util.List;

public class ListItem extends AbstractContainer {
    public ListItem(List<PartOfItem> list){
        super(list);
    }

    @Override
    public void toHtml(StringBuilder sb) {
        super.toHtml(sb, "<li>", "</li>");
    }

    @Override
    @Deprecated
    public void toMarkdown(StringBuilder sb) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("ListItem doesn't support markdown");
    }
}
