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
    private final char CURLY_LEFT_BRACKET = '{';
    private final char CURLY_RIGHT_BRACKET = '}';
    private final char ROUND_LEFT_BRACKET = '(';
    private final char ROUND_RIGHT_BRACKET = ')';
    private final char SQUARE_LEFT_BRACKET = '[';
    private final char SQUARE_RIGHT_BRACKET = ']';
    private final char SEMICOLON = ';';
    private final char SPACE = ' ';
    private final char QUOTATION_MARK = '\"';
    private final char APOSTROPHE = '\'';

    private IReader reader;
    private Map<Character, IToken> separators;
    private char currentChar;
    private boolean glueSituation;

    private boolean lastChild;
    private char previousChar;
    private IToken nextToken;

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
                while (currentChar == SPACE && reader.hasNext()) {
                    currentChar = reader.read();
                }

                if (glueSituation) {
                    glueSituation = false;
                    IToken token = nextToken;
                    nextToken = null;
                    return token;
                }

                if (currentChar == SPACE) {
                    while (currentChar == SPACE && reader.hasNext()) {
                        currentChar = reader.read();
                    }
                    if (sb.length() != 0) {
                        return createToken(sb);
                    }
                }

                nextToken = separators.get(currentChar);
                if (nextToken != null) {
                    glueSituation = true;
                    return createToken(sb);
                }

                if (!hasNext() ){ // && currentChar != SPACE) {
                    lastChild = true;
                }
            }
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
