package markup;

import java.util.List;

public class Emphasis extends Paragraph {
    public Emphasis(List<Markable> list) {
        super(list);
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append("*");
        super.toMarkdown(sb);
        sb.append("*");
    }
}
