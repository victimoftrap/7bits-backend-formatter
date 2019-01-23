package it.sevenbits.formatter.lexer.statemachine.commands;

import it.sevenbits.formatter.lexer.statemachine.LexerState;
import it.sevenbits.formatter.lexer.statemachine.TokenBuilderContext;

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
    public ILexerCommand nextCommand(final LexerState currentState, final TokenBuilderContext context) {
        return commandMap.getCommand(currentState, context);
    }
}
