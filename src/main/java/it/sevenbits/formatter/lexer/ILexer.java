package it.sevenbits.formatter.lexer;

import it.sevenbits.formatter.lexer.token.IToken;

/**
 * Interface of lexical analyzer
 * */
public interface ILexer {

    /**
     * @return IToken - next token from source
     * */
    IToken readToken();

    /**
     * @return true if lexer can generate token
     * */
    boolean hasNext();
}
