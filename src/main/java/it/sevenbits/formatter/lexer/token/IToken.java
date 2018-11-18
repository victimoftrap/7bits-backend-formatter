package it.sevenbits.formatter.lexer.token;

/**
 * Interface for lexical analyzer's tokens
 * */
public interface IToken {

    /**
     * @return token name
     * */
    String getName();

    /**
     * @return built lexeme
     * */
    String getLexeme();
}
