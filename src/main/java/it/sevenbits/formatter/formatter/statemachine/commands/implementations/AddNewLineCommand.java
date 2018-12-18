package it.sevenbits.formatter.formatter.statemachine.commands.implementations;

import it.sevenbits.formatter.writers.WriterException;
import it.sevenbits.formatter.formatter.statemachine.TokenContext;
import it.sevenbits.formatter.formatter.statemachine.commands.IFormatterCommand;

/**
 * Command for making new line
 */
public class AddNewLineCommand implements IFormatterCommand {
    private TokenContext context;
    private IFormatterCommand nextInChain;

    /**
     * Create nextInChain
     *
     * @param context with current situation in formatter
     */
    public AddNewLineCommand(final TokenContext context) {
        this.context = context;
    }

    /**
     * Create command and set next command
     *
     * @param context     with current situation in formatter
     * @param nextCommand next command
     */
    public AddNewLineCommand(final TokenContext context, final IFormatterCommand nextCommand) {
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
        context.getWriter().write('\n');
        for (int i = 0; i < context.getIndentLevel(); i++) {
            for (int j = 0; j < context.getIndentSize(); j++) {
                context.getWriter().write(' ');
            }
        }

        if (nextInChain != null) {
            nextInChain.execute();
        }
    }
}
