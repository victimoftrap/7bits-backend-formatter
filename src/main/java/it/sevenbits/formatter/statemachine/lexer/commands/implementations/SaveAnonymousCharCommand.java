package it.sevenbits.formatter.statemachine.lexer.commands.implementations;

import it.sevenbits.formatter.statemachine.lexer.TokenBuilderContext;
import it.sevenbits.formatter.statemachine.lexer.commands.ILexerCommand;

/**
 * Add char that we read while listening spaces into special buffer.
 * In next calling for token we would add this char into lexeme buffer and recognize it's type
 */
public class SaveAnonymousCharCommand implements ILexerCommand {
    private TokenBuilderContext context;

    /**
     * Create command
     *
     * @param context context with current situation in lexer
     */
    public SaveAnonymousCharCommand(final TokenBuilderContext context) {
        this.context = context;
    }

    @Override
    public void execute() {
        context.setNextLexemeChar(context.getCurrentChar());
    }
}