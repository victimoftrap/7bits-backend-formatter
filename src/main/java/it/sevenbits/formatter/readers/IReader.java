package it.sevenbits.formatter.readers;

import it.sevenbits.formatter.exceptions.RWStreamException;

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
     * @throws RWStreamException if it was some problem with stream
     * */
    char read() throws RWStreamException;
}
