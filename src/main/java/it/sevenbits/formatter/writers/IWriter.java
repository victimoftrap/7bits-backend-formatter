package it.sevenbits.formatter.writers;

import it.sevenbits.formatter.exceptions.RWStreamException;

/**
 * Interface for character writing into stream
 * */
public interface IWriter {

    /**
     * Method that writes character into stream
     * @param c - the character
     * @throws RWStreamException if it was some problem with stream
     * */
    void write(char c) throws RWStreamException;
}
