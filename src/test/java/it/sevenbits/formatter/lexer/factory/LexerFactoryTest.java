package it.sevenbits.formatter.lexer.factory;

import it.sevenbits.formatter.lexer.ILexer;
import it.sevenbits.formatter.lexer.factory.implementations.LexerFactory;
import it.sevenbits.formatter.lexer.implementations.Lexer;
import it.sevenbits.formatter.lexer.implementations.StateMachineLexer;
import it.sevenbits.formatter.readers.IReader;
import it.sevenbits.formatter.readers.ReaderException;
import it.sevenbits.formatter.readers.implementations.StringReader;
import org.junit.Test;

import static org.junit.Assert.*;

public class LexerFactoryTest {
    @Test
    public void typeOfLexerTest() throws ReaderException {
        ILexerFactory factory = new LexerFactory();
        IReader reader = new StringReader("source");

        ILexer baseLexer = factory.getLexer("BASE", reader);
        assertTrue(baseLexer instanceof Lexer);

        ILexer smLexer = factory.getLexer("STATE", reader);
        assertTrue(smLexer instanceof StateMachineLexer);

        ILexer lexer = factory.getLexer("SOMETHING", reader);
        assertNull(lexer);
    }
}