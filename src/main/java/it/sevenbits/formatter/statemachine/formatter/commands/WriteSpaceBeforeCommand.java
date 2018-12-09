package it.sevenbits.formatter.statemachine.formatter.commands;

import it.sevenbits.formatter.exceptions.WriterException;
import it.sevenbits.formatter.statemachine.formatter.TokenContext;

/**
 * Command for writing space in IWriter and call other command
 */
public class WriteSpaceBeforeCommand implements IFormatterCommand {
    private TokenContext context;
    private IFormatterCommand command;
    private char SPACE = ' ';

    /**
     * Create command
     *
     * @param context with IWriter and current IToken
     * @param command next command in chain
     */
    public WriteSpaceBeforeCommand(final TokenContext context, final IFormatterCommand command) {
        this.context = context;
        this.command = command;
    }

    /**
     * Run command
     * */
    @Override
    public void execute() {
        try {
            context.getWriter().write(SPACE);
            if (command != null) {
                command.execute();
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
