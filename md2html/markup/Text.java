package markup;

public class Text implements PartOfHighlight {
    private String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public StringBuilder toHtml(StringBuilder sb) {
        sb.append(text);
        return sb;
    }
}
