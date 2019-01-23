package it.sevenbits.formatter.lexer.factory.implementations;

import it.sevenbits.formatter.lexer.ILexer;
import it.sevenbits.formatter.lexer.implementations.Lexer;
import it.sevenbits.formatter.lexer.implementations.StateMachineLexer;
import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.lexer.factory.ILexerFactory;

/**
 * Realisation of lexer factory
 */
public class LexerFactory implements ILexerFactory {
    /**
     * Default constructor
     */
    public LexerFactory() {
    }

    /**
     * Create lexer factory with current reader
     *
     * @param type   type of needed lexer
     *               "BASE" - base implementation of lexer
     *               "STATE" - implementation based on state machine
     *               other string - would return null
     * @param reader some implementation of IReader
     * @return created lexer
     */
    @Override
    public ILexer getLexer(final String type, final IReader reader) {
        if ("STATE".equals(type)) {
            return new StateMachineLexer(reader);
        }
        if ("BASE".equals(type)) {
            return new Lexer(reader);
        }
        return null;
    }
}
