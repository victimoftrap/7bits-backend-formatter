package it.sevenbits.formatter.writers.implementations;

import it.sevenbits.formatter.writers.WriterException;
import it.sevenbits.formatter.writers.IWriter;

import java.io.Writer;
import java.nio.charset.Charset;
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
     * @throws WriterException if some troubles with file happen
     * */
    public FileWriter(final String path) throws WriterException {
        try {
            writer = Files.newBufferedWriter(Paths.get(path), Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new WriterException("Cannot create stream", e);
        }
    }

    /**
     * Append character into file
     * @param c character that will appends into stream
     * @throws WriterException if some troubles with file happen
     * */
    @Override
    public void write(final char c) throws WriterException {
        try {
            writer.write(c);
        } catch (IOException e) {
            throw new WriterException("Cannot write symbol to file", e);
        }
    }

    /**
     * Close file stream
     * @throws WriterException if file cannot be closed
     * */
    @Override
    public void close() throws WriterException {
        try {
            writer.close();
        } catch (IOException e) {
            throw new WriterException("Trouble with closing file", e);
        }
    }
}
