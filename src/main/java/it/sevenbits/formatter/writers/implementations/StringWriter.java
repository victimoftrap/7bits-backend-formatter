package it.sevenbits.formatter.writers.implementations;

import it.sevenbits.formatter.writers.IWriter;

/**
 * Implementation of IWriter that works with a string as a stream and writes chars into string
 */
public class StringWriter implements IWriter {
    private StringBuilder stringBuilder;

    /**
     * Empty constructor that creates StringBuilder
     */
    public StringWriter() {
        stringBuilder = new StringBuilder();
    }

    /**
     * Append character into StringBuilder object
     *
     * @param c character that will appends into stream
     */
    public void write(final char c) {
        stringBuilder.append(c);
    }

    /**
     * Return string value of stream
     *
     * @return string value of stream
     */
    public String convertToString() {
        return stringBuilder.toString();
    }
}
