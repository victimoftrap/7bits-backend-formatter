package it.sevenbits.formatter.formatter;

import it.sevenbits.formatter.formatter.implementations.StateMachineFormatter;
import it.sevenbits.formatter.lexer.factory.implementations.LexerFactory;
import it.sevenbits.formatter.readers.ReaderException;
import it.sevenbits.formatter.readers.implementations.StringReader;
import it.sevenbits.formatter.writers.implementations.StringWriter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StateMachineFormatterTest {
    @Test
    public void stringLiteralTest() throws FormatterException, ReaderException {
        IFormatter formatter = new StateMachineFormatter(new LexerFactory());
        StringWriter writer = new StringWriter();
        String str1Before = "func{x;\"literal\";}";
        String str1After = "func {\n    x;\n    \"literal\";\n}";
        formatter.format(new StringReader(str1Before), writer);

        assertEquals(str1After, writer.convertToString());
    }

    @Test
    public void inlineCommentTest() throws FormatterException, ReaderException {
        IFormatter formatter = new StateMachineFormatter(new LexerFactory());
        StringWriter writer = new StringWriter();
        String str1Before = "func{x;\"literal\"; // inline \n }";
        String str1After = "func {\n    x;\n    \"literal\"; // inline \n}";
        formatter.format(new StringReader(str1Before), writer);

        assertEquals(str1After, writer.convertToString());
    }

    @Test
    public void inlineCommentBeforeTest() throws FormatterException, ReaderException {
        IFormatter formatter = new StateMachineFormatter(new LexerFactory());
        StringWriter writer = new StringWriter();
        String str1Before = "   // pre \n  func{x;\"literal\";  }";
        String str1After = "// pre \nfunc {\n    x;\n    \"literal\";\n}";
        formatter.format(new StringReader(str1Before), writer);

        assertEquals(str1After, writer.convertToString());
    }

    @Test
    public void inlineCommentAfterTest() throws FormatterException, ReaderException {
        IFormatter formatter = new StateMachineFormatter(new LexerFactory());
        StringWriter writer = new StringWriter();
        String str1Before = "func{x;\"literal\";  }    // post  ";
        String str1After = "func {\n    x;\n    \"literal\";\n} // post  ";
        formatter.format(new StringReader(str1Before), writer);

        assertEquals(str1After, writer.convertToString());
    }

    @Test
    public void multilineCommentTest() throws FormatterException, ReaderException {
        IFormatter formatter = new StateMachineFormatter(new LexerFactory());
        StringWriter writer = new StringWriter();
        String str1Before = "func{x;   /* just\njust a comment*/  \"literal\";}";
        String str1After = "func {\n    x;\n    /* just\n    just a comment*/\n    \"literal\";\n}";
        formatter.format(new StringReader(str1Before), writer);

        assertEquals(str1After, writer.convertToString());
    }
}
