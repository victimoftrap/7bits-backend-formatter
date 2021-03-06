package it.sevenbits.formatter.lexer;

import it.sevenbits.formatter.lexer.token.IToken;

/**
 * Interface of lexical analyzer
 */
public interface ILexer {

    /**
     * @return IToken next token from source
     * @throws LexerException if some trouble with state machine happen
     */
    IToken readToken() throws LexerException;

    /**
     * @return true if state machine can generate token
     */
    boolean hasNext();
}
