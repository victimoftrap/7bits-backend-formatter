package it.sevenbits.formatter.statemachine;

import java.util.Objects;

/**
 * Class of state that could save indent level
 * */
public class StateWithMemory extends State {
    private int indentLevel;

    /**
     * Create state with indent counter
     *
     * @param name        name of state
     * @param indentLevel current indent level
     */
    public StateWithMemory(final String name, final int indentLevel) {
        super(name);
        this.indentLevel = indentLevel;
    }

    /**
     * Create state
     *
     * @param name        name of state
     * @param type        type of lexeme in this state
     * @param indentLevel current indent level
     */
    public StateWithMemory(final String name, final String type, final int indentLevel) {
        super(name, type);
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
    public boolean equals(final Object o) {
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
