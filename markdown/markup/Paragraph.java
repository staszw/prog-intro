package markup;

import java.util.List;

public class Paragraph implements Markable {
    private List<Markable> list;

    public Paragraph(List<Markable> list) {
        this.list = list;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        for (Markable current : list) {
            current.toMarkdown(sb);
        }
    }
}
