package it.sevenbits.formatter.statemachine.formatter.commands.implementations;

import it.sevenbits.formatter.writers.WriterException;
import it.sevenbits.formatter.statemachine.formatter.TokenContext;
import it.sevenbits.formatter.statemachine.formatter.commands.IFormatterCommand;

/**
 * Command for increasing indent level of code
 */
public class IncreaseIndentCommand implements IFormatterCommand {
    private TokenContext context;
    private IFormatterCommand nextInChain;

    /**
     * Create command
     *
     * @param context with current situation in formatter
     */
    public IncreaseIndentCommand(final TokenContext context) {
        this.context = context;
    }

    /**
     * Create command and set next command
     *
     * @param context     with current situation in formatter
     * @param nextCommand next command
     */
    public IncreaseIndentCommand(final TokenContext context, final IFormatterCommand nextCommand) {
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
        context.setIndentLevel(context.getIndentLevel() + 1);

        if (nextInChain != null) {
            nextInChain.execute();
        }
    }
}
