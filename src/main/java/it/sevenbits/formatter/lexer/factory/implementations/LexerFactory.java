package it.sevenbits.formatter.lexer.factory.implementations;

import it.sevenbits.formatter.lexer.ILexer;
import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.lexer.implementations.Lexer;
import it.sevenbits.formatter.lexer.factory.ILexerFactory;

/**
 * Realisation of lexer factory
 * */
public class LexerFactory implements ILexerFactory {
    /**
     * Default constructor
     * */
    public LexerFactory() {
    }

    /**
     * Creating new lexer with current implementation of IReader
     * @param reader some realisation of IReader
     * @return created lexer
     * */
    @Override
    public ILexer getLexer(final IReader reader) {
        return new Lexer(reader);
    }
}
