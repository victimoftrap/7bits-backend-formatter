package it.sevenbits.formatter.statemachine.lexer;

import it.sevenbits.formatter.statemachine.State;

/**
 * Class for running state transitions
 */
public class LexerStateTransition {
    private ILexerStateMap lexerStateMap;

    private LexerStateTransition(){
    }

    /**
     * Create lexer transitions
     *
     * @param lexerStateMap some lexer state map
     */
    public LexerStateTransition(final ILexerStateMap lexerStateMap) {
        this.lexerStateMap = lexerStateMap;
    }

    /**
     * @return start state of automata
     */
    public State getStartState() {
        return lexerStateMap.getStartState();
    }

    /**
     * @param state       current state
     * @param currentChar currently reading char
     * @return next state in automata
     */
    public State getNextState(final State state, final char currentChar) {
        return lexerStateMap.getNextState(state, currentChar);
    }
}
