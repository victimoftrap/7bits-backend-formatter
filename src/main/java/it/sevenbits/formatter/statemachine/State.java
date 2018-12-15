package it.sevenbits.formatter.statemachine;

import java.util.Objects;

/**
 * Class of automata's state
 */
public class State {
    private final String currentState;
    private String type;

    /**
     * Create state
     *
     * @param currentState name of state
     */
    public State(final String currentState) {
        this.currentState = currentState;
        type = currentState;
    }

    /**
     * Create state
     *
     * @param currentState name of state
     * @param type         type of lexeme in this state
     */
    public State(final String currentState, final String type) {
        this.currentState = currentState;
        this.type = type;
    }

    /**
     * @return name of state
     */
    public String toString() {
        return currentState;
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
        State state = (State) o;
        return Objects.equals(currentState, state.currentState) &&
                Objects.equals(type, state.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentState, type);
    }
}
