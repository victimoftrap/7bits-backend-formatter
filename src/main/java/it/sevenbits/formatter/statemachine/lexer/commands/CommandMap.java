package it.sevenbits.formatter.statemachine.lexer.commands;

import it.sevenbits.formatter.statemachine.Pair;
import it.sevenbits.formatter.statemachine.State;
import it.sevenbits.formatter.statemachine.lexer.TokenBuilderContext;
import it.sevenbits.formatter.statemachine.lexer.commands.implementations.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that holds map of commands in state machine
 */
public class CommandMap {
    private Map<Pair<State, Character>, ILexerCommand> commandMap;

    /**
     * Create command map
     *
     * @param context with details about characters
     */
    public CommandMap(final TokenBuilderContext context) {
        this.commandMap = new HashMap<>();
        State defaultState = new State("WAIT BEFORE");
        State waitAfterState = new State("WAIT AFTER");
        State releaseState = new State("RELEASE");
        State curlyLeftBraceState = new State("CURLY_LEFT_BRACE");
        State curlyRightBraceState = new State("CURLY_RIGHT_BRACE");
        State semicolonState = new State("SEMICOLON");
        State stringLiteralState = new State("STRING");
        State idState = new State("ID");

        ILexerCommand ignore = new IgnoreCommand(context);
        ILexerCommand add = new AddToBufferCommand(context);
        ILexerCommand release = new ReleaseTokenCommand(context);
        ILexerCommand releaseSave = new ReleaseTokenCommand(context, new SaveAnonymousCharCommand(context));
        ILexerCommand recognizeAdd = new RecognizeTypeCommand(context, add);

        commandMap.put(new Pair<>(defaultState, ' '), ignore);
        commandMap.put(new Pair<>(defaultState, '\n'), ignore);
        commandMap.put(new Pair<>(defaultState, null), recognizeAdd);

        commandMap.put(new Pair<>(idState, null), add);
        commandMap.put(new Pair<>(idState, ' '), ignore);
        commandMap.put(new Pair<>(idState, '\n'), ignore);
        commandMap.put(new Pair<>(idState, ';'), releaseSave);

        commandMap.put(new Pair<>(semicolonState, ' '), ignore);
        commandMap.put(new Pair<>(semicolonState, '\n'), ignore);
        commandMap.put(new Pair<>(semicolonState, null), releaseSave);

        commandMap.put(new Pair<>(curlyLeftBraceState, ' '), ignore);
        commandMap.put(new Pair<>(curlyLeftBraceState, '\n'), ignore);
        commandMap.put(new Pair<>(curlyLeftBraceState, null), releaseSave);

        commandMap.put(new Pair<>(curlyRightBraceState, ' '), ignore);
        commandMap.put(new Pair<>(curlyRightBraceState, '\n'), ignore);
        commandMap.put(new Pair<>(curlyRightBraceState, null), releaseSave);

        commandMap.put(new Pair<>(waitAfterState, ' '), ignore);
        commandMap.put(new Pair<>(waitAfterState, '\n'), ignore);
        commandMap.put(new Pair<>(waitAfterState, null), releaseSave);

        commandMap.put(new Pair<>(stringLiteralState, null), add);
        commandMap.put(new Pair<>(stringLiteralState, '\"'), add);
    }

    /**
     * @param currentState of automata
     * @param context      with current char
     * @return command for this situation
     */
    public ILexerCommand getCommand(final State currentState, final TokenBuilderContext context) {
        ILexerCommand command = commandMap.get(new Pair<>(currentState, context.getCurrentChar()));
        if (command == null) {
            return commandMap.get(new Pair<>(currentState, null));
        }
        return command;
    }
}
