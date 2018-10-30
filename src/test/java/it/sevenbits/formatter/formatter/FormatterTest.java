package it.sevenbits.formatter.formatter;

import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.readers.implementations.StringReader;
import it.sevenbits.formatter.writers.IWriter;
import it.sevenbits.formatter.writers.implementations.StringWriter;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class FormatterTest {
    private Formatter formatter;

    @Before
    public void init() {
        formatter = new Formatter();
    }

    @Test
    public void testWithRedundantSpaces() throws IOException {
        String str1Before = "aaa{       \n     \n     bbb;\" I am  string   with    spaces     \";ccc;\n}  \n   \n";
        String str1After = "aaa {\n    bbb;\n    \" I am  string   with    spaces     \";\n    ccc;\n}";
        IReader reader = new StringReader(str1Before);
        IWriter writer = new StringWriter();
        formatter.format(reader, writer);
        assertEquals(writer.toString(), str1After);

        String str2Before = "aaa   {   x;     y;  aaa{  z;  }  y;   }x;";
        String str2After = "aaa {\n    x;\n    y;\n    aaa {\n        z;\n    }\n    y;\n}\nx;";
        reader = new StringReader(str2Before);
        writer = new StringWriter();
        formatter.format(reader, writer);
        assertEquals(writer.toString(), str2After);
    }

    @Test
    public void testWithoutSpaces() throws IOException {
        String str1Before = "aaa{bb;cc;}dd;";
        String str1After = "aaa {\n    bb;\n    cc;\n}\ndd;";
        IReader reader = new StringReader(str1Before);
        IWriter writer = new StringWriter();
        formatter.format(reader, writer);
        assertEquals(writer.toString(), str1After);
    }

    @Test
    public void testNoFormat() throws IOException {
        String str = "aaa {\n    bb;\n    cc;\n}\ndd;";
        IReader reader = new StringReader(str);
        IWriter writer = new StringWriter();
        formatter.format(reader, writer);
        assertEquals(writer.toString(), str);
    }
}