package it.sevenbits.formatter.lexer.implementations;

import it.sevenbits.formatter.lexer.ILexer;
import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.lexer.token.IToken;
import it.sevenbits.formatter.lexer.token.implementations.Token;
import it.sevenbits.formatter.exceptions.LexerException;
import it.sevenbits.formatter.exceptions.RWStreamException;

import java.util.Map;
import java.util.HashMap;

/**
 * Class of lexer analyzer
 * */
public class Lexer implements ILexer {
    private final char CURLY_LEFT_BRACKET = '{';
    private final char CURLY_RIGHT_BRACKET = '}';
    private final char ROUND_LEFT_BRACKET = '(';
    private final char ROUND_RIGHT_BRACKET = ')';
    private final char SQUARE_LEFT_BRACKET = '[';
    private final char SQUARE_RIGHT_BRACKET = ']';
    private final char SEMICOLON = ';';
    private final char SPACE = ' ';
    private final char CARRIAGE_RETURN = '\n';
    private final char QUOTATION_MARK = '\"';
    private final char APOSTROPHE = '\'';

    private IReader reader;
    private Map<Character, IToken> separators;
    private char currentChar;
    private char previousChar;

    private IToken nextToken;
    private boolean glueSituation;
    private boolean lastChild;

    /**
     * Forbidden empty constructor
     * */
    private Lexer(){
    }

    /**
     * Creating lexer
     * @param reader some IReader realisation
     * */
    public Lexer(final IReader reader) {
        this.reader = reader;
        separators = new HashMap<>();
        separators.put(CURLY_LEFT_BRACKET, new Token("CURLY LEFT BRACKET", "{"));
        separators.put(CURLY_RIGHT_BRACKET, new Token("CURLY RIGHT BRACKET", "}"));
        separators.put(ROUND_LEFT_BRACKET, new Token("ROUND LEFT BRACKET", "("));
        separators.put(ROUND_RIGHT_BRACKET, new Token("ROUND RIGHT BRACKET", ")"));
        separators.put(SEMICOLON, new Token("SEMICOLON", ";"));
    }

    /**
     * Creates new token if previously reading char wasn't a lexeme or returns created previously token
     * @param accumulator with some string
     * @return token
     * */
    private IToken createToken(StringBuilder accumulator) {
        IToken token = separators.get(previousChar);
        if (token != null) {
            return token;
        } else {
            return new Token("ID OR KEYWORD", accumulator.toString());
        }
    }

    /**
     * Creates new token or return previously created token
     * @param lexeme string created from reader
     * @param c this lexeme or last char from lexeme
     * @return token
     * */
    private IToken createTokenBy(final String lexeme, final char c) {
        IToken token = separators.get(c);
        if (token != null) {
            return token;
        } else {
            // где-то тут проверка на то, что это ключевое слово
            return new Token("ID OR KEYWORD", lexeme);
        }
    }

    /**
     * Reads token from source
     * @return next IToken
     * @throws LexerException if some trouble happen
     * */
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

                if (currentChar == SPACE || currentChar == CARRIAGE_RETURN) {
                    while (hasNext() && (currentChar == SPACE || currentChar == CARRIAGE_RETURN)) {
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
        } catch (RWStreamException e) {
            throw new LexerException("Some trouble with reader content", e);
        }
        return null;
    }

    /**
     * Check if lexer can create a new token
     * @return true if lexer
     * */
    @Override
    public boolean hasNext() {
        return reader.hasNext() || lastChild ;
    }
}
