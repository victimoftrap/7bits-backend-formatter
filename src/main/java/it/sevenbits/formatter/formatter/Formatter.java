package it.sevenbits.formatter.formatter;

import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.writers.IWriter;

import java.io.IOException;

/**
 * Class that formats code by code style rules
 * */
public class Formatter {
    private final int INDENT_LENGTH = 4;
    private final String EXTRA_SPACE = "    ";
    private final char LCBRACE = '{';
    private final char RCBRACE = '}';
    private final char SEMICOLON = ';';
    private final char COMMA = ',';
    private final char SPACE = ' ';
    private final char PRIME = '\'';
    private final char DOUBLE_PRIME = '\"';
    private final char CARRIAGE_RETURN = '\n';

    private int indentLevel;
    private boolean itsAString = false;
    private boolean writeMe = true;
    private boolean needNewLine = false;

    /**
     * Make several times standard indent of 4 spaces in IWriter depending of the level of nesting
     * @param writer - instance of IWriter
     * */
    private void makeIndent(IWriter writer) throws IOException {
        for (int i = 0; i < indentLevel; i++) {
            for (int j = 0; j < INDENT_LENGTH; j++) {
                writer.write(SPACE);
            }
        }
    }

    /**
     * Make new line in IWriter several times
     * @param writer - instance of IWriter
     * */
    private void makeNewLine(IWriter writer) throws IOException {
        writer.write(CARRIAGE_RETURN);
        makeIndent(writer);
    }

    /**
     * Method that formats code
     * @param reader - instance that contains code for formatting
     * @param writer - instance where we would write formatted code
     * */
    public void format(IReader reader, IWriter writer) throws IOException {
        itsAString = false;
        needNewLine = false;
        writeMe = true;
        char currentSymbol = 0;

        while (reader.hasNext()) {
            currentSymbol = reader.read();
            if (!itsAString) {
                while (reader.hasNext() && currentSymbol == SPACE || currentSymbol == CARRIAGE_RETURN) {
                    currentSymbol = reader.read();
                }
                if (currentSymbol == SPACE || currentSymbol == CARRIAGE_RETURN && !reader.hasNext()) {
                    break;
                }
            }
            writeMe = true;
            if (currentSymbol == LCBRACE) {
                indentLevel++;
                writer.write(SPACE);
                writer.write(LCBRACE);
                makeNewLine(writer);
                writeMe = false;
            }
            if (currentSymbol == SEMICOLON) {
                writer.write(SEMICOLON);
                writeMe = false;
                needNewLine = true;
            }
            if (currentSymbol == RCBRACE) {
                indentLevel--;
                makeNewLine(writer);
                writer.write(RCBRACE);
                writeMe = false;
                needNewLine = true;
            }
            if (currentSymbol == DOUBLE_PRIME) {
                itsAString = !itsAString;
            }
            if (writeMe) {
                if (needNewLine) {
                    makeNewLine(writer);
                    needNewLine = false;
                }
                writer.write(currentSymbol);
            }
        }
    }
}