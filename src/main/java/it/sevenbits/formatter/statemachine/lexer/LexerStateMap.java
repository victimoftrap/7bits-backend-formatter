package it.sevenbits.formatter.lexer.lexer;

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

    /**
     * Create map of transitions
     */
    public LexerStateMap() {
        stateMap = new HashMap<>();

        State idState = new State("ID");
        State semicolonState = new State("SEMICOLON");
        State curlyLeftBraceState = new State("CURLY_LEFT_BRACE");
        State curlyRightBraceState = new State("CURLY_RIGHT_BRACE");
        State waitAfterState = new State("WAIT AFTER");
        State releaseState = new State("RELEASE");
        State stringLiteralState = new State("STRING");

        State slashState = new State("SLASH", "DIVIDE");
        State inlineCommentState = new State("//", "INLINE_COMMENT");
        State multiLineCommentState = new State("ASTERISK", "MULTILINE_COMMENT");
        State lastAsteriskState = new State("LAST_ASTERISK", multiLineCommentState.getType());

        stateMap.put(new Pair<>(defaultState, ' '), defaultState);
        stateMap.put(new Pair<>(defaultState, '\n'), defaultState);
        stateMap.put(new Pair<>(defaultState, '\"'), stringLiteralState);
        stateMap.put(new Pair<>(defaultState, ';'), semicolonState);
        stateMap.put(new Pair<>(defaultState, '{'), curlyLeftBraceState);
        stateMap.put(new Pair<>(defaultState, '}'), curlyRightBraceState);
        stateMap.put(new Pair<>(defaultState, null), idState);
        /**Насколько легально делать ключ или часть ключа null, чтобы сделать какое-то значение по-умаолчанию?
         * Долго думал над какими-нибудь другими возможными решениями, но ничего дельного из них не вышло.*/

        // sample text
        stateMap.put(new Pair<>(idState, ' '), waitAfterState);
        stateMap.put(new Pair<>(idState, '\n'), waitAfterState);
        stateMap.put(new Pair<>(idState, ';'), releaseState);
        stateMap.put(new Pair<>(idState, '{'), releaseState);
        stateMap.put(new Pair<>(idState, '}'), releaseState);
        stateMap.put(new Pair<>(idState, null), idState);

        // ;
        stateMap.put(new Pair<>(semicolonState, ' '), waitAfterState);
        stateMap.put(new Pair<>(semicolonState, '\n'), waitAfterState);
        stateMap.put(new Pair<>(semicolonState, null), releaseState);

        // {
        stateMap.put(new Pair<>(curlyLeftBraceState, ' '), waitAfterState);
        stateMap.put(new Pair<>(curlyLeftBraceState, '\n'), waitAfterState);
        stateMap.put(new Pair<>(curlyLeftBraceState, null), releaseState);

        // }
        stateMap.put(new Pair<>(curlyRightBraceState, ' '), waitAfterState);
        stateMap.put(new Pair<>(curlyRightBraceState, '\n'), waitAfterState);
        stateMap.put(new Pair<>(curlyRightBraceState, null), releaseState);

        // avoiding spaces
        stateMap.put(new Pair<>(waitAfterState, ' '), waitAfterState);
        stateMap.put(new Pair<>(waitAfterState, '\n'), waitAfterState);
        stateMap.put(new Pair<>(waitAfterState, null), releaseState);

        // "sample text"
        stateMap.put(new Pair<>(stringLiteralState, null), stringLiteralState);
        stateMap.put(new Pair<>(stringLiteralState, '\"'), waitAfterState);

        // comments transitions
        stateMap.put(new Pair<>(defaultState, '/'), slashState);

        stateMap.put(new Pair<>(slashState, ' '), waitAfterState);
        stateMap.put(new Pair<>(slashState, '\n'), waitAfterState);
        stateMap.put(new Pair<>(slashState, null), releaseState);
        stateMap.put(new Pair<>(slashState, '/'), inlineCommentState);
        stateMap.put(new Pair<>(slashState, '*'), multiLineCommentState);

        stateMap.put(new Pair<>(inlineCommentState, '\n'), waitAfterState);
        stateMap.put(new Pair<>(inlineCommentState, null), inlineCommentState);

        stateMap.put(new Pair<>(multiLineCommentState, null), multiLineCommentState);
        stateMap.put(new Pair<>(multiLineCommentState, '*'), lastAsteriskState);

        stateMap.put(new Pair<>(lastAsteriskState, '/'), waitAfterState);
        stateMap.put(new Pair<>(lastAsteriskState, null), multiLineCommentState);
    }

    /**
     * @return start state of automata
     */
    @Override
    public State getStartState() {
        return defaultState;
    }

    /**
     * Get next state of automata
     *
     * @param state       current state
     * @param currentChar current char
     * @return next state
     */
    @Override
    public State getNextState(final State state, final char currentChar) {
        State next = stateMap.get(new Pair<>(state, currentChar));
        if (next == null) {
            return stateMap.get(new Pair<>(state, null));
        }
        return next;
    }
}
