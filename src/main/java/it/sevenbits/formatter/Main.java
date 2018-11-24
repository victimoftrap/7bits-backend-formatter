package it.sevenbits.formatter;

import it.sevenbits.formatter.exceptions.FormatterException;
import it.sevenbits.formatter.exceptions.RWStreamException;
import it.sevenbits.formatter.formatter.Formattable;
import it.sevenbits.formatter.readers.implementations.FileReader;
import it.sevenbits.formatter.writers.implementations.FileWriter;
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
     * */
    public static void main(final String[] args) {
        try (FileReader reader = new FileReader(args[0]);
            FileWriter writer = new FileWriter(args[1])) {
            Formattable formatter = new CleverFormatter(new LexerFactory());
            formatter.format(reader, writer);
        } catch (RWStreamException | FormatterException e) {
            e.printStackTrace();
        }
    }
}
