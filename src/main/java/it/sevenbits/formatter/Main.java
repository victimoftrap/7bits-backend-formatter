package it.sevenbits.formatter;

import it.sevenbits.formatter.formatter.IFormatter;
import it.sevenbits.formatter.readers.ReaderException;
import it.sevenbits.formatter.writers.WriterException;
import it.sevenbits.formatter.formatter.FormatterException;
import it.sevenbits.formatter.lexer.factory.implementations.LexerFactory;
import it.sevenbits.formatter.formatter.implementations.StateMachineFormatter;
import it.sevenbits.formatter.readers.implementations.FileReader;
import it.sevenbits.formatter.writers.implementations.FileWriter;

/**
 * Main class for running formatter
 */
public final class Main {
    private Main() {
    }

    /**
     * Method that starts formatter
     *
     * @param args console arguments:
     *             path to file that would be formatted, path to file that would contain reformatted code
     */
    public static void main(final String[] args) {
        try (FileReader reader = new FileReader(args[0]);
             FileWriter writer = new FileWriter(args[1])) {
            IFormatter formatter = new StateMachineFormatter(new LexerFactory());
            formatter.format(reader, writer);
        } catch (FormatterException | WriterException | ReaderException e) {
            e.printStackTrace();
        }
    }
}
