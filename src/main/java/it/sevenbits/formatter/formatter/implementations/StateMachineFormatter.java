package it.sevenbits.formatter.formatter.implementations;

import it.sevenbits.formatter.formatter.IFormatter;
import it.sevenbits.formatter.formatter.FormatterException;
import it.sevenbits.formatter.formatter.statemachine.FormatterState;
import it.sevenbits.formatter.lexer.token.IToken;
import it.sevenbits.formatter.lexer.ILexer;
import it.sevenbits.formatter.lexer.LexerException;
import it.sevenbits.formatter.lexer.factory.ILexerFactory;
import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.writers.IWriter;
import it.sevenbits.formatter.writers.WriterException;
import it.sevenbits.formatter.formatter.statemachine.TokenContext;
import it.sevenbits.formatter.formatter.statemachine.transitions.FormatterStateMap;
import it.sevenbits.formatter.formatter.statemachine.transitions.FormatterStateTransition;
import it.sevenbits.formatter.formatter.statemachine.commands.IFormatterCommand;
import it.sevenbits.formatter.formatter.statemachine.commands.FormatterCommandMap;
import it.sevenbits.formatter.formatter.statemachine.commands.FormatterCommandTransition;

/**
 * Class that implements IFormatter by state machine
 */
public class StateMachineFormatter implements IFormatter {
    private ILexerFactory lexerFactory;
    private TokenContext context;
    private FormatterStateTransition stateTransition;
    private FormatterCommandTransition commandTransition;

    /**
     * Create formatter state machine
     *
     * @param lexerFactory factory that can create some lexical analyzer
     */
    public StateMachineFormatter(final ILexerFactory lexerFactory) {
        this.lexerFactory = lexerFactory;
        this.context = new TokenContext();
        this.stateTransition = new FormatterStateTransition(new FormatterStateMap());
        this.commandTransition = new FormatterCommandTransition(new FormatterCommandMap(context));
    }

    @Override
    public void format(final IReader reader, final IWriter writer) throws FormatterException {
        context.setWriter(writer);
        ILexer lex = lexerFactory.getLexer("STATE", reader);
        FormatterState currentState = stateTransition.getStartState();

        try {
            while (lex.hasNext()) {
                IToken token = lex.readToken();
                FormatterState nextState = stateTransition.getNextState(currentState, token.getName());
                context.setToken(token);
                IFormatterCommand command = commandTransition.nextCommand(currentState, token.getName());
                command.execute();
                currentState = nextState;
            }
        } catch (LexerException e) {
            throw new FormatterException("Some troubles with statemachine", e);
        } catch (WriterException e) {
            throw new FormatterException("Some troubles with writer stream", e);
        }
    }
}
