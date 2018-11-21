package it.sevenbits.formatter;

import it.sevenbits.formatter.exceptions.FormatterException;
import it.sevenbits.formatter.exceptions.RWStreamException;
import it.sevenbits.formatter.formatter.Formattable;
import it.sevenbits.formatter.lexer.ILexer;
import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.writers.IWriter;
import it.sevenbits.formatter.readers.implementations.FileReader;
import it.sevenbits.formatter.writers.implementations.FileWriter;
import it.sevenbits.formatter.lexer.implementations.Lexer;
import it.sevenbits.formatter.formatter.implementations.CleverFormatter;
import it.sevenbits.formatter.lexer.factory.implementations.LexerFactory;

public class Main {
    public static void main(String[] args) throws RWStreamException, FormatterException {
        IReader reader = new FileReader(args[0]);
        IWriter writer = new FileWriter(args[1]);

        ILexer lex = new Lexer(reader);
        Formattable formatter = new CleverFormatter(new LexerFactory());
        formatter.format(reader, writer);
    }
}
