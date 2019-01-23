package it.sevenbits.formatter.formatter;

import it.sevenbits.formatter.formatter.implementations.CleverFormatter;
import it.sevenbits.formatter.lexer.factory.implementations.LexerFactory;
import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.readers.ReaderException;
import it.sevenbits.formatter.readers.implementations.StringReader;
import it.sevenbits.formatter.writers.IWriter;
import it.sevenbits.formatter.writers.WriterException;
import it.sevenbits.formatter.writers.implementations.StringWriter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

public class CleverFormatterTest {
    private IFormatter formatter;

    @Before
    public void init() {
        formatter = new CleverFormatter(new LexerFactory());
    }

    @Test
    public void testWithRedundantSpaces() throws FormatterException, ReaderException {
        String str1Before = "aaa{       \n     \n     bbb;ccc;\n}  \n   \n";
        String str1After = "aaa {\n    bbb;\n    ccc;\n}";
        IReader reader = new StringReader(str1Before);
        StringWriter writer = new StringWriter();
        formatter.format(reader, writer);
        assertEquals(str1After, writer.convertToString());

        String str2Before = "void   f()  {  \n\n char x='c'  \n\n  ;     \n  auto \ny=x;if(x=='c') \n{  int z=7;  }  y++;\n   \n}\n\nx='a';";
        String str2After = "void f() {\n    char x='c';\n    auto y=x;\n    if(x=='c') {\n        int z=7;\n    }\n    y++;\n}\nx='a';";
        reader = new StringReader(str2Before);
        writer = new StringWriter();
        formatter.format(reader, writer);
        assertEquals(str2After, writer.convertToString());
    }

    @Test
    public void testWithoutSpaces() throws FormatterException, ReaderException {
        String str1Before = "aaa{bb;cc;}dd;";
        String str1After = "aaa {\n    bb;\n    cc;\n}\ndd;";
        IReader reader = new StringReader(str1Before);
        StringWriter writer = new StringWriter();
        formatter.format(reader, writer);
        assertEquals(str1After, writer.convertToString());

        String str2Before = "public static void main(String[] args){int a=0;int b=1;}";
        String str2After = "public static void main(String[] args) {\n    int a=0;\n    int b=1;\n}";
        reader = new StringReader(str2Before);
        writer = new StringWriter();
        formatter.format(reader, writer);
        assertEquals(str2After, writer.convertToString());
    }

    @Test
    public void testNoFormat() throws FormatterException, ReaderException {
        String str = "void g() {\n    int b=100;\n    char c='c';\n}\naaa;";
        IReader reader = new StringReader(str);
        StringWriter writer = new StringWriter();
        formatter.format(reader, writer);
        assertEquals(str, writer.convertToString());
    }

    @Test(expected = FormatterException.class)
    public void exceptionOnWriterTest() throws WriterException, ReaderException, FormatterException {
        IFormatter formatter = new CleverFormatter(new LexerFactory());

        IWriter writer = mock(IWriter.class);
        doThrow(WriterException.class).when(writer).write(anyChar());
        StringReader reader = new StringReader("source code");

        formatter.format(reader, writer);
    }

    @Test(expected = FormatterException.class)
    public void exceptionOnLexerTest() throws ReaderException, FormatterException {
        IFormatter formatter = new CleverFormatter(new LexerFactory());

        IWriter writer = new StringWriter();

        IReader reader = mock(IReader.class);
        when(reader.hasNext()).thenReturn(true);
        doThrow(ReaderException.class).when(reader).read();

        formatter.format(reader, writer);
    }
}
