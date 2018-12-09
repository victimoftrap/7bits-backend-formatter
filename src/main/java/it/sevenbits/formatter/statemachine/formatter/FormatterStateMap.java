package it.sevenbits.formatter.statemachine.formatter;

import it.sevenbits.formatter.statemachine.Pair;
import it.sevenbits.formatter.statemachine.State;

import java.util.HashMap;
import java.util.Map;

/**
 * Class, collected formatter's states
 */
public class FormatterStateMap implements IStateMap {
    private Map<Pair<State, String>, State> stateMap;
    private State defaultState = new State("LISTEN");
    private String CURLY_LEFT_BRACE = "CURLY LEFT BRACKET";
    private String SEMICOLON = "SEMICOLON";

    /**
     * Create state map for formatter
     */
    public FormatterStateMap() {
        stateMap = new HashMap<>();
        State idState = new State("ID");
        State clbState = new State("CLB");
        State semicolonState = new State("SEMICOLON");

        stateMap.put(new Pair<>(defaultState, "ID_OR_KEYWORD"), idState);
        stateMap.put(new Pair<>(defaultState, CURLY_LEFT_BRACE), clbState);

        stateMap.put(new Pair<>(idState, "ID_OR_KEYWORD"), idState);
        stateMap.put(new Pair<>(idState, SEMICOLON), semicolonState);

        stateMap.put(new Pair<>(clbState, "ID_OR_KEYWORD"), defaultState);

        stateMap.put(new Pair<>(semicolonState, "ID_OR_KEYWORD"), defaultState);
    }

    /**
     * @return start state of automata
     */
    @Override
    public State getStartState() {
        return defaultState;
    }

    /**
     * @param state     current state
     * @param tokenType type of current token
     * @return next state of automata
     */
    @Override
    public State getNextState(final State state, final String tokenType) {
        return stateMap.get(new Pair<>(state, tokenType));
    }
}
