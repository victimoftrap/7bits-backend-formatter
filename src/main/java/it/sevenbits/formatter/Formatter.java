package it.sevenbits.formatter;

public class Formatter {
    private final String EXTRA_SPACE = "    ";
    private final char LCBRACE = '{';
    private final char RCBRACE = '}';
    private final char SEMICOLON = ';';
    private final char COMMA = ',';
    private final char SPACE = ' ';
    private final char CARRIAGE_RETURN = '\n';
    private int checkedIndex;
    private int indentLevel;

    private void makeIndent(StringBuilder sb) {
        for (int i = 0; i < indentLevel; i++) {
            sb.append(EXTRA_SPACE);
        }
    }

    private void makeIndentAt(int index, StringBuilder sb) {
        for (int i = 0; i < indentLevel; i++) {
            sb.insert(index, EXTRA_SPACE);
        }
    }

    private void makeNewLine(StringBuilder sb) {
        sb.append(CARRIAGE_RETURN);
        makeIndent(sb);
    }

    private void makeNewLineAt(int index, StringBuilder sb) {
        sb.insert(index, CARRIAGE_RETURN);
        makeIndentAt(index+1, sb);
    }

    private int removeSpacesFrom(int index, StringBuilder sb) {
        int startIndex = index;
        while (startIndex > checkedIndex && sb.charAt(startIndex) == SPACE || sb.charAt(startIndex) == CARRIAGE_RETURN) {
            startIndex--;
        }
        if (startIndex != index) {
            sb.delete(startIndex+1, index+1);
        }
        return startIndex+1;
    }

    private int preventSpaces(String source, int from) {
        if (source.length() == from+1) {
            return from;
        }
        while (source.charAt(from+1) == SPACE || source.charAt(from+1) == CARRIAGE_RETURN) {
            from++;
        }
        return from;
    }

    public String format(String source) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < source.length(); i++) {
            stringBuilder.append(source.charAt(i));

            if (source.charAt(i) == LCBRACE) {
                indentLevel++;
                int index = i;
                if (i != 0) {
                     index = removeSpacesFrom(stringBuilder.lastIndexOf(String.valueOf(LCBRACE))-1, stringBuilder);
                }
                stringBuilder.insert(index, SPACE);
                makeNewLineAt(index+2, stringBuilder);
                checkedIndex = stringBuilder.length();
                i = preventSpaces(source, i);
            }
            if (source.charAt(i) == RCBRACE) {
                indentLevel--;
                removeSpacesFrom(stringBuilder.length()-2, stringBuilder);
                makeNewLineAt(stringBuilder.length()-1, stringBuilder);
                makeNewLine(stringBuilder);
                i = preventSpaces(source, i);
                // checkedIndex = stringBuilder.length();
            }
            if (source.charAt(i) == SEMICOLON) {
                i = preventSpaces(source, i);
                makeNewLineAt(stringBuilder.length(), stringBuilder);
                // checkedIndex = stringBuilder.length();
            }
        }
        indentLevel = 0;
        checkedIndex = 0;
        return stringBuilder.toString();
    }
}
