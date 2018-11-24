package it.sevenbits.formatter;

import it.sevenbits.formatter.exceptions.FormatterException;
import it.sevenbits.formatter.exceptions.RWStreamException;
import it.sevenbits.formatter.formatter.Formattable;
import it.sevenbits.formatter.lexer.ILexer;
import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.writers.IWriter;
import it.sevenbits.formatter.readers.implementations.FileReader;
import it.sevenbits.formatter.writers.implementations.FileWriter;
import it.sevenbits.formatter.lexer.implementations.Lexer;
import it.sevenbits.formatter.formatter.implementations.CleverFormatter;
import it.sevenbits.formatter.lexer.factory.implementations.LexerFactory;

/**
 * Main class for running formatter
 * */
public final class Main {
    private Main() {
    }

    /**
     * Method that starts formatter
     * @param args console arguments:
     *        path to file that would be formatted, path to file that would contain reformatted code
     *
     * */
    public static void main(final String[] args) {
        IReader reader = null;
        IWriter writer = null;
        try {
            reader = new FileReader(args[0]);
            writer = new FileWriter(args[1]);
        } catch (RWStreamException e) {
            e.printStackTrace();
        }

        ILexer lex = new Lexer(reader);
        Formattable formatter = new CleverFormatter(new LexerFactory());
        try {
            formatter.format(reader, writer);
        } catch (FormatterException e) {
            e.printStackTrace();
        }
    }
}
