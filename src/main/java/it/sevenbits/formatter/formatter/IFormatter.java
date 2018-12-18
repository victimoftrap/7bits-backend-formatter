package it.sevenbits.formatter.formatter;

import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.writers.IWriter;

/**
 * Interface of formatter
 */
public interface IFormatter {
    /**
     * Format source
     *
     * @param reader that contains source to format
     * @param writer that would contain reformatted source
     * @throws FormatterException if some trouble happen
     */
    void format(IReader reader, IWriter writer) throws FormatterException;
}
