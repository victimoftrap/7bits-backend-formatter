package it.sevenbits.formatter.lexer;

import it.sevenbits.formatter.exceptions.LexerException;
import it.sevenbits.formatter.exceptions.RWStreamException;
import it.sevenbits.formatter.exceptions.ReaderException;
import it.sevenbits.formatter.lexer.implementations.Lexer;
import it.sevenbits.formatter.lexer.token.implementations.Token;
import it.sevenbits.formatter.readers.implementations.FileReader;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileLexerTest {
    @Test
    public void test() {
        try (FileReader reader = new FileReader("./src/test/java/it/sevenbits/formatter/testdata/source")) {
            Lexer lexer = new Lexer(reader);
            assertEquals(new Token("ID OR KEYWORD", "fun"), lexer.readToken());
            assertEquals(new Token("ID OR KEYWORD", "someFunc"), lexer.readToken());
            assertEquals(new Token("ROUND LEFT BRACKET", "("), lexer.readToken());
            assertEquals(new Token("ID OR KEYWORD", "char"), lexer.readToken());
            assertEquals(new Token("ID OR KEYWORD", "x"), lexer.readToken());
            assertEquals(new Token("ROUND RIGHT BRACKET", ")"), lexer.readToken());
            assertEquals(new Token("CURLY LEFT BRACKET", "{"), lexer.readToken());
            assertEquals(new Token("ID OR KEYWORD", "return"), lexer.readToken());
            assertEquals(new Token("ID OR KEYWORD", "x"), lexer.readToken());
            assertEquals(new Token("SEMICOLON", ";"), lexer.readToken());
            assertEquals(new Token("CURLY RIGHT BRACKET", "}"), lexer.readToken());
        } catch (ReaderException | LexerException e) {
            e.printStackTrace();
        }
    }
}
