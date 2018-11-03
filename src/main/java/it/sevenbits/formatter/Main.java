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
        String str4 = "a   public  \n   void   test(){\n    int x=5;   \n  \nint y=6;\n    if (x==y)              {\n       int z=x+y;       \n      }\n    \"bla\".add(z);\n}           ";
        String str5 = "a void f(String[] args){int a=0;}";
        IReader reader = new StringReader(str4);
        IWriter writer = new StringWriter();
        formatter.format(reader, writer);
        System.out.println(writer.toString());
    }

}
