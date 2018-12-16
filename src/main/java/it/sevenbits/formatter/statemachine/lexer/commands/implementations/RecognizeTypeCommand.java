package it.sevenbits.formatter.statemachine.lexer.commands.implementations;

import it.sevenbits.formatter.statemachine.lexer.TokenBuilderContext;
import it.sevenbits.formatter.statemachine.lexer.commands.ILexerCommand;

/**
 * Command that would save recognized lexeme type
 */
public class RecognizeTypeCommand implements ILexerCommand {
    private TokenBuilderContext context;
    private ILexerCommand command;

    /**
     * Create command
     *
     * @param context context with current situation in lexer
     */
    public RecognizeTypeCommand(final TokenBuilderContext context) {
        this.context = context;
    }

    /**
     * Create command
     *
     * @param context context with current situation in lexer
     * @param command that would execute after this command
     */
    public RecognizeTypeCommand(final TokenBuilderContext context, final ILexerCommand command) {
        this.context = context;
        this.command = command;
    }

    /**
     * Save lexeme type and execute next command, if it exists
     */
    @Override
    public void execute() {
        context.setLexemeType(context.getStateType());
        if (command != null) {
            command.execute();
        }
    }
}
