package it.sevenbits.formatter.lexer.lexer;

import it.sevenbits.formatter.statemachine.State;

/**
 * Interface for lexer states map of automata
 */
public interface ILexerStateMap {
    /**
     * @return start state of automata
     */
    State getStartState();

    /**
     * Get next state of automata by current state and current char
     *
     * @param state       current state
     * @param currentChar type of current token
     * @return next state of automata
     */
    State getNextState(State state, char currentChar);
}
