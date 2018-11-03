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
        assertEquals(str1After, writer.toString());

        String str2Before = "void   f()  {  \n\n char x='c'  \n\n  ;     \n  auto \ny=x;if(x=='c') \n{  int z=7;  }  y++;\n   \n}\n\nx='\0';";
        String str2After = "void f() {\n    char x='c';\n    auto y=x;\n    if(x=='c') {\n        int z=7;\n    }\n    y++;\n}\nx='\0';";
        reader = new StringReader(str2Before);
        writer = new StringWriter();
        formatter.format(reader, writer);
        assertEquals(str2After, writer.toString());
    }

    @Test
    public void testWithoutSpaces() throws IOException {
        String str1Before = "aaa{bb;cc;}dd;";
        String str1After = "aaa {\n    bb;\n    cc;\n}\ndd;";
        IReader reader = new StringReader(str1Before);
        IWriter writer = new StringWriter();
        formatter.format(reader, writer);
        assertEquals(str1After, writer.toString());

        String str2Before = "public static void main(String[] args){int a=0;int b=1;}";
        String str2After = "public static void main(String[] args) {\n    int a=0;\n    int b=1;\n}";
        reader = new StringReader(str2Before);
        writer = new StringWriter();
        formatter.format(reader, writer);
        assertEquals(str2After, writer.toString());
    }

    @Test
    public void testNoFormat() throws IOException {
        String str = "void g() {\n    int b=100;\n    char c='c';\n}\naaa;";
        IReader reader = new StringReader(str);
        IWriter writer = new StringWriter();
        formatter.format(reader, writer);
        assertEquals(str, writer.toString());
    }
}