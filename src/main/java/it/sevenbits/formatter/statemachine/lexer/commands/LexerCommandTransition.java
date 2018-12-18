package it.sevenbits.formatter.statemachine.lexer.commands;

import it.sevenbits.formatter.statemachine.State;
import it.sevenbits.formatter.statemachine.lexer.TokenBuilderContext;

/**
 * Class for running lexer command transitions
 */
public class LexerCommandTransition {
    private LexerCommandMap commandMap;

    private LexerCommandTransition() {
    }

    /**
     * Create lexer command transitions
     *
     * @param commandMap map with commands
     */
    public LexerCommandTransition(final LexerCommandMap commandMap) {
        this.commandMap = commandMap;
    }

    /**
     * Get command for lexer state machine transition
     *
     * @param currentState current state of automata
     * @param context      with current situation in lexer
     * @return command to execute
     */
    public ILexerCommand nextCommand(final State currentState, final TokenBuilderContext context) {
        return commandMap.getCommand(currentState, context);
    }
}
