package it.sevenbits.formatter.statemachine.formatter.commands;

import it.sevenbits.formatter.statemachine.State;

/**
 * Class for running formatter command transitions
 */
public class FormatterCommandTransition {
    private FormatterCommandMap commandMap;

    /**
     * Create formatter command transitions
     *
     * @param commandMap map with commands
     */
    public FormatterCommandTransition(final FormatterCommandMap commandMap) {
        this.commandMap = commandMap;
    }

    /**
     * Get next command
     *
     * @param currentState current state of automata
     * @param tokenType    type of current token
     * @return next command to execute
     */
    public IFormatterCommand nextCommand(final State currentState, final String tokenType) {
        return commandMap.nextCommand(currentState, tokenType);
    }
}
