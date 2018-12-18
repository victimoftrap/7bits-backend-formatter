package it.sevenbits.formatter.statemachine.formatter.commands.implementations;

import it.sevenbits.formatter.writers.WriterException;
import it.sevenbits.formatter.statemachine.formatter.TokenContext;
import it.sevenbits.formatter.statemachine.formatter.commands.IFormatterCommand;

/**
 * Command for writing token in IWriter
 */
public class AddTokenCommand implements IFormatterCommand {
    private TokenContext context;
    private IFormatterCommand nextInChain;

    /**
     * Create command
     *
     * @param context with current situation in formatter
     */
    public AddTokenCommand(final TokenContext context) {
        this.context = context;
    }

    /**
     * Create command and set next command
     *
     * @param context     with current situation in formatter
     * @param nextCommand next command
     */
    public AddTokenCommand(final TokenContext context, final IFormatterCommand nextCommand) {
        this.context = context;
        this.nextInChain = nextCommand;
    }

    /**
     * Run command
     *
     * @throws WriterException it something goes wrong
     */
    @Override
    public void execute() throws WriterException {
        for (char c : context.getToken().getName().toCharArray()) {
            context.getWriter().write(c);
        }

        if (nextInChain != null) {
            nextInChain.execute();
        }
    }
}
