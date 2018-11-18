package it.sevenbits.formatter.lexer.token.implementations;

import it.sevenbits.formatter.lexer.token.IToken;

/**
 * Implementation of IToken
 * */
public class Token implements IToken {
    private String name;
    private String lexeme;

    /**
     * Forbidden empty constructor
     * */
    private Token() {
    }

    /**
     * Creating token by token name and lexeme
     * @param name name of token
     * @param lexeme name of lexeme
     * */
    public Token(final String name, final String lexeme) {
        this.name = name;
        this.lexeme = lexeme;
    }

    /**
     * @return name of token
     * */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @return name of lexeme
     * */
    @Override
    public String getLexeme() {
        return lexeme;
    }
}
