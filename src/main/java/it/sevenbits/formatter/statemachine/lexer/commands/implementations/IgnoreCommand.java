package it.sevenbits.formatter.lexer.lexer.commands.implementations;

import it.sevenbits.formatter.lexer.lexer.TokenBuilderContext;
import it.sevenbits.formatter.lexer.lexer.commands.ILexerCommand;

/**
 * Ignoring command
 */
public class IgnoreCommand implements ILexerCommand {
    private TokenBuilderContext context;

    /**
     * Create command
     *
     * @param context context with current situation in lexer
     */
    public IgnoreCommand(final TokenBuilderContext context) {
        this.context = context;
    }

    @Override
    public void execute() {
    }
}
