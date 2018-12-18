package it.sevenbits.formatter.formatter.statemachine;

import it.sevenbits.formatter.lexer.token.IToken;
import it.sevenbits.formatter.writers.IWriter;

import java.util.Objects;

/**
 * Class-context for formatter
 */
public class TokenContext {
    private IWriter writer;
    private IToken token;
    private int indentLevel;
    private final int INDENT_SIZE = 4;

    /**
     * Create default context
     */
    public TokenContext() {
    }

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

    /**
     * Get current indent level
     *
     * @return current indent
     */
    public int getIndentLevel() {
        return indentLevel;
    }

    /**
     * Set indent level
     *
     * @param indentLevel current indent
     */
    public void setIndentLevel(final int indentLevel) {
        this.indentLevel = indentLevel;
    }

    /**
     * Get indent size
     *
     * @return standard indent size
     */
    public int getIndentSize() {
        return INDENT_SIZE;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TokenContext that = (TokenContext) o;
        return Objects.equals(writer, that.writer) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(writer, token);
    }
}
