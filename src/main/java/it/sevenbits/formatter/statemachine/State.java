package it.sevenbits.formatter.statemachine;

import java.util.Objects;

/**
 * Class of automata's state
 */
public class State {
    private final String currentState;

    /**
     * Create state
     *
     * @param currentState name of state
     */
    public State(final String currentState) {
        this.currentState = currentState;
    }

    /**
     * @return name of state
     */
    public String toString() {
        return currentState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(currentState, state.currentState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentState);
    }
}
