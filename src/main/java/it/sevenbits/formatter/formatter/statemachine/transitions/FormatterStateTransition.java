package it.sevenbits.formatter.formatter.statemachine.transitions;

import it.sevenbits.formatter.formatter.statemachine.FormatterState;

/**
 * LexerState transition for formatter
 */
public class FormatterStateTransition {
    private IFormatterStateMap stateMap;

    /**
     * Create transition by state map
     *
     * @param stateMap implementation of IFormatterStateMap
     */
    public FormatterStateTransition(final IFormatterStateMap stateMap) {
        this.stateMap = stateMap;
    }

    /**
     * @return start state of automata
     */
    public FormatterState getStartState() {
        return stateMap.getStartState();
    }

    /**
     * @param state     current state
     * @param tokenType type of current token
     * @return next state of automata
     */
    public FormatterState getNextState(final FormatterState state, final String tokenType) {
        return stateMap.getNextState(state, tokenType);
    }
}
