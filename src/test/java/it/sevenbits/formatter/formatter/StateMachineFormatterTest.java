package it.sevenbits.formatter.formatter;

import it.sevenbits.formatter.formatter.implementations.StateMachineFormatter;
import it.sevenbits.formatter.lexer.factory.implementations.LexerFactory;
import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.readers.ReaderException;
import it.sevenbits.formatter.readers.implementations.StringReader;
import it.sevenbits.formatter.writers.IWriter;
import it.sevenbits.formatter.writers.WriterException;
import it.sevenbits.formatter.writers.implementations.StringWriter;

import org.junit.Test;

import static org.mockito.Mockito.*;
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

    @Test
    public void normalCodeTest() throws ReaderException, FormatterException {
        IFormatter formatter = new StateMachineFormatter(new LexerFactory());
        StringWriter writer = new StringWriter();
        String str1Before =
                "public class Nelly{\n\nprivate" +
                " String word;\n" +
                "public Nelly(String word){word eq word;}\n" +
                "public String say(){return \"Nelly said $word, and it right\"; //and nothing happen\n" +
                "}\n" +
                "public void nothing(){}\n" +
                "}";

        String str1After =
                "public class Nelly {\n" +
                "    private String word;\n" +
                "    public Nelly (String word) {\n" +
                "        word eq word;\n" +
                "    }\n" +
                "    public String say () {\n" +
                "        return \"Nelly said $word, and it right\"; //and nothing happen\n" +
                "    }\n" +
                "    public void nothing () {\n" +
                "    }\n" +
                "}";
        formatter.format(new StringReader(str1Before), writer);

        assertEquals(str1After, writer.convertToString());
    }

    @Test
    public void functionWithTwoParamsTest() throws ReaderException, FormatterException {
        IFormatter formatter = new StateMachineFormatter(new LexerFactory());
        StringWriter writer = new StringWriter();

        String str1Before =
                "public   boolean      compare(String   s1, String s2){return s1\n" +
                "equals s2;\n" +
                "}\n" +
                "\n" +
                "compare(\"Kotlin\",   \"Ceylon\");";
        String str1After =
                "public boolean compare (String s1, String s2) {\n" +
                "    return s1 equals s2;\n" +
                "}\n" +
                "compare (\"Kotlin\", \"Ceylon\");";

        formatter.format(new StringReader(str1Before), writer);

        assertEquals(str1After, writer.convertToString());
    }

    @Test(expected = FormatterException.class)
    public void exceptionOnWriterTest() throws WriterException, ReaderException, FormatterException {
        IFormatter formatter = new StateMachineFormatter(new LexerFactory());

        IWriter writer = mock(IWriter.class);
        doThrow(WriterException.class).when(writer).write(anyChar());
        StringReader reader = new StringReader("source code");

        formatter.format(reader, writer);
    }

    @Test(expected = FormatterException.class)
    public void exceptionOnLexerTest() throws ReaderException, FormatterException {
        IFormatter formatter = new StateMachineFormatter(new LexerFactory());

        IWriter writer = new StringWriter();

        IReader reader = mock(IReader.class);
        when(reader.hasNext()).thenReturn(true);
        doThrow(ReaderException.class).when(reader).read();

        formatter.format(reader, writer);
    }
}
