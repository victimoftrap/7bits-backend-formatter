package it.sevenbits.formatter.lexer;

import it.sevenbits.formatter.readers.ReaderException;
import it.sevenbits.formatter.lexer.implementations.Lexer;
import it.sevenbits.formatter.lexer.token.implementations.Token;
import it.sevenbits.formatter.readers.implementations.FileReader;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileLexerTest {
    @Test
    public void test() {
        try (FileReader reader = new FileReader("./src/test/resources/source")) {
            Lexer lexer = new Lexer(reader);
            assertEquals(new Token("ID", "fun"), lexer.readToken());
            assertEquals(new Token("ID", "someFunc"), lexer.readToken());
            assertEquals(new Token("ROUND_LEFT_BRACE", "("), lexer.readToken());
            assertEquals(new Token("ID", "char"), lexer.readToken());
            assertEquals(new Token("ID", "x"), lexer.readToken());
            assertEquals(new Token("ROUND_RIGHT_BRACE", ")"), lexer.readToken());
            assertEquals(new Token("CURLY_LEFT_BRACE", "{"), lexer.readToken());
            assertEquals(new Token("ID", "return"), lexer.readToken());
            assertEquals(new Token("ID", "x"), lexer.readToken());
            assertEquals(new Token("SEMICOLON", ";"), lexer.readToken());
            assertEquals(new Token("CURLY_RIGHT_BRACE", "}"), lexer.readToken());
            assertFalse(lexer.hasNext());
        } catch (ReaderException | LexerException e) {
            e.printStackTrace();
        }
    }
}
