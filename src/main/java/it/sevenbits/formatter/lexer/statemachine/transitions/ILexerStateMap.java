package it.sevenbits.formatter.lexer.statemachine.transitions;

import it.sevenbits.formatter.lexer.statemachine.LexerState;

/**
 * Interface for lexer states map of automata
 */
public interface ILexerStateMap {
    /**
     * @return start state of automata
     */
    LexerState getStartState();

    /**
     * Get next state of automata by current state and current char
     *
     * @param state       current state
     * @param currentChar type of current token
     * @return next state of automata
     */
    LexerState getNextState(LexerState state, char currentChar);
}
