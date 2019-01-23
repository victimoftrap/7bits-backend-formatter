package it.sevenbits.formatter.writers;

import it.sevenbits.formatter.writers.implementations.StringWriter;
import org.junit.Test;
import static org.junit.Assert.*;

public class StringWriterTest {
    @Test
    public void writeSomeCharTest() {
        StringWriter writer = new StringWriter();
        writer.write('c');
        writer.write('z');
        writer.write('e');
        writer.write('c');
        writer.write('h');
        assertEquals("czech", writer.convertToString());
    }

    @Test
    public void emptyOutputTest() {
        StringWriter writer = new StringWriter();
        assertEquals("", writer.convertToString());
    }
}
