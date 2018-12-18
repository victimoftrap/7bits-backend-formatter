package it.sevenbits.formatter.lexer.statemachine;

import java.util.Objects;

/**
 * Class of automata's state
 */
public class LexerState {
    private final String name;
    private String type;

    /**
     * Create state
     *
     * @param name name of state
     */
    public LexerState(final String name) {
        this.name = name;
        type = name;
    }

    /**
     * Create state
     *
     * @param name name of state
     * @param type type of lexeme in this state
     */
    public LexerState(final String name, final String type) {
        this.name = name;
        this.type = type;
    }

    /**
     * @return name of state
     */
    public String toString() {
        return name;
    }

    /**
     * Get type of lexeme from this state
     *
     * @return type of state
     */
    public String getType() {
        return type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LexerState state = (LexerState) o;
        return Objects.equals(name, state.name) &&
                Objects.equals(type, state.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
