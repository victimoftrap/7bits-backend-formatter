package it.sevenbits.formatter.writers;

import java.io.IOException;

/**
 * Interface for character writing into stream
 * */
public interface IWriter {

    /**
     * Method that writes character into stream
     * @param c - the character
     * @throws IOException if it was some problem with stream
     * */
    void write(final char c) throws IOException;

    /**
     * Get string value of characters in stream
     * @return string value of stream
     * */
    String toString();
}
