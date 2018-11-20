package it.sevenbits.formatter.exceptions;

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
    public LexerException(String s) {
        super(s);
    }

    /**
     * Exception constructor
     * @param s - description message
     * @param throwable - previous exception
     * */
    public LexerException(String s, Throwable throwable) {
        super(s, throwable);
    }

    /**
     * Exception constructor
     * @param throwable - previous exception
     * */
    public LexerException(Throwable throwable) {
        super(throwable);
    }
}
