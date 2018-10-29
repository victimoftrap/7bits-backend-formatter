package it.sevenbits.formatter.readers;

import java.io.IOException;

/**
 * Interface for character reading from stream
 * */
public interface IReader {

    /**
     * Method that checks if stream has a next character after current
     * @return true if stream has a character, false if stream are over
     * */
    boolean hasNext();

    /**
     * Method that reads character from stream
     * @return character from stream
     * @throws IOException if it was some problem with stream
     * */
    char read() throws IOException;
}
