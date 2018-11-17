package it.sevenbits.formatter.readers.implementations;

import it.sevenbits.formatter.exceptions.RWStreamException;
import it.sevenbits.formatter.readers.IReader;

/**
 * Implementation of IReader that works with a string as a stream
 * */
public class StringReader implements IReader {
    private String source;
    private int position;

    /**
     * Forbidden empty constructor
     * */
    private StringReader(){}

    /**
     * Constructor that copies link on string
     * */
    public StringReader(String source) {
        this.source = source;
        position = 0;
    }

    /**
     * Check if string has character after current position
     * @return true if string has character, else false
     * */
    public boolean hasNext() {
        return position < source.length();
    }

    /**
     * Read character in current position and increase position if string has more characters
     * @return character in current position
     * */
    public char read() throws RWStreamException {
        if (hasNext()) {
            return source.charAt(position++);
        }
        throw new RWStreamException("Stream are empty");
    }
}
