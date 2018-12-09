package it.sevenbits.formatter.statemachine.formatter.commands;

import it.sevenbits.formatter.exceptions.WriterException;
import it.sevenbits.formatter.statemachine.formatter.TokenContext;

/**
 * Command for writing token in IWriter
 */
public class AddTokenCommand implements IFormatterCommand {
    private TokenContext context;

    /**
     * Create command
     *
     * @param context with IWriter and current IToken
     */
    public AddTokenCommand(final TokenContext context) {
        this.context = context;
    }

    /**
     * Run command
     */
    @Override
    public void execute() {
        for (char c : context.getToken().getName().toCharArray()) {
            try {
                context.getWriter().write(c);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }
}
