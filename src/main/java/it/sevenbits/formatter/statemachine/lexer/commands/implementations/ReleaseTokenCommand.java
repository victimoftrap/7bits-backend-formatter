package it.sevenbits.formatter.statemachine.lexer.commands.implementations;

import it.sevenbits.formatter.lexer.token.implementations.Token;
import it.sevenbits.formatter.statemachine.lexer.TokenBuilderContext;
import it.sevenbits.formatter.statemachine.lexer.commands.ILexerCommand;

/**
 * Command for creating token
 */
public class ReleaseTokenCommand implements ILexerCommand {
    private TokenBuilderContext context;
    private ILexerCommand command;

    /**
     * Create command
     *
     * @param context context with current situation in lexer
     */
    public ReleaseTokenCommand(final TokenBuilderContext context) {
        this.context = context;
    }

    /**
     * Create command
     *
     * @param context context with current situation in lexer
     * @param command that would execute after this command
     */
    public ReleaseTokenCommand(final TokenBuilderContext context, final ILexerCommand command) {
        this.context = context;
        this.command = command;
    }

    /**
     * Execute this command and execute next command if it's not null
     */
    @Override
    public void execute() {
        this.context.setToken(new Token(context.getCurrentStateType(), context.getTokenBuffer().toString()));
        this.context.getTokenBuffer().setLength(0);
        if (command != null) {
            command.execute();
        }
    }
}
