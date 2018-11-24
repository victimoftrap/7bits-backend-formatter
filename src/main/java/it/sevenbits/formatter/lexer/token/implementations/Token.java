package it.sevenbits.formatter.lexer.token.implementations;

import it.sevenbits.formatter.lexer.token.IToken;

import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Token token = (Token) o;
        return Objects.equals(name, token.name) &&
                Objects.equals(lexeme, token.lexeme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lexeme);
    }
}
