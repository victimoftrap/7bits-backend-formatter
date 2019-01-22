package it.sevenbits.formatter.readers.implementations;

import it.sevenbits.formatter.readers.ReaderException;
import it.sevenbits.formatter.readers.IReader;

/**
 * Implementation of IReader that works with a string as a stream
 */
public class StringReader implements IReader {
    private String source;
    private int position;

    /**
     * Forbidden empty constructor
     */
    private StringReader() {
    }

    /**
     * Constructor that copies link on string
     *
     * @param source string that will be copied
     * @throws ReaderException if source are null
     */
    public StringReader(final String source) throws ReaderException {
        if (source == null) {
            throw new ReaderException("Stream cannot be null");
        }
        this.source = source;
        position = 0;
    }

    /**
     * Check if string has character after current position
     *
     * @return true if string has character, else false
     */
    public boolean hasNext() {
        return position < source.length();
    }

    /**
     * Read character in current position and increase position if string has more characters
     *
     * @return character in current position
     * @throws ReaderException if stream are empty
     */
    public char read() throws ReaderException {
        if (hasNext()) {
            return source.charAt(position++);
        }
        throw new ReaderException("Stream are empty");
    }
}
