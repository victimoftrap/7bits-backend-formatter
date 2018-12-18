package it.sevenbits.formatter.formatter.statemachine;

import java.util.Objects;

/**
 * Special class for formatter state
 * */
public class FormatterState {
    private final String name;

    /**
     * Create formatter state
     *
     * @param name name of state
     */
    public FormatterState(final String name) {
        this.name = name;
    }

    /**
     * @return name of formatter state
     */
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormatterState that = (FormatterState) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
