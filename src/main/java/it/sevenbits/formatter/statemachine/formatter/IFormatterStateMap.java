package it.sevenbits.formatter.statemachine.formatter;

import it.sevenbits.formatter.statemachine.State;

/**
 * Interface for map of states of automata
 */
public interface IFormatterStateMap {
    /**
     * @return start state of automata
     */
    State getStartState();

    /**
     * Get next state of automata by current state and type of current token
     *
     * @param state     current state
     * @param tokenType type of current token
     * @return next state of automata
     */
    State getNextState(State state, String tokenType);
}
