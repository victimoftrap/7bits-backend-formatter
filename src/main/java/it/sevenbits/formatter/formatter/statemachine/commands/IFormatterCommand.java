package it.sevenbits.formatter.formatter.statemachine.commands;

import it.sevenbits.formatter.writers.WriterException;

/**
 * Interface for formatter commands
 */
public interface IFormatterCommand {

    /**
     * Run command
     *
     * @throws WriterException it something goes wrong
     */
    void execute() throws WriterException;
}
