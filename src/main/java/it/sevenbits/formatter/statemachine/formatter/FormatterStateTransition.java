package it.sevenbits.formatter.statemachine.formatter;

import it.sevenbits.formatter.statemachine.State;

/**
 * State transition for formatter
 */
public class FormatterStateTransition {
    private IStateMap stateMap;

    /**
     * Create transition by state map
     *
     * @param stateMap implementation of IStateMap
     */
    public FormatterStateTransition(final IStateMap stateMap) {
        this.stateMap = stateMap;
    }

    /**
     * @return start state of automata
     */
    public State getStartState() {
        return stateMap.getStartState();
    }

    /**
     * @param state     current state
     * @param tokenType type of current token
     * @return next state of automata
     */
    public State getNextState(final State state, final String tokenType) {
        return stateMap.getNextState(state, tokenType);
    }
}
