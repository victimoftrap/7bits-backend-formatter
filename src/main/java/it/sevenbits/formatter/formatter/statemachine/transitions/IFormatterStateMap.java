package it.sevenbits.formatter.formatter.statemachine.transitions;

import it.sevenbits.formatter.formatter.statemachine.FormatterState;

/**
 * Interface for formatter state machine map
 */
public interface IFormatterStateMap {
    /**
     * @return start state of automata
     */
    FormatterState getStartState();

    /**
     * Get next state of automata by current state and type of current token
     *
     * @param state     current state
     * @param tokenType type of current token
     * @return next state of automata
     */
    FormatterState getNextState(FormatterState state, String tokenType);
}
