package markup;

public interface Element {
    void toHtml(StringBuilder sb);
    void toMarkdown(StringBuilder sb);
}
