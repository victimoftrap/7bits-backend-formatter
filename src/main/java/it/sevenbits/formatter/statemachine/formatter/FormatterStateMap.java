package it.sevenbits.formatter.statemachine.formatter;

import it.sevenbits.formatter.statemachine.Pair;
import it.sevenbits.formatter.statemachine.State;

import java.util.HashMap;
import java.util.Map;

/**
 * Class, collected formatter's states
 */
public class FormatterStateMap implements IFormatterStateMap {
    private Map<Pair<State, String>, State> stateMap;
    private State defaultState = new State("LISTEN");
    private String CURLY_LEFT_BRACE = "CURLY_LEFT_BRACE";
    private String CURLY_RIGHT_BRACE = "CURLY_RIGHT_BRACE";
    private String SEMICOLON = "SEMICOLON";

    /**
     * Create state map for formatter
     */
    public FormatterStateMap() {
        stateMap = new HashMap<>();
        State semicolonState = new State(SEMICOLON);
        State curlyLeftBraceState = new State(CURLY_LEFT_BRACE);
        State curlyRightBraceState = new State(CURLY_RIGHT_BRACE);

        stateMap.put(new Pair<>(defaultState, CURLY_LEFT_BRACE), curlyLeftBraceState);
        stateMap.put(new Pair<>(defaultState, SEMICOLON), semicolonState);
        stateMap.put(new Pair<>(defaultState, CURLY_RIGHT_BRACE), curlyRightBraceState);
        stateMap.put(new Pair<>(defaultState, null), defaultState);

        stateMap.put(new Pair<>(curlyLeftBraceState, null), defaultState);

        stateMap.put(new Pair<>(semicolonState, null), defaultState);
        stateMap.put(new Pair<>(semicolonState, CURLY_RIGHT_BRACE), curlyRightBraceState);

        stateMap.put(new Pair<>(curlyRightBraceState, null), defaultState);
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
        State next = stateMap.get(new Pair<>(state, tokenType));
        if (next == null) {
            return stateMap.get(new Pair<>(state, null));
        }
        return next;
    }
}
