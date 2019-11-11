package markup;

public class Text implements PartOfHighlight {
    private String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append(text);
    }

    @Override
    public void toHtml(StringBuilder sb) {
        sb.append(text);
    }
}
