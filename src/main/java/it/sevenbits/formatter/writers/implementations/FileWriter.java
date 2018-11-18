package it.sevenbits.formatter.writers.implementations;

import it.sevenbits.formatter.writers.IWriter;
import it.sevenbits.formatter.exceptions.RWStreamException;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

/**
 * Implementation of IWriter that writes characters into file
 * */
public class FileWriter implements IWriter, AutoCloseable {
    private Writer writer;

    /**
     * Forbidden empty constructor
     * */
    private FileWriter(){
    }

    /**
     * Create FileWriter by path to file
     * @param path - path to file
     * @throws RWStreamException if some troubles with file happen
     * */
    public FileWriter(final String path) throws RWStreamException {
        try {
            writer = Files.newBufferedWriter(Paths.get(path));
        } catch (IOException e) {
            throw new RWStreamException(e);
        }
    }

    /**
     * Append character into file
     * @param c character that will appends into stream
     * @throws RWStreamException if some troubles with file happen
     * */
    @Override
    public void write(final char c) throws RWStreamException {
        try {
            writer.write(c);
        } catch (IOException e) {
            throw new RWStreamException(e);
        }
    }

    /**
     * Close file stream
     * @throws RWStreamException if file cannot be closed
     * */
    @Override
    public void close() throws RWStreamException {
        try {
            writer.close();
        } catch (IOException e) {
            throw new RWStreamException(e);
        }
    }
}
