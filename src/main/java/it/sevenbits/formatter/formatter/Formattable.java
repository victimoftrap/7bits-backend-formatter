package it.sevenbits.formatter.formatter;

import it.sevenbits.formatter.exceptions.FormatterException;
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
     * @throws FormatterException if some trouble happen
     * */
    void format(final IReader reader, final IWriter writer) throws FormatterException;
}
