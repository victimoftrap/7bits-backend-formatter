package it.sevenbits.formatter.lexer.implementations;

import it.sevenbits.formatter.lexer.statemachine.LexerState;
import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.lexer.LexerException;
import it.sevenbits.formatter.readers.ReaderException;
import it.sevenbits.formatter.lexer.ILexer;
import it.sevenbits.formatter.lexer.token.IToken;
import it.sevenbits.formatter.lexer.token.implementations.Token;
import it.sevenbits.formatter.lexer.statemachine.transitions.LexerStateMap;
import it.sevenbits.formatter.lexer.statemachine.transitions.LexerStateTransition;
import it.sevenbits.formatter.lexer.statemachine.TokenBuilderContext;
import it.sevenbits.formatter.lexer.statemachine.commands.ILexerCommand;
import it.sevenbits.formatter.lexer.statemachine.commands.LexerCommandMap;
import it.sevenbits.formatter.lexer.statemachine.commands.LexerCommandTransition;

/**
 * Class that implements statemachine interface by state machine
 */
public class StateMachineLexer implements ILexer {
    private IReader reader;
    private LexerStateTransition lexerTransition;
    private TokenBuilderContext context;
    private LexerCommandTransition commandTransition;
    private boolean isAnonymousChar;

    private StateMachineLexer() {
    }

    /**
     * Create statemachine
     *
     * @param reader stream with symbols
     */
    public StateMachineLexer(final IReader reader) {
        this.reader = reader;
        this.lexerTransition = new LexerStateTransition(new LexerStateMap());
        this.context = new TokenBuilderContext();
        this.commandTransition = new LexerCommandTransition(new LexerCommandMap(context));
    }

    /**
     * Read token from reader
     *
     * @return new token from stream
     * @throws LexerException if some troubles happen in reader or reader input are empty
     */
    @Override
    public IToken readToken() throws LexerException {
        LexerState current = lexerTransition.getStartState();
        try {
            while (hasNext() && !current.toString().equals("RELEASE")) {
                /*if (!isAnonymousChar) {
                    context.setCurrentChar(reader.read());
                } else {
                    context.setCurrentChar(context.getNextLexemeChar());
                    context.setNextLexemeChar('\0');
                    isAnonymousChar = false;
                }*/
                if (context.getNextLexemeChar() != 0) {
                    context.setCurrentChar(context.getNextLexemeChar());
                    context.setNextLexemeChar('\0');
                } else {
                    context.setCurrentChar(reader.read());
                }

                LexerState next = lexerTransition.getNextState(current, context.getCurrentChar());
                context.setStateType(next.getType());
                ILexerCommand command = commandTransition.nextCommand(current, context);
                command.execute();
                current = next;
            }
            if (context.getToken() != null) {
                IToken token = context.getToken();
                context.setToken(null);
                /*if (context.getNextLexemeChar() != 0) {
                    isAnonymousChar = true;
                }*/
                return token;
            }
            if (context.getLexemeBuffer().length() != 0) {
                IToken token = new Token(context.getLexemeType(), context.getLexemeBuffer().toString());
                context.getLexemeBuffer().setLength(0);
                return token;
            }
        } catch (ReaderException e) {
            throw new LexerException("Some troubles with reader ", e);
        }
        throw new LexerException("Empty input for lexer");
    }

    /**
     * @return true if stream has next character
     */
    @Override
    public boolean hasNext() {
        return reader.hasNext() || context.getNextLexemeChar() != 0;
    }
}