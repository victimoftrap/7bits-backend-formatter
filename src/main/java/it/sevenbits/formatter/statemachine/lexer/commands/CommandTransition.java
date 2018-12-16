package it.sevenbits.formatter.statemachine.lexer.commands;

import it.sevenbits.formatter.statemachine.State;
import it.sevenbits.formatter.statemachine.lexer.TokenBuilderContext;

/**
 * Class for running command transitions
 */
public class CommandTransition {
    private CommandMap commandMap;

    private CommandTransition() {
    }

    /**
     * Create command transitions
     *
     * @param commandMap map with commands
     */
    public CommandTransition(final CommandMap commandMap) {
        this.commandMap = commandMap;
    }

    /**
     * Get command for state machine transition
     *
     * @param currentState current state of automata
     * @param context      with current situation in lexer
     * @return command to execute
     */
    public ILexerCommand nextCommand(final State currentState, final TokenBuilderContext context) {
        return commandMap.getCommand(currentState, context);
    }
}
