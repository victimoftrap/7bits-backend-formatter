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

    private void makeIndentAt(int position, StringBuilder sb) {
        for (int i = 0; i < indentLevel; i++) {
            sb.insert(position, EXTRA_SPACE);
        }
    }

    private void makeNewLineAt(int position, StringBuilder sb) {
        sb.insert(position, CARRIAGE_RETURN);
        makeIndentAt(position+1, sb);
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

    public String format(String source) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < source.length(); i++) {
            stringBuilder.append(source.charAt(i));

            if (source.charAt(i) == LCBRACE) {
                indentLevel++;
                int index = i;
                if (i != 0) {
                     index = removeSpacesFrom(stringBuilder.lastIndexOf(String.valueOf(LCBRACE)) - 1, stringBuilder);
                }
                stringBuilder.insert(index, SPACE);
                makeNewLineAt(index+2, stringBuilder);
                checkedIndex = stringBuilder.length();
            }
        }
        return stringBuilder.toString();
    }
}
