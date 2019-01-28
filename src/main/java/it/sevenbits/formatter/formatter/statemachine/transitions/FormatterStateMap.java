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
    private final FormatterState startState = new FormatterState("START");

    private String CURLY_LEFT_BRACE = "CURLY_LEFT_BRACE";
    private String CURLY_RIGHT_BRACE = "CURLY_RIGHT_BRACE";
    private String SEMICOLON = "SEMICOLON";
    private String STRING = "STRING";
    private String INLINE_COMMENT = "INLINE_COMMENT";
    private String MULTILINE_COMMENT = "MULTILINE_COMMENT";

    private String ROUND_LEFT_BRACE = "ROUND_LEFT_BRACE";
    private String ROUND_RIGHT_BRACE = "ROUND_RIGHT_BRACE";
    private String COMMA = "COMMA";

    /**
     * Create state map for formatter
     */
    public FormatterStateMap() {
        stateMap = new HashMap<>();
        FormatterState writeState = new FormatterState("WRITE");

        FormatterState semicolonState = new FormatterState(SEMICOLON);
        FormatterState curlyLeftBraceState = new FormatterState(CURLY_LEFT_BRACE);
        FormatterState curlyRightBraceState = new FormatterState(CURLY_RIGHT_BRACE);
        FormatterState stringLiteralState = new FormatterState(STRING);
        FormatterState inlineCommentState = new FormatterState(INLINE_COMMENT);
        FormatterState multiLineCommentState = new FormatterState(MULTILINE_COMMENT);

        FormatterState roundLeftBraceState = new FormatterState(ROUND_LEFT_BRACE);
        FormatterState roundRightBraceState = new FormatterState(ROUND_RIGHT_BRACE);
        FormatterState commaState = new FormatterState(COMMA);

        // start state for comments (if they exists)
        stateMap.put(new Pair<>(startState, null), writeState);
        stateMap.put(new Pair<>(startState, INLINE_COMMENT), inlineCommentState);
        stateMap.put(new Pair<>(startState, MULTILINE_COMMENT), multiLineCommentState);

        // state that writes all id and literal tokens
        stateMap.put(new Pair<>(writeState, null), writeState);
        stateMap.put(new Pair<>(writeState, CURLY_LEFT_BRACE), curlyLeftBraceState);
        stateMap.put(new Pair<>(writeState, INLINE_COMMENT), inlineCommentState);
        stateMap.put(new Pair<>(writeState, MULTILINE_COMMENT), multiLineCommentState);
        stateMap.put(new Pair<>(writeState, SEMICOLON), semicolonState);
        stateMap.put(new Pair<>(writeState, CURLY_RIGHT_BRACE), curlyRightBraceState);

        stateMap.put(new Pair<>(writeState, ROUND_LEFT_BRACE), roundLeftBraceState);
        stateMap.put(new Pair<>(writeState, ROUND_RIGHT_BRACE), writeState);
        stateMap.put(new Pair<>(writeState, COMMA), commaState);

        // {
        stateMap.put(new Pair<>(curlyLeftBraceState, null), writeState);
        stateMap.put(new Pair<>(curlyLeftBraceState, INLINE_COMMENT), inlineCommentState);
        stateMap.put(new Pair<>(curlyLeftBraceState, MULTILINE_COMMENT), multiLineCommentState);
        stateMap.put(new Pair<>(curlyLeftBraceState, CURLY_LEFT_BRACE), curlyLeftBraceState);
        stateMap.put(new Pair<>(curlyLeftBraceState, CURLY_RIGHT_BRACE), curlyRightBraceState);

        // ;
        stateMap.put(new Pair<>(semicolonState, null), writeState);
        stateMap.put(new Pair<>(semicolonState, INLINE_COMMENT), inlineCommentState);
        stateMap.put(new Pair<>(semicolonState, MULTILINE_COMMENT), multiLineCommentState);
        stateMap.put(new Pair<>(semicolonState, CURLY_RIGHT_BRACE), curlyRightBraceState);

        // }
        stateMap.put(new Pair<>(curlyRightBraceState, null), writeState);
        stateMap.put(new Pair<>(curlyRightBraceState, INLINE_COMMENT), inlineCommentState);
        stateMap.put(new Pair<>(curlyRightBraceState, MULTILINE_COMMENT), multiLineCommentState);
        stateMap.put(new Pair<>(curlyRightBraceState, CURLY_RIGHT_BRACE), curlyRightBraceState);

        // "//" comment
        stateMap.put(new Pair<>(inlineCommentState, null), writeState);
        stateMap.put(new Pair<>(inlineCommentState, CURLY_LEFT_BRACE), curlyLeftBraceState);
        stateMap.put(new Pair<>(inlineCommentState, CURLY_RIGHT_BRACE), curlyRightBraceState);

        // "/**/" comment
        stateMap.put(new Pair<>(multiLineCommentState, null), writeState);

        // (
        stateMap.put(new Pair<>(roundLeftBraceState, null), writeState);

        // ,
        stateMap.put(new Pair<>(commaState, null), writeState);
    }

    /**
     * @return start state of automata
     */
    @Override
    public FormatterState getStartState() {
        return startState;
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
