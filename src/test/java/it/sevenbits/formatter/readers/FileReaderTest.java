package it.sevenbits.formatter.readers;

import it.sevenbits.formatter.readers.implementations.FileReader;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileReaderTest {

    @Test
    public void readCharactersTest() throws ReaderException {
        try (FileReader reader = new FileReader("./src/test/resources/source")) {
            assertEquals('f', reader.read());
            assertEquals('u', reader.read());
            assertEquals('n', reader.read());
            assertTrue(reader.hasNext());
        }
    }

    @Test
    public void emptyFileHasNoNextSymbolTest() throws ReaderException {
        try (FileReader reader = new FileReader("./src/test/resources/emptyfile")) {
            assertFalse(reader.hasNext());
        }
    }

    @Test(expected = ReaderException.class)
    public void readCharactersFromEmptyFileTest() throws ReaderException {
        try (FileReader reader = new FileReader("./src/test/resources/emptyfile")) {
            reader.read();
        }
    }

    @Test(expected = ReaderException.class)
    public void readCharactersNonExistingFileTest() throws ReaderException {
        try (FileReader reader = new FileReader("./src/test/resources/nofile")) {

        }
    }
}
