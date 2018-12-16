package it.sevenbits.formatter.statemachine.lexer.commands.implementations;

import it.sevenbits.formatter.statemachine.lexer.TokenBuilderContext;
import it.sevenbits.formatter.statemachine.lexer.commands.ILexerCommand;

/**
 * Commands that add character into buffer
 */
public class AddToBufferCommand implements ILexerCommand {
    private TokenBuilderContext context;

    /**
     * Create command
     *
     * @param context context with current situation in lexer
     */
    public AddToBufferCommand(final TokenBuilderContext context) {
        this.context = context;
    }

    @Override
    public void execute() {
        context.getLexemeBuffer().append(context.getCurrentChar());
    }
}
