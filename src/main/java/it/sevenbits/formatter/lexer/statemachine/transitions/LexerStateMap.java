package it.sevenbits.formatter.lexer.statemachine.transitions;

import it.sevenbits.formatter.lexer.statemachine.Pair;
import it.sevenbits.formatter.lexer.statemachine.LexerState;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that holds transitions on lexer state machine
 */
public class LexerStateMap implements ILexerStateMap {
    private Map<Pair<LexerState, Character>, LexerState> stateMap;
    private LexerState waitBeforeState = new LexerState("WAIT BEFORE");

    /**
     * Create map of transitions
     */
    public LexerStateMap() {
        stateMap = new HashMap<>();

        LexerState idState = new LexerState("ID");
        LexerState semicolonState = new LexerState("SEMICOLON");
        LexerState curlyLeftBraceState = new LexerState("CURLY_LEFT_BRACE");
        LexerState curlyRightBraceState = new LexerState("CURLY_RIGHT_BRACE");
        LexerState waitAfterState = new LexerState("WAIT AFTER");
        LexerState releaseState = new LexerState("RELEASE");
        LexerState stringLiteralState = new LexerState("STRING");

        LexerState slashState = new LexerState("SLASH", "DIVIDE");
        LexerState inlineCommentState = new LexerState("//", "INLINE_COMMENT");
        LexerState multiLineCommentState = new LexerState("ASTERISK", "MULTILINE_COMMENT");
        LexerState lastAsteriskState = new LexerState("LAST_ASTERISK", multiLineCommentState.getType());

        LexerState roundLeftBraceState = new LexerState("ROUND_LEFT_BRACE");
        LexerState roundRightBraceState = new LexerState("ROUND_RIGHT_BRACE");
        LexerState commaState = new LexerState("COMMA");

        stateMap.put(new Pair<>(waitBeforeState, ' '), waitBeforeState);
        stateMap.put(new Pair<>(waitBeforeState, '\n'), waitBeforeState);
        stateMap.put(new Pair<>(waitBeforeState, '\"'), stringLiteralState);
        stateMap.put(new Pair<>(waitBeforeState, ';'), semicolonState);
        stateMap.put(new Pair<>(waitBeforeState, '{'), curlyLeftBraceState);
        stateMap.put(new Pair<>(waitBeforeState, '}'), curlyRightBraceState);
        stateMap.put(new Pair<>(waitBeforeState, '/'), slashState);

        stateMap.put(new Pair<>(waitBeforeState, '('), roundLeftBraceState);
        stateMap.put(new Pair<>(waitBeforeState, ')'), roundRightBraceState);
        stateMap.put(new Pair<>(waitBeforeState, ','), commaState);

        stateMap.put(new Pair<>(waitBeforeState, null), idState);
        /*Насколько легально делать ключ или часть ключа null, чтобы сделать какое-то значение по-умаолчанию?
         * Долго думал над какими-нибудь другими возможными решениями, но ничего дельного из них не вышло.*/

        // sample text
        stateMap.put(new Pair<>(idState, ' '), waitAfterState);
        stateMap.put(new Pair<>(idState, '\n'), waitAfterState);
        stateMap.put(new Pair<>(idState, ';'), releaseState);
        stateMap.put(new Pair<>(idState, '{'), releaseState);
        stateMap.put(new Pair<>(idState, '}'), releaseState);
        stateMap.put(new Pair<>(idState, null), idState);
        stateMap.put(new Pair<>(idState, '('), releaseState);
        stateMap.put(new Pair<>(idState, ')'), releaseState);
        stateMap.put(new Pair<>(idState, ','), releaseState);

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
        stateMap.put(new Pair<>(slashState, ' '), waitAfterState);
        stateMap.put(new Pair<>(slashState, '\n'), waitAfterState);
        stateMap.put(new Pair<>(slashState, null), releaseState);
        stateMap.put(new Pair<>(slashState, '/'), inlineCommentState);
        stateMap.put(new Pair<>(slashState, '*'), multiLineCommentState);

        stateMap.put(new Pair<>(inlineCommentState, '\n'), waitAfterState);
        stateMap.put(new Pair<>(inlineCommentState, null), inlineCommentState);

        stateMap.put(new Pair<>(multiLineCommentState, '*'), lastAsteriskState);
        stateMap.put(new Pair<>(multiLineCommentState, null), multiLineCommentState);

        stateMap.put(new Pair<>(lastAsteriskState, '/'), waitAfterState);
        stateMap.put(new Pair<>(lastAsteriskState, null), multiLineCommentState);

        // (
        stateMap.put(new Pair<>(roundLeftBraceState, ' '), waitAfterState);
        stateMap.put(new Pair<>(roundLeftBraceState, '\n'), waitAfterState);
        stateMap.put(new Pair<>(roundLeftBraceState, null), releaseState);

        // )
        stateMap.put(new Pair<>(roundRightBraceState, ' '), waitAfterState);
        stateMap.put(new Pair<>(roundRightBraceState, '\n'), waitAfterState);
        stateMap.put(new Pair<>(roundRightBraceState, null), releaseState);

        // ,
        stateMap.put(new Pair<>(commaState, ' '), waitAfterState);
        stateMap.put(new Pair<>(commaState, '\n'), waitAfterState);
        stateMap.put(new Pair<>(commaState, null), releaseState);
    }

    /**
     * @return start state of automata
     */
    @Override
    public LexerState getStartState() {
        return waitBeforeState;
    }

    /**
     * Get next state of automata
     *
     * @param state       current state
     * @param currentChar current char
     * @return next state
     */
    @Override
    public LexerState getNextState(final LexerState state, final char currentChar) {
        LexerState next = stateMap.get(new Pair<>(state, currentChar));
        if (next == null) {
            return stateMap.get(new Pair<>(state, null));
        }
        return next;
    }
}
