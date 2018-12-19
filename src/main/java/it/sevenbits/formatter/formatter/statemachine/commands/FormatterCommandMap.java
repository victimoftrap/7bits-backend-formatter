package it.sevenbits.formatter.formatter.statemachine.commands;

import it.sevenbits.formatter.formatter.statemachine.Pair;
import it.sevenbits.formatter.formatter.statemachine.FormatterState;
import it.sevenbits.formatter.formatter.statemachine.TokenContext;
import it.sevenbits.formatter.formatter.statemachine.commands.implementations.AddTokenCommand;
import it.sevenbits.formatter.formatter.statemachine.commands.implementations.AddNewLineCommand;
import it.sevenbits.formatter.formatter.statemachine.commands.implementations.DecreaseIndentCommand;
import it.sevenbits.formatter.formatter.statemachine.commands.implementations.IncreaseIndentCommand;
import it.sevenbits.formatter.formatter.statemachine.commands.implementations.WriteSpaceBeforeCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that holds map of commands in formatter state machine
 */
public class FormatterCommandMap {
    private Map<Pair<FormatterState, String>, IFormatterCommand> commandMap;

    /**
     * Create formatter command map
     *
     * @param context situation in state machine
     */
    public FormatterCommandMap(final TokenContext context) {
        commandMap = new HashMap<>();
        FormatterState startState = new FormatterState("START");
        FormatterState listenState = new FormatterState("LISTEN");
        FormatterState semicolonState = new FormatterState("SEMICOLON");
        FormatterState curlyLeftBraceState = new FormatterState("CURLY_LEFT_BRACE");
        FormatterState curlyRightBraceState = new FormatterState("CURLY_RIGHT_BRACE");
        FormatterState stringLiteralState = new FormatterState("STRING");
        FormatterState inlineCommentState = new FormatterState("INLINE_COMMENT");
        FormatterState multiLineCommentState = new FormatterState("MULTILINE_COMMENT");

        IFormatterCommand addToken = new AddTokenCommand(context);
        IFormatterCommand increase = new IncreaseIndentCommand(context);
        IFormatterCommand decrease = new DecreaseIndentCommand(context);
        IFormatterCommand addLine = new AddNewLineCommand(context);
        IFormatterCommand space = new WriteSpaceBeforeCommand(context);

        IFormatterCommand spaceToken = new WriteSpaceBeforeCommand(context, addToken);
        IFormatterCommand spaceTokenIncrease = new WriteSpaceBeforeCommand(context, new AddTokenCommand(context, increase));
        IFormatterCommand newLineToken = new AddNewLineCommand(context, addToken);
        IFormatterCommand decNewLineToken = new DecreaseIndentCommand(context, new AddNewLineCommand(context, addToken));

        commandMap.put(new Pair<>(startState, null), addToken);
        commandMap.put(new Pair<>(startState, inlineCommentState.toString()), addToken);
        commandMap.put(new Pair<>(startState, multiLineCommentState.toString()), addToken);

        commandMap.put(new Pair<>(listenState, null), spaceToken);
        commandMap.put(new Pair<>(listenState, curlyLeftBraceState.toString()), spaceTokenIncrease);
        commandMap.put(new Pair<>(listenState, semicolonState.toString()), addToken);
        commandMap.put(new Pair<>(listenState, inlineCommentState.toString()), spaceToken);
        commandMap.put(new Pair<>(listenState, multiLineCommentState.toString()), spaceToken);
        commandMap.put(new Pair<>(listenState, curlyRightBraceState.toString()), decNewLineToken);

        commandMap.put(new Pair<>(curlyLeftBraceState, null), newLineToken);
        commandMap.put(new Pair<>(curlyLeftBraceState, inlineCommentState.toString()), newLineToken);
        commandMap.put(new Pair<>(curlyLeftBraceState, multiLineCommentState.toString()), newLineToken);

        commandMap.put(new Pair<>(semicolonState, null), newLineToken);
        commandMap.put(new Pair<>(semicolonState, curlyRightBraceState.toString()), decNewLineToken);
        commandMap.put(new Pair<>(semicolonState, inlineCommentState.toString()), spaceToken);
        commandMap.put(new Pair<>(semicolonState, multiLineCommentState.toString()), newLineToken);

        commandMap.put(new Pair<>(curlyRightBraceState, null), newLineToken);
        commandMap.put(new Pair<>(curlyRightBraceState, inlineCommentState.toString()), spaceToken);
        commandMap.put(new Pair<>(curlyRightBraceState, multiLineCommentState.toString()), newLineToken);

        commandMap.put(new Pair<>(inlineCommentState, null), newLineToken);
        commandMap.put(new Pair<>(inlineCommentState, curlyLeftBraceState.toString()), newLineToken);
        commandMap.put(new Pair<>(inlineCommentState, curlyRightBraceState.toString()), decNewLineToken);

        commandMap.put(new Pair<>(multiLineCommentState, null), newLineToken);
    }

    /**
     * Get next command
     *
     * @param currentState current state of automata
     * @param tokenType    type of current token
     * @return next command to execute
     */
    public IFormatterCommand nextCommand(final FormatterState currentState, final String tokenType) {
        IFormatterCommand command = commandMap.get(new Pair<>(currentState, tokenType));
        if (command == null) {
            return commandMap.get(new Pair<>(currentState, null));
        }
        return command;
    }
}
