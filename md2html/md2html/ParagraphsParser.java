package md2html;

import markup.*;

import java.util.List;

public class ParagraphsParser {
    private String text;

    public ParagraphsParser(String text) {
        this.text = text;
    }

    private int getLevel() {
        int level = 0;
        while (level < text.length() && text.charAt(level) == '#') {
            level++;
        }
        if (level < text.length() && text.charAt(level) == ' ') {
            return level;
        }
        return 0;
    }

    public ExternalClass parse() {
        int level = getLevel();
        if (level != 0) {
            text = text.substring(level + 1);
        }

        List<PartOfHighlight> list = new HighlightsParser(text).parse();

        if (level == 0) {
            return new Paragraph(list);
        } else {
            return new Header(list, level);
        }
    }
}
