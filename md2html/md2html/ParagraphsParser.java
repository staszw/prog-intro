package md2html;

import markup.*;

import java.util.List;

public class ParagraphsParser {
    private static int getLevel(String text){
        int level = 0;
        while (text.length() > level && text.charAt(level) == '#') {
            level++;
        }
        if (text.length() == level || text.charAt(level) != ' ') {
            level = 0;
        }
        return level;
    }

    public static ExternalClass parse(String text) {
        int level = getLevel(text);
        if (level != 0)
            text = text.substring(level + 1);

        List<PartOfHighlight> list = HighlightsParser.parse(text);

        if (level == 0) {
            return new Paragraph(list);
        } else {
            return new Header(list, level);
        }
    }
}
