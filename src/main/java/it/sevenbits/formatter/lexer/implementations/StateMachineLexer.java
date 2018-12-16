package it.sevenbits.formatter.lexer.implementations;

import it.sevenbits.formatter.exceptions.LexerException;
import it.sevenbits.formatter.exceptions.ReaderException;
import it.sevenbits.formatter.lexer.ILexer;
import it.sevenbits.formatter.lexer.token.IToken;
import it.sevenbits.formatter.lexer.token.implementations.Token;
import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.readers.implementations.StringReader;
import it.sevenbits.formatter.statemachine.State;
import it.sevenbits.formatter.statemachine.lexer.LexerStateMap;
import it.sevenbits.formatter.statemachine.lexer.TokenBuilderContext;
import it.sevenbits.formatter.statemachine.lexer.commands.CommandMap;
import it.sevenbits.formatter.statemachine.lexer.commands.ILexerCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that implements lexer interface by state machine
 */
public class StateMachineLexer implements ILexer {
    private IReader reader;
    private LexerStateMap lexerStateMap;
    private TokenBuilderContext context;
    private CommandMap commandMap;
    private boolean isAnonymousChar;

    private StateMachineLexer() {
    }

    /**
     * Create lexer
     *
     * @param reader stream with symbols
     */
    public StateMachineLexer(final IReader reader) {
        this.reader = reader;
        this.lexerStateMap = new LexerStateMap();
        this.context = new TokenBuilderContext();
        this.commandMap = new CommandMap(context);
    }

    /**
     * @return new token from stream
     */
    @Override
    public IToken readToken() throws LexerException {
        State current = lexerStateMap.getStartState();
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

                State next = lexerStateMap.getNextState(current, context.getCurrentChar());
                context.setStateType(next.getType());
                ILexerCommand command = commandMap.getCommand(current, context);
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
            throw new LexerException(e);
        }
        return null;
    }

    /**
     * @return true if stream has next character
     */
    @Override
    public boolean hasNext() {
        return reader.hasNext() || context.getNextLexemeChar() != 0;
    }

    public static void main(String[] args) throws LexerException {
        StateMachineLexer sml = new StateMachineLexer(new StringReader("   {    abceda   ; \"meye\";  }   "));
        List<IToken> list = new ArrayList<>();
        while (sml.hasNext()) {
            list.add(sml.readToken());
        }
    }
}