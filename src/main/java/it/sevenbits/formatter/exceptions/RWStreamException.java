package it.sevenbits.formatter.exceptions;

/**
 * Exception class for IReader and IWriter
 * */
public class RWStreamException extends Exception {
    /**
     * Default exception constructor
     * */
    public RWStreamException() {
    }

    /**
     * Exception constructor
     * @param s - description message
     * */
    public RWStreamException(final String s) {
        super(s);
    }

    /**
     * Exception constructor
     * @param s - description message
     * @param throwable - previous exception
     * */
    public RWStreamException(final String s, final Throwable throwable) {
        super(s, throwable);
    }
}
