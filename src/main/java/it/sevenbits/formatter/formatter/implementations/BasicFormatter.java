package it.sevenbits.formatter.formatter.implementations;

import it.sevenbits.formatter.formatter.FormatterException;
import it.sevenbits.formatter.readers.ReaderException;
import it.sevenbits.formatter.writers.WriterException;
import it.sevenbits.formatter.formatter.IFormatter;
import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.writers.IWriter;

/**
 * Class that formats code by code style rules
 */
public class BasicFormatter implements IFormatter {
    private final int INDENT_LENGTH = 4;
    private final String EXTRA_SPACE = "    ";
    private final char LCBRACE = '{';
    private final char RCBRACE = '}';
    private final char SEMICOLON = ';';
    private final char COMMA = ',';
    private final char SPACE = ' ';
    private final char PRIME = '\'';
    private final char DOUBLE_PRIME = '\"';
    private final char SIGN_EQUALS = '=';
    private final char SIGN_PLUS = '+';
    private final char CARRIAGE_RETURN = '\n';

    private int indentLevel;

    /**
     * Make several times standard indent of 4 spaces in IWriter depending of the level of nesting
     *
     * @param writer - instance of IWriter
     */
    private void makeIndent(final IWriter writer) throws WriterException {
        for (int i = 0; i < indentLevel; i++) {
            for (int j = 0; j < INDENT_LENGTH; j++) {
                writer.write(SPACE);
            }
        }
    }

    /**
     * Make new line in IWriter several times
     *
     * @param writer - instance of IWriter
     */
    private void makeNewLine(final IWriter writer) throws WriterException {
        writer.write(CARRIAGE_RETURN);
        makeIndent(writer);
    }

    /**
     * Method that formats code
     *
     * @param reader - instance that contains code for formatting
     * @param writer - instance where we would write formatted code
     * @throws FormatterException if was trouble with streams
     */
    public void format(final IReader reader, final IWriter writer) throws FormatterException {
        boolean significantBefore = true;
        boolean significantNow = false;
        boolean needNewLine = false;
        boolean needSpace = false;

        boolean itsAString = false;
        char currentSymbol = 0;

        try {
            while (reader.hasNext()) {
                currentSymbol = reader.read();

                needSpace = false;
                if (currentSymbol == SPACE) {
                    if (!itsAString) {
                        while (reader.hasNext() && (currentSymbol == SPACE || currentSymbol == CARRIAGE_RETURN)) {
                            currentSymbol = reader.read();
                            needSpace = true;
                        }
                        if (!reader.hasNext() && (currentSymbol == SPACE || currentSymbol == CARRIAGE_RETURN)) {
                            break;
                        }
                    }
                }

                if (currentSymbol == CARRIAGE_RETURN) {
                    significantNow = true;
                }
                if (currentSymbol == LCBRACE) {
                    indentLevel++;
                    writer.write(SPACE);
                    writer.write(LCBRACE);
                    makeNewLine(writer);
                    significantNow = true;
                }
                if (currentSymbol == SEMICOLON) {
                    writer.write(SEMICOLON);
                    needNewLine = true;
                    significantNow = true;
                }
                if (currentSymbol == RCBRACE) {
                    indentLevel--;
                    makeNewLine(writer);
                    writer.write(RCBRACE);
                    needNewLine = true;
                    significantNow = true;
                }

                if (currentSymbol == DOUBLE_PRIME) {
                    itsAString = !itsAString;
                }

                if (!significantNow) {
                    if (!significantBefore && needSpace) {
                        writer.write(SPACE);
                    }
                    if (needNewLine) {
                        makeNewLine(writer);
                        needNewLine = false;
                    }
                    writer.write(currentSymbol);
                }

                significantBefore = significantNow;
                significantNow = false;
            }
        } catch (ReaderException e) {
            throw new FormatterException("Some troubles with reader", e);
        } catch (WriterException e) {
            throw new FormatterException("Some troubles with writer", e);
        }
    }
}