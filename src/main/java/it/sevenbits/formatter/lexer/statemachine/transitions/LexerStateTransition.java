package it.sevenbits.formatter.lexer.statemachine.transitions;

import it.sevenbits.formatter.lexer.statemachine.LexerState;

/**
 * Class for running lexer state transitions
 */
public class LexerStateTransition {
    private ILexerStateMap lexerStateMap;

    private LexerStateTransition() {
    }

    /**
     * Create lexer state machine transitions
     *
     * @param lexerStateMap some lexer state map
     */
    public LexerStateTransition(final ILexerStateMap lexerStateMap) {
        this.lexerStateMap = lexerStateMap;
    }

    /**
     * @return start state of automata
     */
    public LexerState getStartState() {
        return lexerStateMap.getStartState();
    }

    /**
     * @param state       current state
     * @param currentChar currently reading char
     * @return next state in automata
     */
    public LexerState getNextState(final LexerState state, final char currentChar) {
        return lexerStateMap.getNextState(state, currentChar);
    }
}
