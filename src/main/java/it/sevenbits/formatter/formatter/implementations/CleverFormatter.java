package it.sevenbits.formatter.formatter.implementations;

import it.sevenbits.formatter.exceptions.FormatterException;
import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.writers.IWriter;
import it.sevenbits.formatter.lexer.ILexer;
import it.sevenbits.formatter.lexer.token.IToken;
import it.sevenbits.formatter.lexer.factory.ILexerFactory;
import it.sevenbits.formatter.formatter.Formattable;
import it.sevenbits.formatter.exceptions.RWStreamException;
import it.sevenbits.formatter.exceptions.LexerException;

/**
 * Realisation of formatter based on lexical analyzer
 * */
public class CleverFormatter implements Formattable {
    private final int INDENT_LENGTH = 4;

    private final String ID_OR_KEYWORD = "ID OR KEYWORD";
    private final String CURLY_LEFT_BRACKET = "CURLY LEFT BRACKET";
    private final String CURLY_RIGHT_BRACKET = "CURLY RIGHT BRACKET";
    private final String ROUND_LEFT_BRACKET = "ROUND LEFT BRACKET";
    private final String ROUND_RIGHT_BRACKET = "ROUND RIGHT BRACKET";
    private final String SEMICOLON = "SEMICOLON";
    private final char SPACE = ' ';
    private final char CARRIAGE_RETURN = '\n';

    private final ILexerFactory lexerFactory;
    private int indentLevel;
    private boolean significantNow;
    private boolean significantBefore;
    private boolean needNewLine;

    /**
     * Constructor of lexer-based formatter
     * @param lexerFactory that can create lexer by some reader
     * */
    public CleverFormatter(final ILexerFactory lexerFactory) {
        this.lexerFactory = lexerFactory;
    }

    /**
     * Write lexeme into writer instance
     * @param writer - instance of IWriter
     * @param lexeme that we would write
     * @throws RWStreamException if some trouble with writer happen
     * */
    private void writeLexeme(final IWriter writer, final String lexeme) throws RWStreamException {
        for (char c : lexeme.toCharArray()) {
            writer.write(c);
        }
    }

    /**
     * Make several times standard indent of 4 spaces in IWriter depending of the level of nesting
     * @param writer - instance of IWriter
     * @throws RWStreamException if some trouble with writer happen
     * */
    private void makeIndent(final IWriter writer) throws RWStreamException {
        for (int i = 0; i < indentLevel; i++) {
            for (int j = 0; j < INDENT_LENGTH; j++) {
                writer.write(SPACE);
            }
        }
    }

    /**
     * Make new line in IWriter several times
     * @param writer - instance of IWriter
     * @throws RWStreamException if some trouble with writer happen
     * */
    private void makeNewLine(final IWriter writer) throws RWStreamException {
        writer.write(CARRIAGE_RETURN);
        makeIndent(writer);
    }

    /**
     * Format something in source
     * @param reader - instance that contains code for formatting
     * @param writer - instance where we would write formatted code
     * @throws FormatterException if some troubles with lexer or writer happen
     * */
    @Override
    public void format(final IReader reader, final IWriter writer) throws FormatterException {
        ILexer lexer = lexerFactory.getLexer(reader);
        significantBefore = true;
        significantNow = false;
        needNewLine = false;

        try {
            while (lexer.hasNext()) {
                IToken token = lexer.readToken();

                if (token.getName().equals(CURLY_LEFT_BRACKET)) {
                    indentLevel++;
                    writer.write(SPACE);
                    writeLexeme(writer, token.getLexeme());
                    makeNewLine(writer);
                    significantNow = true;
                }
                if (token.getName().equals(SEMICOLON)) {
                    writeLexeme(writer, token.getLexeme());
                    needNewLine = true;
                    significantNow = true;
                }
                if (token.getName().equals(CURLY_RIGHT_BRACKET)) {
                    indentLevel--;
                    makeNewLine(writer);
                    writeLexeme(writer, token.getLexeme());
                    needNewLine = true;
                    significantNow = true;
                }
                if (token.getName().equals(ROUND_LEFT_BRACKET)) {
                    writeLexeme(writer, token.getLexeme());
                    needNewLine = false;
                    significantNow = true;
                }
                if (token.getName().equals(ROUND_RIGHT_BRACKET)) {
                    writeLexeme(writer, token.getLexeme());
                    needNewLine = false;
                    significantNow = true;
                }

                if (!significantNow) {
                    if (needNewLine) {
                        makeNewLine(writer);
                        needNewLine = false;
                    }
                    if (!significantBefore) {
                        writer.write(SPACE);
                    }
                    writeLexeme(writer, token.getLexeme());
                }

                significantBefore = significantNow;
                significantNow = false;
            }
        } catch (LexerException e) {
            throw new FormatterException("Some trouble with lexer", e);
        } catch (RWStreamException e) {
            throw new FormatterException("Some trouble with writer", e);
        }
    }
}