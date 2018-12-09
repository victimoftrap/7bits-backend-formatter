package it.sevenbits.formatter.writers;

import it.sevenbits.formatter.exceptions.WriterException;

/**
 * Interface for character writing into stream
 * */
public interface IWriter {

    /**
     * Method that writes character into stream
     * @param c - the character
     * @throws WriterException if it was some problem with stream
     * */
    void write(char c) throws WriterException;
}
