package it.sevenbits.formatter.lexer.factory;

import it.sevenbits.formatter.lexer.ILexer;
import it.sevenbits.formatter.readers.IReader;

/**
 * Interface for factory of lexers
 */
public interface ILexerFactory {
    /**
     * Create lexer with current reader
     *
     * @param type   type of needed lexer
     * @param reader some implementation of IReader
     * @return created lexer
     */
    ILexer getLexer(String type, IReader reader);
}
