package it.sevenbits.formatter.lexer.lexer.commands.implementations;

import it.sevenbits.formatter.lexer.lexer.TokenBuilderContext;
import it.sevenbits.formatter.lexer.lexer.commands.ILexerCommand;

/**
 * Add char that we read while listening spaces into special buffer.
 * In next calling for token we would add this char into lexeme buffer and recognize it's type
 */
public class SaveAnonymousCharCommand implements ILexerCommand {
    private TokenBuilderContext context;
    private ILexerCommand nextInChain;

    /**
     * Create command
     *
     * @param context context with current situation in lexer
     */
    public SaveAnonymousCharCommand(final TokenBuilderContext context) {
        this.context = context;
    }

    /**
     * Create command and set next command to execute
     *
     * @param context context with current situation in lexer
     * @param command that would execute after this command
     */
    public SaveAnonymousCharCommand(final TokenBuilderContext context, final ILexerCommand command) {
        this.context = context;
        this.nextInChain = command;
    }

    @Override
    public void execute() {
        context.setNextLexemeChar(context.getCurrentChar());
        if (nextInChain != null) {
            nextInChain.execute();
        }
    }
}
