package it.sevenbits.formatter.lexer.implementations;

import it.sevenbits.formatter.exceptions.RWStreamException;
import it.sevenbits.formatter.lexer.ILexer;
import it.sevenbits.formatter.lexer.token.IToken;
import it.sevenbits.formatter.lexer.token.implementations.Token;
import it.sevenbits.formatter.readers.IReader;

import java.util.HashMap;
import java.util.Map;

/**
 * Class of lexer analyzer
 * */
public class Lexer implements ILexer {
    private final char LCBRACE = '{';
    private final char RCBRACE = '}';
    private final char SEMICOLON = ';';
    private final char SPACE = ' ';
    private IReader reader;
    private Map<Character, IToken> separators;
    private char currentChar;
    private boolean glueSituation;

    private boolean lastChild;
    private char previousChar;

    /**
     * Forbidden empty constructor
     * */
    private Lexer(){
    }

    /**
     * Creating lexer
     * @param reader some IReader realisation
     * */
    public Lexer(IReader reader) {
        this.reader = reader;
        separators = new HashMap<>();
        separators.put(LCBRACE, new Token("LEFT BRACE", "{"));
        separators.put(RCBRACE, new Token("RIGHT BRACE", "}"));
        separators.put(SEMICOLON, new Token("SEMICOLON", ";"));
    }

    /**
     * Creates new token if
     * @return token
     * */
    private IToken createToken(StringBuilder accumulator) {
        IToken token = separators.get(previousChar);
        if (token != null) {
            return token;
        } else {
            return new Token("ID_OR_KEYWORD", accumulator.toString());
        }
    }

    /**
     * Reads token from source
     * @return next IToken
     * */
    @Override
    public IToken readToken() {
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
                if (currentChar == SPACE) {
                    while (currentChar == SPACE && reader.hasNext()) {
                        currentChar = reader.read();
                    }
                    if (sb.length() != 0) {
                        if (!hasNext()) {
                            lastChild = true;
                        }
                        return createToken(sb);
                    }
                }
            }

            /*while (hasNext()) {
                if (glueSituation) {
                    glueSituation = false;
                    IToken token = separators.get(remember);
                    remember = 0;
                    return token;
                }

                remember = reader.read();
                if (remember == SPACE) {
                    while (reader.hasNext() && remember == SPACE) {
                        remember = reader.read();
                    }
                    if (sb.length() != 0) {
                        return new Token("ID_OR_KEYWORD", sb.toString().trim());
                    }
                }

                if (remember == LCBRACE || remember == RCBRACE || remember == SEMICOLON) {
                    if (sb.length() != 0) {
                        glueSituation = true;
                        return new Token("ID_OR_KEYWORD", sb.toString().trim());
                    }
                    IToken token = separators.get(remember);
                    remember = 0;
                    return token;
                }
                sb.append(remember);
            }*/
            /*if (remember != 0) {
                return separators.get(remember);
            }*/
        } catch (RWStreamException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Check if lexer can create a new token
     * @return true if lexer
     * */
    @Override
    public boolean hasNext() {
        return reader.hasNext() || lastChild;
    }
}
