package it.sevenbits.formatter.formatter;

import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.writers.IWriter;
import it.sevenbits.formatter.exceptions.RWStreamException;

/**
 * Interface of formatter
 * */
public interface Formatting {
    /**
     * Format source
     * @param reader that contains source to format
     * @param writer that would contain reformatted source
     * */
    void format(IReader reader, IWriter writer) throws RWStreamException;
}
