package it.sevenbits.formatter.lexer.implementations;

import it.sevenbits.formatter.readers.ReaderException;
import it.sevenbits.formatter.lexer.ILexer;
import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.lexer.token.IToken;
import it.sevenbits.formatter.lexer.token.implementations.Token;
import it.sevenbits.formatter.lexer.LexerException;

import java.util.Map;
import java.util.HashMap;

/**
 * Class of lexical analyzer
 */
public class Lexer implements ILexer {
    private final char CHAR_CURLY_LEFT_BRACE = '{';
    private final char CHAR_CURLY_RIGHT_BRACE = '}';
    private final char CHAR_ROUND_LEFT_BRACE = '(';
    private final char CHAR_ROUND_RIGHT_BRACE = ')';
    private final char CHAR_SEMICOLON = ';';
    private final char CHAR_SPACE = ' ';
    private final char CHAR_CARRIAGE_RETURN = '\n';
    private final char CHAR_QUOTATION_MARK = '\"';

    private final String ID = "ID";
    private final String CURLY_LEFT_BRACE = "CURLY_LEFT_BRACE";
    private final String CURLY_RIGHT_BRACE = "CURLY_RIGHT_BRACE";
    private final String ROUND_LEFT_BRACE = "ROUND_LEFT_BRACE";
    private final String ROUND_RIGHT_BRACE = "ROUND_RIGHT_BRACE";
    private final String SEMICOLON = "SEMICOLON";
    private final String STRING_LITERAL = "STRING_LITERAL";

    private IReader reader;
    private Map<Character, IToken> separators;
    private char currentChar;
    private char previousChar;

    private IToken nextToken;
    private boolean glueSituation;
    private boolean lastChild;

    /**
     * Forbidden empty constructor
     */
    private Lexer() {
    }

    /**
     * Creating lexer
     *
     * @param reader some IReader realisation
     */
    public Lexer(final IReader reader) {
        this.reader = reader;
        separators = new HashMap<>();
        separators.put(CHAR_CURLY_LEFT_BRACE, new Token(CURLY_LEFT_BRACE, "{"));
        separators.put(CHAR_CURLY_RIGHT_BRACE, new Token(CURLY_RIGHT_BRACE, "}"));
        separators.put(CHAR_ROUND_LEFT_BRACE, new Token(ROUND_LEFT_BRACE, "("));
        separators.put(CHAR_ROUND_RIGHT_BRACE, new Token(ROUND_RIGHT_BRACE, ")"));
        separators.put(CHAR_SEMICOLON, new Token(SEMICOLON, ";"));
    }

    /**
     * Creates new token if previously reading char wasn't a lexeme or returns created previously token
     *
     * @param accumulator with some string
     * @return token
     */
    private IToken createToken(final StringBuilder accumulator) {
        IToken token = separators.get(previousChar);
        if (token != null) {
            return token;
        } else {
            return new Token(ID, accumulator.toString());
        }
    }

//    /**
//     * Creates new token or return previously created token
//     * @param lexeme string created from reader
//     * @param c this lexeme or last char from lexeme
//     * @return token
//     * */
//    private IToken createTokenBy(final String lexeme, final char c) {
//        IToken token = separators.get(c);
//        if (token != null) {
//            return token;
//        } else {
//            // где-то тут проверка на то, что это ключевое слово
//            return new Token("ID OR KEYWORD", lexeme);
//        }
//    }

    /**
     * Reads token from source
     *
     * @return next IToken
     * @throws LexerException if some trouble happen
     */
    @Override
    public IToken readToken() throws LexerException {
        StringBuilder sb = new StringBuilder();

        try {
            while (hasNext()) {
                if (currentChar != 0) {
                    previousChar = currentChar;
                    sb.append(currentChar);
                }
                if (lastChild) {
                    lastChild = false;
                    return createToken(sb);
                }

                currentChar = reader.read();

                if (currentChar == CHAR_SPACE || currentChar == CHAR_CARRIAGE_RETURN) {
                    while (hasNext() && (currentChar == CHAR_SPACE || currentChar == CHAR_CARRIAGE_RETURN)) {
                        currentChar = reader.read();
                    }
                    if (sb.length() != 0) {
                        nextToken = separators.get(currentChar);
                        if (nextToken != null) {
                            if (!hasNext()) {
                                lastChild = true;
                            }
                            glueSituation = true;
                        } else {
                            glueSituation = false;
                        }
                        return createToken(sb);
                    }
                }

                if (!hasNext()) {
                    lastChild = true;
                }

                if (glueSituation) {
                    glueSituation = false;
                    IToken token = nextToken;
                    nextToken = null;

                    nextToken = separators.get(currentChar);
                    if (nextToken != null) {
                        glueSituation = true;
                    }

                    return token;
                }

                nextToken = separators.get(currentChar);
                if (nextToken != null) {
                    glueSituation = true;
                    if (sb.length() > 0) {
                        return createToken(sb);
                    }
                }
            }
        } catch (ReaderException e) {
            throw new LexerException("Some trouble with reader content", e);
        }
        return null;
    }

    /**
     * Check if lexer can create a new token
     *
     * @return true if lexer can create token
     */
    @Override
    public boolean hasNext() {
        return reader.hasNext() || lastChild;
    }
}
