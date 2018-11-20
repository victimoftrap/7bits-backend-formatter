package it.sevenbits.formatter.lexer.implementations;

import it.sevenbits.formatter.lexer.token.IToken;
import it.sevenbits.formatter.lexer.token.implementations.Token;
import it.sevenbits.formatter.readers.implementations.StringReader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class LexerTest {
    @Test
    public void test1() {
        Lexer lex = new Lexer(new StringReader(" func   {  x  } "));
        IToken token0 = new Token("ID OR KEYWORD", "func");
        IToken token1 = new Token("CURLY LEFT BRACKET", "{");
        IToken token2 = new Token("ID OR KEYWORD", "x");
        IToken token3 = new Token("CURLY RIGHT BRACKET", "}");
        List<IToken> tokens = new ArrayList<>();
        Collections.addAll(tokens, token0, token1, token2, token3);

        int i = 0;
        while (lex.hasNext()) {
            IToken token = lex.readToken();
            assertEquals(tokens.get(i).getName(), token.getName());
            assertEquals(tokens.get(i).getLexeme(), token.getLexeme());
            i++;
        }
    }

    @Test
    public void test2() {
        Lexer lex = new Lexer(new StringReader("   func   {   x;   }   "));
        IToken token0 = new Token("ID OR KEYWORD", "func");
        IToken token1 = new Token("CURLY LEFT BRACKET", "{");
        IToken token2 = new Token("ID OR KEYWORD", "x");
        IToken token3 = new Token("SEMICOLON", ";");
        IToken token4 = new Token("CURLY RIGHT BRACKET", "}");
        List<IToken> tokens = new ArrayList<>();
        Collections.addAll(tokens, token0, token1, token2, token3, token4);

        int i = 0;
        while (lex.hasNext()) {
            IToken token = lex.readToken();
            assertEquals(tokens.get(i).getName(), token.getName());
            assertEquals(tokens.get(i).getLexeme(), token.getLexeme());
            i++;
        }
    }

    @Test
    public void test3() {
        Lexer lex = new Lexer(new StringReader("func{x}"));
        IToken token0 = new Token("ID OR KEYWORD", "func");
        IToken token1 = new Token("CURLY LEFT BRACKET", "{");
        IToken token2 = new Token("ID OR KEYWORD", "x");
        IToken token3 = new Token("CURLY RIGHT BRACKET", "}");
        List<IToken> tokens = new ArrayList<>();
        Collections.addAll(tokens, token0, token1, token2, token3);

        int i = 0;
        while (lex.hasNext()) {
            IToken token = lex.readToken();
            assertEquals(tokens.get(i).getName(), token.getName());
            assertEquals(tokens.get(i).getLexeme(), token.getLexeme());
            i++;
        }
    }

    @Test
    public void test4() {
        Lexer lex = new Lexer(new StringReader("func{x;y;}"));
        IToken token0 = new Token("ID OR KEYWORD", "func");
        IToken token1 = new Token("CURLY LEFT BRACKET", "{");
        IToken token2 = new Token("ID OR KEYWORD", "x");
        IToken token3 = new Token("SEMICOLON", ";");
        IToken token4 = new Token("ID OR KEYWORD", "y");
        IToken token5 = new Token("SEMICOLON", ";");
        IToken token6 = new Token("CURLY RIGHT BRACKET", "}");
        List<IToken> tokens = new ArrayList<>();
        Collections.addAll(tokens, token0, token1, token2, token3, token4, token5, token6);

        int i = 0;
        while (lex.hasNext()) {
            IToken token = lex.readToken();
            assertEquals(tokens.get(i).getName(), token.getName());
            assertEquals(tokens.get(i).getLexeme(), token.getLexeme());
            i++;
        }
    }

    @Test
    public void test5() {
        Lexer lex = new Lexer(new StringReader("{{{}}}"));
        IToken token0 = new Token("CURLY LEFT BRACKET", "{");
        IToken token1 = new Token("CURLY LEFT BRACKET", "{");
        IToken token2 = new Token("CURLY LEFT BRACKET", "{");
        IToken token3 = new Token("CURLY RIGHT BRACKET", "}");
        IToken token4 = new Token("CURLY RIGHT BRACKET", "}");
        IToken token5 = new Token("CURLY RIGHT BRACKET", "}");
        List<IToken> tokens = new ArrayList<>();
        Collections.addAll(tokens, token0, token1, token2, token3, token4, token5);

        int i = 0;
        while (lex.hasNext()) {
            IToken token = lex.readToken();
            assertEquals(tokens.get(i).getName(), token.getName());
            assertEquals(tokens.get(i).getLexeme(), token.getLexeme());
            i++;
        }
    }

    @Test
    public void test6() {
        Lexer lex = new Lexer(new StringReader("     void    "));
        IToken token0 = new Token("ID OR KEYWORD", "void");
        List<IToken> tokens = new ArrayList<>();
        tokens.add(token0);

        IToken token = lex.readToken();
        assertEquals(tokens.get(0).getName(), token.getName());
        assertEquals(tokens.get(0).getLexeme(), token.getLexeme());
    }
}