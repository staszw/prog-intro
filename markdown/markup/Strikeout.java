package markup;

import java.util.List;

public class Strikeout extends Paragraph {
    public Strikeout(List<Markable> list) {
        super(list);
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append("~");
        super.toMarkdown(sb);
        sb.append("~");
    }
}
