package it.sevenbits.formatter.readers.implementations;

import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.exceptions.RWStreamException;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.io.IOException;

/**
 * Implementation of IReader that reads characters from file
 * */
public class FileReader implements IReader, AutoCloseable {
    private Reader reader;
    private int currentSymbol;

    /**
     * Forbidden empty constructor
     * */
    private FileReader(){
    }

    /**
     * Create FileReader by path to file
     * @param path - path to file
     * @throws RWStreamException if some troubles with file happen
     * */
    public FileReader(final String path) throws RWStreamException {
        try {
            reader = Files.newBufferedReader(Paths.get(path), Charset.forName("UTF-8"));
            currentSymbol = 0;
        } catch (IOException e) {
            throw new RWStreamException(e);
        }
    }

    /**
     * Check if string has character after current position
     * @return true if string has character, else false
     * */
    @Override
    public boolean hasNext() {
        return currentSymbol != -1;
    }

    /**
     * Read character in current position and increase position if string has more characters
     * @return character in current position
     * */
    @Override
    public char read() throws RWStreamException {
        try {
            currentSymbol = reader.read();
            return (char) currentSymbol;
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
            reader.close();
        } catch (IOException e) {
            throw new RWStreamException("Cannot close file", e);
        }
    }
}
