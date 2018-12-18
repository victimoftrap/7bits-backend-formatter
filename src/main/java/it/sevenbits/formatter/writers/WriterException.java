package it.sevenbits.formatter.writers;

/**
 * Exception class for IWriter
 */
public class WriterException extends Exception {
    /**
     * Default exception constructor
     */
    public WriterException() {
    }

    /**
     * Exception constructor
     *
     * @param s - description message
     */
    public WriterException(final String s) {
        super(s);
    }

    /**
     * Exception constructor
     *
     * @param throwable - previous exception
     */
    public WriterException(final Throwable throwable) {
        super(throwable);
    }

    /**
     * Exception constructor
     *
     * @param s         - description message
     * @param throwable - previous exception
     */
    public WriterException(final String s, final Throwable throwable) {
        super(s, throwable);
    }
}
