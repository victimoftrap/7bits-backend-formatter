package it.sevenbits.formatter.statemachine.lexer;

import it.sevenbits.formatter.lexer.token.IToken;

/**
 * Context for lexer
 * */
public class TokenBuilderContext {
    private StringBuilder tokenBuffer;
    private char currentChar;
    private char nextLexemeChar;
    private String currentStateType;
    private IToken token;

    public TokenBuilderContext() {
        tokenBuffer = new StringBuilder();
    }

    /**
     * Creating context
     *
     * @param tokenBuffer buffer with token chars
     * @param currentChar currently reading char
     * */
    /*public TokenBuilderContext(final StringBuilder tokenBuffer, final char currentChar) {
        this.tokenBuffer = tokenBuffer;
        this.currentChar = currentChar;
    }*/

    /**
     * @return buffer with tokens char
     * */
    public StringBuilder getTokenBuffer() {
        return tokenBuffer;
    }

    /**
     * Set new buffer for token
     * @param tokenBuffer buffer with token chars
     * */
    public void setTokenBuffer(final StringBuilder tokenBuffer) {
        this.tokenBuffer = tokenBuffer;
    }

    /**
     * @return current char
     * */
    public char getCurrentChar() {
        return currentChar;
    }

    /**
     * Set current char
     * @param currentChar from stream
     * */
    public void setCurrentChar(final char currentChar) {
        this.currentChar = currentChar;
    }

    /**
     * @return created token
     * */
    public IToken getToken() {
        return token;
    }

    /**
     * Set token
     * @param token generated from chars buffer
     * */
    public void setToken(final IToken token) {
        this.token = token;
    }

    /**
     * @return type of state
     * */
    public String getCurrentStateType() {
        return currentStateType;
    }

    /**
     * Set type of state
     * @param currentStateType type of state
     * */
    public void setCurrentStateType(final String currentStateType) {
        this.currentStateType = currentStateType;
    }

    public char getNextLexemeChar() {
        return nextLexemeChar;
    }

    public void setNextLexemeChar(final char nextLexemeChar) {
        this.nextLexemeChar = nextLexemeChar;
    }
}
