package it.sevenbits.formatter.lexer;

/**
 * Exception class ILexer
 * */
public class LexerException extends Exception {
    /**
     * Default exception constructor
     * */
    public LexerException() {
    }

    /**
     * Exception constructor
     * @param s - description message
     * */
    public LexerException(final String s) {
        super(s);
    }

    /**
     * Exception constructor
     * @param s - description message
     * @param throwable - previous exception
     * */
    public LexerException(final String s, final Throwable throwable) {
        super(s, throwable);
    }

    /**
     * Exception constructor
     * @param throwable - previous exception
     * */
    public LexerException(final Throwable throwable) {
        super(throwable);
    }
}
