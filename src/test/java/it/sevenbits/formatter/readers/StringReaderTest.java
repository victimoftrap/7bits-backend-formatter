package it.sevenbits.formatter.readers;

import it.sevenbits.formatter.readers.implementations.StringReader;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringReaderTest {
    @Test(expected = ReaderException.class)
    public void nullStreamTest() throws ReaderException {
        StringReader reader = new StringReader(null);
    }

    @Test
    public void readCharactersFromSourceTest() throws ReaderException {
        StringReader reader = new StringReader("abcdefg");

        assertEquals('a', reader.read());
        assertTrue(reader.hasNext());

        assertEquals('b', reader.read());
        assertTrue(reader.hasNext());
    }

    @Test(expected = ReaderException.class)
    public void readCharactersFromEmptySourceTest() throws ReaderException {
        StringReader reader = new StringReader("");

        reader.read();
    }
}
