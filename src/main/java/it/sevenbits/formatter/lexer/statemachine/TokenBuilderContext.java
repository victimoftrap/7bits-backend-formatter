package it.sevenbits.formatter.lexer.statemachine;

import it.sevenbits.formatter.lexer.token.IToken;

/**
 * Context for lexer. Contains information about current char, current state and buffers for creating token
 */
public class TokenBuilderContext {
    private char currentChar;
    private char nextLexemeChar;
    /* Это получилось из-за того, что автомат будет съедать пробелы после получения токена до тех пор,
     пока не встретит символ какой-то следующей лексемы или пока поток не закончится. Придётся тоже так хранить*/
    private String stateType;
    private String lexemeType;
    /* Тип состояния будет меняться на каждом проходе по автомату.
    Как сделать так, чтобы можно было хранить только один тип лексемы я не придумал,
    так как тип состояния не всегда равен типу лексемы.
    Поэтому на тех состояниях, где тип уже точно будет известен, придётся записывать тип в специальную переменную*/
    private StringBuilder lexemeBuffer;
    private IToken readyToken;

    /**
     * Creating empty token
     */
    public TokenBuilderContext() {
        lexemeBuffer = new StringBuilder();
    }

    /**
     * @return buffer with lexeme chars
     */
    public StringBuilder getLexemeBuffer() {
        return lexemeBuffer;
    }

    /**
     * Set new buffer for readyToken
     *
     * @param lexemeBuffer buffer with readyToken chars
     */
    public void setLexemeBuffer(final StringBuilder lexemeBuffer) {
        this.lexemeBuffer = lexemeBuffer;
    }

    /**
     * @return current char
     */
    public char getCurrentChar() {
        return currentChar;
    }

    /**
     * Set current char
     *
     * @param currentChar from stream
     */
    public void setCurrentChar(final char currentChar) {
        this.currentChar = currentChar;
    }

    /**
     * @return created readyToken
     */
    public IToken getToken() {
        return readyToken;
    }

    /**
     * Set readyToken
     *
     * @param token generated from chars buffer
     */
    public void setToken(final IToken token) {
        this.readyToken = token;
    }

    /**
     * @return type of state
     */
    public String getStateType() {
        return stateType;
    }

    /**
     * Set type of state
     *
     * @param stateType type of state
     */
    public void setStateType(final String stateType) {
        this.stateType = stateType;
    }

    /**
     * @return char from next lexeme
     */
    public char getNextLexemeChar() {
        return nextLexemeChar;
    }

    /**
     * Save char from next unknown lexeme.
     *
     * @param nextLexemeChar lexeme char
     */
    public void setNextLexemeChar(final char nextLexemeChar) {
        this.nextLexemeChar = nextLexemeChar;
    }

    /**
     * @return type of lexeme, that statemachine would create in one iteration
     */
    public String getLexemeType() {
        return lexemeType;
    }

    /**
     * Save type of lexeme
     *
     * @param lexemeType type of current lexeme
     */
    public void setLexemeType(final String lexemeType) {
        this.lexemeType = lexemeType;
    }
}
