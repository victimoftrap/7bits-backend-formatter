package it.sevenbits.formatter.lexer;

import it.sevenbits.formatter.lexer.implementations.Lexer;
import it.sevenbits.formatter.lexer.token.IToken;
import it.sevenbits.formatter.lexer.token.implementations.Token;
import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.readers.ReaderException;
import it.sevenbits.formatter.readers.implementations.StringReader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LexerTest {
    public void compareTokens(List<IToken> expected, ILexer actual) throws LexerException {
        int i = 0;
        while (actual.hasNext()) {
            IToken token = actual.readToken();
            assertEquals(expected.get(i).getName(), token.getName());
            assertEquals(expected.get(i).getLexeme(), token.getLexeme());
            i++;
        }
        assertEquals(expected.size(), i);
    }

    @Test
    public void simpleCodeTest() throws LexerException, ReaderException {
        Lexer lex = new Lexer(new StringReader(" func   {  x  } "));

        IToken token0 = new Token("ID OR KEYWORD", "func");
        IToken token1 = new Token("CURLY LEFT BRACKET", "{");
        IToken token2 = new Token("ID OR KEYWORD", "x");
        IToken token3 = new Token("CURLY RIGHT BRACKET", "}");
        List<IToken> tokens = new ArrayList<>();
        Collections.addAll(tokens, token0, token1, token2, token3);

        compareTokens(tokens, lex);
    }

    @Test
    public void codeWithSemicolonTest() throws LexerException, ReaderException {
        Lexer lex = new Lexer(new StringReader("   func   {   x;   }   "));

        IToken token0 = new Token("ID OR KEYWORD", "func");
        IToken token1 = new Token("CURLY LEFT BRACKET", "{");
        IToken token2 = new Token("ID OR KEYWORD", "x");
        IToken token3 = new Token("SEMICOLON", ";");
        IToken token4 = new Token("CURLY RIGHT BRACKET", "}");
        List<IToken> tokens = new ArrayList<>();
        Collections.addAll(tokens, token0, token1, token2, token3, token4);

        compareTokens(tokens, lex);
    }

    @Test
    public void codeWithoutSpacesTest() throws LexerException, ReaderException {
        Lexer lex = new Lexer(new StringReader("func{x}"));

        IToken token0 = new Token("ID OR KEYWORD", "func");
        IToken token1 = new Token("CURLY LEFT BRACKET", "{");
        IToken token2 = new Token("ID OR KEYWORD", "x");
        IToken token3 = new Token("CURLY RIGHT BRACKET", "}");
        List<IToken> tokens = new ArrayList<>();
        Collections.addAll(tokens, token0, token1, token2, token3);

        compareTokens(tokens, lex);
    }

    @Test
    public void codeWithSemicolonsAndWithoutSpacesTest() throws LexerException, ReaderException {
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

        compareTokens(tokens, lex);
    }

    @Test
    public void bracketTest() throws LexerException, ReaderException {
        Lexer lex = new Lexer(new StringReader("{{{}}}"));

        IToken token0 = new Token("CURLY LEFT BRACKET", "{");
        IToken token1 = new Token("CURLY LEFT BRACKET", "{");
        IToken token2 = new Token("CURLY LEFT BRACKET", "{");
        IToken token3 = new Token("CURLY RIGHT BRACKET", "}");
        IToken token4 = new Token("CURLY RIGHT BRACKET", "}");
        IToken token5 = new Token("CURLY RIGHT BRACKET", "}");
        List<IToken> tokens = new ArrayList<>();
        Collections.addAll(tokens, token0, token1, token2, token3, token4, token5);

        compareTokens(tokens, lex);
    }

    @Test
    public void oneKeywordWithRubbishTest() throws LexerException, ReaderException {
        Lexer lex = new Lexer(new StringReader("     void\n\n\n\n"));

        IToken token0 = new Token("ID OR KEYWORD", "void");
        List<IToken> tokens = new ArrayList<>();
        tokens.add(token0);

        IToken token = lex.readToken();
        assertEquals(tokens.get(0).getName(), token.getName());
        assertEquals(tokens.get(0).getLexeme(), token.getLexeme());
    }

    @Test
    public void codeOfFunctionTest() throws LexerException, ReaderException {
        Lexer lex = new Lexer(new StringReader("  int \n  func  ( \n\n  )  {  return  x  ;}"));

        IToken token0 = new Token("ID OR KEYWORD", "int");
        IToken token1 = new Token("ID OR KEYWORD", "func");
        IToken token2 = new Token("ROUND LEFT BRACKET", "(");
        IToken token3 = new Token("ROUND RIGHT BRACKET", ")");
        IToken token4 = new Token("CURLY LEFT BRACKET", "{");
        IToken token5 = new Token("ID OR KEYWORD", "return");
        IToken token6 = new Token("ID OR KEYWORD", "x");
        IToken token7 = new Token("SEMICOLON", ";");
        IToken token8 = new Token("CURLY RIGHT BRACKET", "}");
        List<IToken> tokens = new ArrayList<>();
        Collections.addAll(tokens, token0, token1, token2, token3, token4, token5, token6, token7, token8);

        compareTokens(tokens, lex);
    }

    @Test
    public void codeOfFunctionWithParamsWithoutRubbishTest() throws LexerException, ReaderException {
        Lexer lex = new Lexer(new StringReader("int f(char x){return x;}"));

        IToken token0 = new Token("ID OR KEYWORD", "int");
        IToken token1 = new Token("ID OR KEYWORD", "f");
        IToken token2 = new Token("ROUND LEFT BRACKET", "(");
        IToken token3 = new Token("ID OR KEYWORD", "char");
        IToken token4 = new Token("ID OR KEYWORD", "x");
        IToken token5 = new Token("ROUND RIGHT BRACKET", ")");
        IToken token6 = new Token("CURLY LEFT BRACKET", "{");
        IToken token7 = new Token("ID OR KEYWORD", "return");
        IToken token8 = new Token("ID OR KEYWORD", "x");
        IToken token9 = new Token("SEMICOLON", ";");
        IToken token10 = new Token("CURLY RIGHT BRACKET", "}");
        List<IToken> tokens = new ArrayList<>();
        Collections.addAll(tokens, token0, token1, token2, token3, token4, token5, token6, token7, token8, token9, token10);

        compareTokens(tokens, lex);
    }

    @Test
    public void codeOfFunctionWithParamsWithRubbishTest() throws LexerException, ReaderException {
        Lexer lex = new Lexer(
                new StringReader("\n   int    f  \n\n\n    (   char  \n    x )    {    return  \n   x     ;      }"));

        IToken token0 = new Token("ID OR KEYWORD", "int");
        IToken token1 = new Token("ID OR KEYWORD", "f");
        IToken token2 = new Token("ROUND LEFT BRACKET", "(");
        IToken token3 = new Token("ID OR KEYWORD", "char");
        IToken token4 = new Token("ID OR KEYWORD", "x");
        IToken token5 = new Token("ROUND RIGHT BRACKET", ")");
        IToken token6 = new Token("CURLY LEFT BRACKET", "{");
        IToken token7 = new Token("ID OR KEYWORD", "return");
        IToken token8 = new Token("ID OR KEYWORD", "x");
        IToken token9 = new Token("SEMICOLON", ";");
        IToken token10 = new Token("CURLY RIGHT BRACKET", "}");
        List<IToken> tokens = new ArrayList<>();
        Collections.addAll(tokens, token0, token1, token2, token3, token4, token5, token6, token7, token8, token9, token10);

        compareTokens(tokens, lex);
    }

    @Test(expected = LexerException.class)
    public void exceptionOnReaderTest() throws LexerException, ReaderException {
        IReader reader = mock(IReader.class);
        when(reader.hasNext()).thenReturn(true);
        doThrow(ReaderException.class).when(reader).read();

        Lexer lexer = new Lexer(reader);
        assertTrue(lexer.hasNext());

        lexer.readToken();
    }
}