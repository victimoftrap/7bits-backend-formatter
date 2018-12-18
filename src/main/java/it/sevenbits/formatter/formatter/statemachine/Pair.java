package it.sevenbits.formatter.formatter.statemachine;

import java.util.Objects;

/**
 * Class for collecting pair of some objects
 *
 * @param <T> first object
 * @param <U> second object
 */
public final class Pair<T, U> {
    private T first;
    private U second;

    private Pair() {
    }

    /**
     * Create pair of some elements
     *
     * @param first  element
     * @param second element
     */
    public Pair(final T first, final U second) {
        this.first = first;
        this.second = second;
    }

    /**
     * @return first element
     */
    public T getFirst() {
        return first;
    }

    /**
     * @return second element
     */
    public U getSecond() {
        return second;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) &&
                Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
