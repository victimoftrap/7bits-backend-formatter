package it.sevenbits.formatter.lexer;

import it.sevenbits.formatter.lexer.implementations.StateMachineLexer;
import it.sevenbits.formatter.lexer.token.IToken;
import it.sevenbits.formatter.lexer.token.implementations.Token;
import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.readers.ReaderException;
import it.sevenbits.formatter.readers.implementations.StringReader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class StateMachineLexerTest {
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
        ILexer state = new StateMachineLexer(new StringReader("   func  \n   {  x ;  }  \n "));

        IToken token0 = new Token("ID", "func");
        IToken token1 = new Token("CURLY_LEFT_BRACE", "{");
        IToken token2 = new Token("ID", "x");
        IToken token3 = new Token("SEMICOLON", ";");
        IToken token4 = new Token("CURLY_RIGHT_BRACE", "}");
        List<IToken> tokens = new ArrayList<>();
        Collections.addAll(tokens, token0, token1, token2, token3, token4);

        compareTokens(tokens, state);
    }

    @Test
    public void codeWithoutSpaceInEndTest() throws LexerException, ReaderException {
        ILexer state = new StateMachineLexer(new StringReader(" func  \n {  x  ; }"));
        IToken token0 = new Token("ID", "func");
        IToken token1 = new Token("CURLY_LEFT_BRACE", "{");
        IToken token2 = new Token("ID", "x");
        IToken token3 = new Token("SEMICOLON", ";");
        IToken token4 = new Token("CURLY_RIGHT_BRACE", "}");
        List<IToken> tokens = new ArrayList<>();
        Collections.addAll(tokens, token0, token1, token2, token3, token4);

        compareTokens(tokens, state);
    }

    @Test
    public void codeWithStringLiteralTest() throws LexerException, ReaderException {
        ILexer state = new StateMachineLexer(new StringReader(" func   {  x;  \" abceda   \";  } "));

        IToken token0 = new Token("ID", "func");
        IToken token1 = new Token("CURLY_LEFT_BRACE", "{");
        IToken token2 = new Token("ID", "x");
        IToken token3 = new Token("SEMICOLON", ";");
        IToken token4 = new Token("STRING", "\" abceda   \"");
        IToken token5 = new Token("SEMICOLON", ";");
        IToken token6 = new Token("CURLY_RIGHT_BRACE", "}");
        List<IToken> tokens = new ArrayList<>();
        Collections.addAll(tokens, token0, token1, token2, token3, token4, token5, token6);

        compareTokens(tokens, state);
    }

    @Test
    public void codeWithInlineCommentTest() throws LexerException, ReaderException {
        ILexer state = new StateMachineLexer(new StringReader(" func   {  x; //sample text  ! \n \" abceda   \";  } "));

        IToken token0 = new Token("ID", "func");
        IToken token1 = new Token("CURLY_LEFT_BRACE", "{");
        IToken token2 = new Token("ID", "x");
        IToken token3 = new Token("SEMICOLON", ";");
        IToken token4 = new Token("INLINE_COMMENT", "//sample text  ! ");
        IToken token5 = new Token("STRING", "\" abceda   \"");
        IToken token6 = new Token("SEMICOLON", ";");
        IToken token7 = new Token("CURLY_RIGHT_BRACE", "}");
        List<IToken> tokens = new ArrayList<>();
        Collections.addAll(tokens, token0, token1, token2, token3, token4, token5, token6, token7);

        compareTokens(tokens, state);
    }

    @Test
    public void codeWithCommentsTest() throws LexerException, ReaderException {
        ILexer state = new StateMachineLexer(
                new StringReader(" func   {  x; //this\n \"abceda\"; /* and  this  */ } "));

        IToken token0 = new Token("ID", "func");
        IToken token1 = new Token("CURLY_LEFT_BRACE", "{");
        IToken token2 = new Token("ID", "x");
        IToken token3 = new Token("SEMICOLON", ";");
        IToken token4 = new Token("INLINE_COMMENT", "//this");
        IToken token5 = new Token("STRING", "\"abceda\"");
        IToken token6 = new Token("SEMICOLON", ";");
        IToken token7 = new Token("MULTILINE_COMMENT", "/* and  this  */");
        IToken token8 = new Token("CURLY_RIGHT_BRACE", "}");
        List<IToken> tokens = new ArrayList<>();
        Collections.addAll(tokens, token0, token1, token2, token3, token4, token5, token6, token7, token8);

        compareTokens(tokens, state);
    }

    @Test
    public void normalCodeTest() throws ReaderException, LexerException {
        ILexer lexer = new StateMachineLexer(new StringReader(
                "public void g(String name){sout(\"Hello, $name!\");}"));

        IToken token00 = new Token("ID", "public");
        IToken token01 = new Token("ID", "void");
        IToken token02 = new Token("ID", "g");
        IToken token03 = new Token("ROUND_LEFT_BRACE", "(");
        IToken token04 = new Token("ID", "String");
        IToken token05 = new Token("ID", "name");
        IToken token06 = new Token("ROUND_RIGHT_BRACE", ")");
        IToken token07 = new Token("CURLY_LEFT_BRACE", "{");
        IToken token08 = new Token("ID", "sout");
        IToken token09 = new Token("ROUND_LEFT_BRACE", "(");
        IToken token10 = new Token("STRING", "\"Hello, $name!\"");
        IToken token11 = new Token("ROUND_RIGHT_BRACE", ")");
        IToken token12 = new Token("SEMICOLON", ";");
        IToken token13 = new Token("CURLY_RIGHT_BRACE", "}");
        List<IToken> tokens = new ArrayList<>();
        Collections.addAll(tokens, token00, token01, token02, token03, token04, token05,
                token06, token07, token08, token09, token10, token11, token12, token13);

        compareTokens(tokens, lexer);
    }

    @Test
    public void codeWithCommasTest() throws ReaderException, LexerException {
        ILexer lexer = new StateMachineLexer(new StringReader(
                "concat(s1, s2){return s1 AND s2;}"));

        IToken token00 = new Token("ID", "concat");
        IToken token01 = new Token("ROUND_LEFT_BRACE", "(");
        IToken token02 = new Token("ID", "s1");
        IToken token03 = new Token("COMMA", ",");
        IToken token04 = new Token("ID", "s2");
        IToken token05 = new Token("ROUND_RIGHT_BRACE", ")");
        IToken token06 = new Token("CURLY_LEFT_BRACE", "{");
        IToken token07 = new Token("ID", "return");
        IToken token08 = new Token("ID", "s1");
        IToken token09 = new Token("ID", "AND");
        IToken token10 = new Token("ID", "s2");
        IToken token11 = new Token("SEMICOLON", ";");
        IToken token12 = new Token("CURLY_RIGHT_BRACE", "}");
        List<IToken> tokens = new ArrayList<>();
        Collections.addAll(tokens, token00, token01, token02, token03, token04, token05,
                token06, token07, token08, token09, token10, token11, token12);

        compareTokens(tokens, lexer);
    }

    @Test(expected = LexerException.class)
    public void exceptionOnReaderTest() throws LexerException, ReaderException {
        IReader reader = mock(IReader.class);
        when(reader.hasNext()).thenReturn(true);
        doThrow(ReaderException.class).when(reader).read();

        ILexer lexer = new StateMachineLexer(reader);
        assertTrue(lexer.hasNext());

        lexer.readToken();
    }

    @Test(expected = LexerException.class)
    public void emptyReaderForLexer() throws ReaderException, LexerException {
        ILexer lexer = new StateMachineLexer(new StringReader(""));

        lexer.readToken();
    }
}
