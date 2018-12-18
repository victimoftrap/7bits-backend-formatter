package it.sevenbits.formatter.lexer.factory;

import it.sevenbits.formatter.lexer.ILexer;
import it.sevenbits.formatter.readers.IReader;

/**
 * Interface for factory of lexers
 */
public interface ILexerFactory {
    /**
     * Create statemachine with current reader
     *
     * @param type   type of needed statemachine
     * @param reader some implementation of IReader
     * @return created statemachine
     */
    ILexer getLexer(String type, IReader reader);
}
