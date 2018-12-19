package it.sevenbits.formatter.formatter.statemachine.transitions;

import it.sevenbits.formatter.formatter.statemachine.Pair;
import it.sevenbits.formatter.formatter.statemachine.FormatterState;

import java.util.HashMap;
import java.util.Map;

/**
 * Class, collected formatter states
 */
public class FormatterStateMap implements IFormatterStateMap {
    private Map<Pair<FormatterState, String>, FormatterState> stateMap;
    private FormatterState defaultState = new FormatterState("START");
    private String CURLY_LEFT_BRACE = "CURLY_LEFT_BRACE";
    private String CURLY_RIGHT_BRACE = "CURLY_RIGHT_BRACE";
    private String SEMICOLON = "SEMICOLON";
    private String STRING = "STRING";
    private String INLINE_COMMENT = "INLINE_COMMENT";
    private String MULTILINE_COMMENT = "MULTILINE_COMMENT";

    /**
     * Create state map for formatter
     */
    public FormatterStateMap() {
        stateMap = new HashMap<>();
        FormatterState listenState = new FormatterState("LISTEN");
        FormatterState semicolonState = new FormatterState(SEMICOLON);
        FormatterState curlyLeftBraceState = new FormatterState(CURLY_LEFT_BRACE);
        FormatterState curlyRightBraceState = new FormatterState(CURLY_RIGHT_BRACE);
        FormatterState stringLiteralState = new FormatterState(STRING);
        FormatterState inlineCommentState = new FormatterState(INLINE_COMMENT);
        FormatterState multiLineCommentState = new FormatterState(MULTILINE_COMMENT);

        stateMap.put(new Pair<>(defaultState, null), listenState);
        stateMap.put(new Pair<>(defaultState, INLINE_COMMENT), inlineCommentState);
        stateMap.put(new Pair<>(defaultState, MULTILINE_COMMENT), multiLineCommentState);

        stateMap.put(new Pair<>(listenState, null), listenState);
        stateMap.put(new Pair<>(listenState, CURLY_LEFT_BRACE), curlyLeftBraceState);
        stateMap.put(new Pair<>(listenState, INLINE_COMMENT), inlineCommentState);
        stateMap.put(new Pair<>(listenState, MULTILINE_COMMENT), multiLineCommentState);
        stateMap.put(new Pair<>(listenState, SEMICOLON), semicolonState);
        stateMap.put(new Pair<>(listenState, CURLY_RIGHT_BRACE), curlyRightBraceState);

        stateMap.put(new Pair<>(curlyLeftBraceState, null), listenState);
        stateMap.put(new Pair<>(curlyLeftBraceState, INLINE_COMMENT), inlineCommentState);
        stateMap.put(new Pair<>(curlyLeftBraceState, MULTILINE_COMMENT), multiLineCommentState);

        stateMap.put(new Pair<>(semicolonState, null), listenState);
        stateMap.put(new Pair<>(semicolonState, INLINE_COMMENT), inlineCommentState);
        stateMap.put(new Pair<>(semicolonState, MULTILINE_COMMENT), multiLineCommentState);
        stateMap.put(new Pair<>(semicolonState, CURLY_RIGHT_BRACE), curlyRightBraceState);

        stateMap.put(new Pair<>(curlyRightBraceState, null), listenState);
        stateMap.put(new Pair<>(curlyRightBraceState, INLINE_COMMENT), inlineCommentState);
        stateMap.put(new Pair<>(curlyRightBraceState, MULTILINE_COMMENT), multiLineCommentState);

        stateMap.put(new Pair<>(inlineCommentState, null), listenState);
        stateMap.put(new Pair<>(inlineCommentState, CURLY_LEFT_BRACE), curlyLeftBraceState);
        stateMap.put(new Pair<>(inlineCommentState, CURLY_RIGHT_BRACE), curlyRightBraceState);

        stateMap.put(new Pair<>(multiLineCommentState, null), listenState);
    }

    /**
     * @return start state of automata
     */
    @Override
    public FormatterState getStartState() {
        return defaultState;
    }

    /**
     * @param state     current state
     * @param tokenType type of current token
     * @return next state of automata
     */
    @Override
    public FormatterState getNextState(final FormatterState state, final String tokenType) {
        FormatterState next = stateMap.get(new Pair<>(state, tokenType));
        if (next == null) {
            return stateMap.get(new Pair<>(state, null));
        }
        return next;
    }
}
