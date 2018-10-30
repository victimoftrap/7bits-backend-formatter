package it.sevenbits.formatter;

import it.sevenbits.formatter.formatter.Formatter;
import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.readers.implementations.StringReader;
import it.sevenbits.formatter.writers.IWriter;
import it.sevenbits.formatter.writers.implementations.StringWriter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Formatter formatter = new Formatter();
        String str2 = "aaa   {   bb;   cc;   aaa   {   dd;     }   ee;}";
        String str3 = "aaa   {   x;  y;   aaa  {y;  aaa{    }}z;   }x;";
        IReader reader = new StringReader(str2);
        IWriter writer = new StringWriter();
        formatter.format(reader, writer);
        System.out.println(writer.toString());
    }
}
