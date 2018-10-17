package it.sevenbits.formatter;

public class Formatter {
    private static final String EXTRA_SPACE = "    ";
    private static final char LCBRACE = '{';
    private static final char RCBRACE = '}';
    private static final char SEMICOLON = ';';
    private static final char COMMA = ',';
    private static final char SPACE = ' ';
    private static final char CARRIAGE_RETURN = '\n';
    private int tabLevel;
    private boolean newLine;

    private void removeSpaces(StringBuilder sb, int from) {
        while (sb.charAt(from) == SPACE || sb.charAt(from) == CARRIAGE_RETURN) {
            sb.deleteCharAt(from);
            from--;
        }
    }

    private int preventSpaces(String source, int from) {
        while (source.charAt(from+1) == SPACE || source.charAt(from+1) == CARRIAGE_RETURN) {
            from++;
        }
        return from;
    }

    private void addNewLine(StringBuilder sb) {
        sb.append(CARRIAGE_RETURN);
        addIndent(sb);
    }

    private void addNewLineAt(StringBuilder sb, int index) {
        sb.insert(index, CARRIAGE_RETURN);
        for (int i = 0; i < tabLevel; i++) {
            sb.insert(index+1, EXTRA_SPACE);
        }
    }

    private void addIndent(StringBuilder sb) {
        for (int i = 0; i < tabLevel; i++) {
            sb.append(EXTRA_SPACE);
        }
    }

    public String format(String source) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < source.length(); i++) {
            stringBuilder.append(source.charAt(i));
            if (source.charAt(i) == LCBRACE) {
                removeSpaces(stringBuilder, stringBuilder.lastIndexOf(String.valueOf(LCBRACE))-1);
                stringBuilder.insert(stringBuilder.lastIndexOf(String.valueOf(LCBRACE)), SPACE);
                tabLevel++;
                newLine = true;
                i = preventSpaces(source, i);
            }
            if (source.charAt(i) == RCBRACE) {
                removeSpaces(stringBuilder, stringBuilder.length()-2);
                tabLevel--;
                addNewLineAt(stringBuilder, stringBuilder.length()-1);
                newLine = true;
            }
            if (source.charAt(i) == SEMICOLON) {
                newLine = true;
                i = preventSpaces(source, i);
            }

            if (newLine) {
                addNewLine(stringBuilder);
                newLine = false;
            }
        }
        return stringBuilder.toString();
    }
}
