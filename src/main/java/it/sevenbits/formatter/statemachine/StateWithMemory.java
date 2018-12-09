package it.sevenbits.formatter.statemachine;

import java.util.Objects;

public class StateWithMemory extends State {
    private int indentLevel;

    /**
     * Create state with indent counter
     *
     * @param currentState name of state
     * @param indentLevel  current indent level
     */
    public StateWithMemory(final String currentState, final int indentLevel) {
        super(currentState);
        this.indentLevel = indentLevel;
    }

    /**
     * Get current indent level
     *
     * @return current indent level
     */
    public int getIndentLevel() {
        return indentLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StateWithMemory that = (StateWithMemory) o;
        return indentLevel == that.indentLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), indentLevel);
    }
}
