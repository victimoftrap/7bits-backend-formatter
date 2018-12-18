package it.sevenbits.formatter.lexer.statemachine.commands.implementations;

import it.sevenbits.formatter.lexer.token.implementations.Token;
import it.sevenbits.formatter.lexer.statemachine.TokenBuilderContext;
import it.sevenbits.formatter.lexer.statemachine.commands.ILexerCommand;

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
     * Create command and set next command to execute
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
        this.context.setToken(new Token(context.getLexemeType(), context.getLexemeBuffer().toString()));
        this.context.getLexemeBuffer().setLength(0);
        if (command != null) {
            command.execute();
        }
    }
}
