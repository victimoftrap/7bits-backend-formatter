package it.sevenbits.formatter.readers.implementations;

import it.sevenbits.formatter.readers.ReaderException;
import it.sevenbits.formatter.readers.IReader;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Implementation of IReader that reads characters from file
 */
public class FileReader implements IReader, AutoCloseable {
    private Reader reader;
    private int currentSymbol;

    /**
     * Forbidden empty constructor
     */
    private FileReader() {
    }

    /**
     * Create FileReader by path to file
     *
     * @param path - path to file
     * @throws ReaderException if some troubles with file happen
     */
    public FileReader(final String path) throws ReaderException {
        try {
            reader = Files.newBufferedReader(Paths.get(path), Charset.forName("UTF-8"));
            currentSymbol = reader.read();
        } catch (IOException e) {
            throw new ReaderException("Cannot create stream", e);
        }
    }

    /**
     * Check if string has character after current position
     *
     * @return true if string has character, else false
     */
    @Override
    public boolean hasNext() {
        return currentSymbol != -1;
    }

    /**
     * Read character in current position and increase position if string has more characters
     *
     * @return character in current position
     * @throws ReaderException if stream are empty or reader cannot return next character
     */
    @Override
    public char read() throws ReaderException {
        if (!hasNext()) {
            throw new ReaderException("Stream are empty");
        }
        try {
            int temp = currentSymbol;
            currentSymbol = reader.read();
            return (char) temp;
        } catch (IOException e) {
            throw new ReaderException("Cannot read symbol from file", e);
        }
    }

    /**
     * Close file stream
     *
     * @throws ReaderException if file cannot be closed
     */
    @Override
    public void close() throws ReaderException {
        try {
            reader.close();
        } catch (IOException e) {
            throw new ReaderException("Trouble with closing file", e);
        }
    }
}