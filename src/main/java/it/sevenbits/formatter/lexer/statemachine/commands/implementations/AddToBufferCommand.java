package it.sevenbits.formatter.lexer.statemachine.commands.implementations;

import it.sevenbits.formatter.lexer.statemachine.TokenBuilderContext;
import it.sevenbits.formatter.lexer.statemachine.commands.ILexerCommand;

/**
 * Commands that add character into buffer
 */
public class AddToBufferCommand implements ILexerCommand {
    private TokenBuilderContext context;
    private ILexerCommand nextInChain;

    /**
     * Create command
     *
     * @param context context with current situation in lexer
     */
    public AddToBufferCommand(final TokenBuilderContext context) {
        this.context = context;
    }

    /**
     * Create command and set next command to execute
     *
     * @param context context with current situation in lexer
     * @param command that would execute after this command
     */
    public AddToBufferCommand(final TokenBuilderContext context, final ILexerCommand command) {
        this.context = context;
        this.nextInChain = command;
    }

    @Override
    public void execute() {
        context.getLexemeBuffer().append(context.getCurrentChar());

        if (nextInChain != null) {
            nextInChain.execute();
        }
    }
}
