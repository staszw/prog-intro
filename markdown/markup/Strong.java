package markup;

import java.util.List;

public class Strong extends Paragraph {
    public Strong(List<Markable> list) {
        super(list);
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append("__");
        super.toMarkdown(sb);
        sb.append("__");
    }
}
