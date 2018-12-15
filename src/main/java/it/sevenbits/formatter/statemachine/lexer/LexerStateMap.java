package it.sevenbits.formatter.statemachine.lexer;

import it.sevenbits.formatter.statemachine.Pair;
import it.sevenbits.formatter.statemachine.State;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that holds transitions on lexer state machine
 */
public class LexerStateMap implements ILexerStateMap {
    private Map<Pair<State, Character>, State> stateMap;
    private State defaultState = new State("WAIT BEFORE");
    private String ID = "ID";
    private String CURLY_LEFT_BRACE = "CURLY LEFT BRACKET";
    private String SEMICOLON = "SEMICOLON";

    /**
     * Create map of transitions
     */
    public LexerStateMap() {
        stateMap = new HashMap<>();
        State idState = new State(ID);
        State semicolonState = new State(SEMICOLON);
        State curlyLeftBraceState = new State(CURLY_LEFT_BRACE);
        State waitAfterState = new State("WAIT AFTER");
        State releaseState = new State("RELEASE");
        State stringLiteralState = new State("STRING");

        stateMap.put(new Pair<>(defaultState, ' '), defaultState);
        stateMap.put(new Pair<>(defaultState, '\n'), defaultState);
        stateMap.put(new Pair<>(defaultState, '\"'), stringLiteralState);
        stateMap.put(new Pair<>(defaultState, ';'), semicolonState);
        stateMap.put(new Pair<>(defaultState, '{'), curlyLeftBraceState);
        stateMap.put(new Pair<>(defaultState, null), idState);
        /**Насколько легально делать ключ или часть ключа null, чтобы сделать какое-то значение по-умаолчанию?
         * Долго думал над какими-нибудь другими возможными решениями, но ничего дельного из них не вышло.*/

        stateMap.put(new Pair<>(idState, ' '), waitAfterState);
        stateMap.put(new Pair<>(idState, '\n'), waitAfterState);
        stateMap.put(new Pair<>(idState, ';'), releaseState);
        stateMap.put(new Pair<>(idState, '{'), releaseState);
        stateMap.put(new Pair<>(idState, null), idState);

        stateMap.put(new Pair<>(semicolonState, ' '), waitAfterState);
        stateMap.put(new Pair<>(semicolonState, '\n'), waitAfterState);
        stateMap.put(new Pair<>(semicolonState, null), releaseState);

        stateMap.put(new Pair<>(curlyLeftBraceState, ' '), waitAfterState);
        stateMap.put(new Pair<>(curlyLeftBraceState, '\n'), waitAfterState);
        stateMap.put(new Pair<>(curlyLeftBraceState, null), releaseState);

        stateMap.put(new Pair<>(waitAfterState, ' '), waitAfterState);
        stateMap.put(new Pair<>(waitAfterState, '\n'), waitAfterState);
        stateMap.put(new Pair<>(waitAfterState, null), waitAfterState);

        stateMap.put(new Pair<>(stringLiteralState, null), stringLiteralState);
        stateMap.put(new Pair<>(stringLiteralState, '\"'), waitAfterState);
    }

    @Override
    public State getStartState() {
        return defaultState;
    }

    @Override
    public State getNextState(final State state, final char currentChar) {
        State next = stateMap.get(new Pair<>(state, currentChar));
        if (next == null) {
            return stateMap.get(new Pair<>(state, null));
        }
        return next;
    }
}
