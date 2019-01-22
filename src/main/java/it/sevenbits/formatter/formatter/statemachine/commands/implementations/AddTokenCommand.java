package it.sevenbits.formatter.formatter.statemachine.commands.implementations;

import it.sevenbits.formatter.writers.WriterException;
import it.sevenbits.formatter.formatter.statemachine.TokenContext;
import it.sevenbits.formatter.formatter.statemachine.commands.IFormatterCommand;

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
     * Remade indent if token contains \n symbol
     */
    private void madeIndent() throws WriterException {
        for (int i = 0; i < context.getIndentLevel(); i++) {
            for (int j = 0; j < context.getIndentSize(); j++) {
                context.getWriter().write(' ');
            }
        }
    }

    /**
     * Run command
     *
     * @throws WriterException it something goes wrong
     */
    @Override
    public void execute() throws WriterException {
        for (char c : context.getToken().getLexeme().toCharArray()) {
            context.getWriter().write(c);
            if (c == '\n') {
                madeIndent();
            }
        }

        if (nextInChain != null) {
            nextInChain.execute();
        }
    }
}
