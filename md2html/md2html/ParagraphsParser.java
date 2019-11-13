package md2html;

import markup.*;

import java.util.List;

public class ParagraphsParser {
    private String text;

    public ParagraphsParser(String text){
        this.text = text;
    }

    private int getLevel(){
        int level = 0;
        while (text.length() > level && text.charAt(level) == '#') {
            level++;
        }
        if (text.length() == level || text.charAt(level) != ' ') {
            level = 0;
        }
        return level;
    }

    public ExternalClass parse() {
        int level = getLevel();
        if (level != 0)
            text = text.substring(level + 1);

        HighlightsParser parser = new HighlightsParser(text);
        List<PartOfHighlight> list = parser.parse();

        if (level == 0) {
            return new Paragraph(list);
        } else {
            return new Header(list, level);
        }
    }
}
