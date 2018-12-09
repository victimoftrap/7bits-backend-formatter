package it.sevenbits.formatter.statemachine.formatter;

import it.sevenbits.formatter.lexer.token.IToken;
import it.sevenbits.formatter.writers.IWriter;

import java.util.Objects;

/**
 * Class-context for formatter
 */
public class TokenContext {
    private IWriter writer;
    private IToken token;

    /**
     * Create context class for formatter
     *
     * @param writer stream with previously written tokens
     * @param token  current token
     */
    public TokenContext(final IWriter writer, final IToken token) {
        this.writer = writer;
        this.token = token;
    }

    /**
     * @return stream with previously written tokens
     */
    public IWriter getWriter() {
        return writer;
    }

    /**
     * Set writer's stream
     *
     * @param writer previously written tokens
     */
    public void setWriter(final IWriter writer) {
        this.writer = writer;
    }

    /**
     * @return current token
     */
    public IToken getToken() {
        return token;
    }

    /**
     * Set current token
     *
     * @param token current token
     */
    public void setToken(final IToken token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenContext that = (TokenContext) o;
        return Objects.equals(writer, that.writer) &&
                Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(writer, token);
    }
}
