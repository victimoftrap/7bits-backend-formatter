package it.sevenbits.formatter.formatter.statemachine.commands.implementations;

import it.sevenbits.formatter.writers.WriterException;
import it.sevenbits.formatter.formatter.statemachine.TokenContext;
import it.sevenbits.formatter.formatter.statemachine.commands.IFormatterCommand;

/**
 * Command for decreasing indent level of code
 */
public class DecreaseIndentCommand implements IFormatterCommand {
    private TokenContext context;
    private IFormatterCommand nextInChain;

    /**
     * Create nextInChain
     *
     * @param context with current situation in formatter
     */
    public DecreaseIndentCommand(final TokenContext context) {
        this.context = context;
    }

    /**
     * Create command and set next command
     *
     * @param context     with current situation in formatter
     * @param nextCommand next command
     */
    public DecreaseIndentCommand(final TokenContext context, final IFormatterCommand nextCommand) {
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
        context.setIndentLevel(context.getIndentLevel() - 1);

        if (nextInChain != null) {
            nextInChain.execute();
        }
    }
}
