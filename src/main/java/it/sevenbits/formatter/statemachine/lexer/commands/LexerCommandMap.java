package it.sevenbits.formatter.lexer.lexer.commands;

import it.sevenbits.formatter.statemachine.Pair;
import it.sevenbits.formatter.statemachine.State;
import it.sevenbits.formatter.lexer.lexer.TokenBuilderContext;
import it.sevenbits.formatter.lexer.lexer.commands.implementations.IgnoreCommand;
import it.sevenbits.formatter.lexer.lexer.commands.implementations.AddToBufferCommand;
import it.sevenbits.formatter.lexer.lexer.commands.implementations.ReleaseTokenCommand;
import it.sevenbits.formatter.lexer.lexer.commands.implementations.RecognizeTypeCommand;
import it.sevenbits.formatter.lexer.lexer.commands.implementations.SaveAnonymousCharCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that holds map of commands in lexer state machine
 */
public class LexerCommandMap {
    private Map<Pair<State, Character>, ILexerCommand> commandMap;

    private LexerCommandMap() {
    }

    /**
     * Create lexer command map
     *
     * @param context with details about characters
     */
    public LexerCommandMap(final TokenBuilderContext context) {
        this.commandMap = new HashMap<>();
        State releaseState = new State("RELEASE");
        State defaultState = new State("WAIT BEFORE");
        State waitAfterState = new State("WAIT AFTER");
        State curlyLeftBraceState = new State("CURLY_LEFT_BRACE");
        State curlyRightBraceState = new State("CURLY_RIGHT_BRACE");
        State semicolonState = new State("SEMICOLON");
        State stringLiteralState = new State("STRING");
        State idState = new State("ID");

        State slashState = new State("SLASH", "DIVIDE");
        State inlineCommentState = new State("//", "INLINE_COMMENT");
        State multiLineCommentState = new State("ASTERISK", "MULTILINE_COMMENT");
        State lastAsteriskState = new State("LAST_ASTERISK", multiLineCommentState.getType());

        ILexerCommand save = new SaveAnonymousCharCommand(context);
        ILexerCommand ignore = new IgnoreCommand(context);
        ILexerCommand add = new AddToBufferCommand(context);
        ILexerCommand release = new ReleaseTokenCommand(context);
        ILexerCommand releaseSave = new ReleaseTokenCommand(context, save);
        ILexerCommand recognize = new RecognizeTypeCommand(context);
        ILexerCommand recognizeSave = new RecognizeTypeCommand(context, save);
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

        //
        commandMap.put(new Pair<>(defaultState, '/'), add);

        commandMap.put(new Pair<>(slashState, ' '), recognize);
        commandMap.put(new Pair<>(slashState, '\n'), recognize);
        commandMap.put(new Pair<>(slashState, '/'), recognizeAdd);
        commandMap.put(new Pair<>(slashState, '*'), recognizeAdd);
        commandMap.put(new Pair<>(slashState, null), recognizeSave);

        commandMap.put(new Pair<>(inlineCommentState, null), add);
        commandMap.put(new Pair<>(inlineCommentState, '\n'), ignore);

        commandMap.put(new Pair<>(multiLineCommentState, null), add);
        commandMap.put(new Pair<>(multiLineCommentState, '*'), add);

        commandMap.put(new Pair<>(lastAsteriskState, '/'), add);
        commandMap.put(new Pair<>(lastAsteriskState, null), add);
    }

    /**
     * @param currentState of automata
     * @param context      with current char
     * @return lexer command for this situation
     */
    public ILexerCommand getCommand(final State currentState, final TokenBuilderContext context) {
        ILexerCommand command = commandMap.get(new Pair<>(currentState, context.getCurrentChar()));
        if (command == null) {
            return commandMap.get(new Pair<>(currentState, null));
        }
        return command;
    }
}
