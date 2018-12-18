package it.sevenbits.formatter.statemachine.formatter.commands;

import it.sevenbits.formatter.exceptions.WriterException;

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
