package it.sevenbits.formatter.formatter.implementations;

import it.sevenbits.formatter.formatter.IFormatter;
import it.sevenbits.formatter.formatter.FormatterException;
import it.sevenbits.formatter.lexer.token.IToken;
import it.sevenbits.formatter.lexer.ILexer;
import it.sevenbits.formatter.lexer.LexerException;
import it.sevenbits.formatter.lexer.factory.ILexerFactory;
import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.writers.IWriter;
import it.sevenbits.formatter.writers.WriterException;
import it.sevenbits.formatter.statemachine.State;
import it.sevenbits.formatter.statemachine.formatter.TokenContext;
import it.sevenbits.formatter.statemachine.formatter.FormatterStateMap;
import it.sevenbits.formatter.statemachine.formatter.FormatterStateTransition;
import it.sevenbits.formatter.statemachine.formatter.commands.IFormatterCommand;
import it.sevenbits.formatter.statemachine.formatter.commands.FormatterCommandMap;
import it.sevenbits.formatter.statemachine.formatter.commands.FormatterCommandTransition;

/**
 * Class that implements IFormatter by state machine
 */
public class StateMachineFormatter implements IFormatter {
    private ILexerFactory lexerFactory;
    private TokenContext context;
    private FormatterStateTransition stateTransition;
    private FormatterCommandTransition commandTransition;

    /**
     * Create state machine formatter
     *
     * @param lexerFactory lexer factory that can create some lexical analyzer
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
        State currentState = stateTransition.getStartState();

        try {
            while (lex.hasNext()) {
                IToken token = lex.readToken();
                State nextState = stateTransition.getNextState(currentState, token.getName());
                context.setToken(token);
                IFormatterCommand command = commandTransition.nextCommand(currentState, token.getName());
                command.execute();
                currentState = nextState;
            }
        } catch (LexerException e) {
            throw new FormatterException("Some troubles with lexer", e);
        } catch (WriterException e) {
            throw new FormatterException("Some troubles with writer stream", e);
        }
    }
}
