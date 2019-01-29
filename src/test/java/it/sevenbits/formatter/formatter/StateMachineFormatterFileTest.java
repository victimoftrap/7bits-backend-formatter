package it.sevenbits.formatter.formatter;

import it.sevenbits.formatter.formatter.implementations.StateMachineFormatter;
import it.sevenbits.formatter.lexer.factory.implementations.LexerFactory;
import it.sevenbits.formatter.readers.ReaderException;
import it.sevenbits.formatter.writers.WriterException;
import it.sevenbits.formatter.readers.implementations.FileReader;
import it.sevenbits.formatter.writers.implementations.FileWriter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StateMachineFormatterFileTest {
    @Test
    public void formatterFileInputTest() throws ReaderException, WriterException, FormatterException {
        try (FileReader reader = new FileReader("./src/test/resources/fileInput");
             FileWriter writer = new FileWriter("./src/test/resources/fileOutput")) {
            IFormatter formatter = new StateMachineFormatter(new LexerFactory());
            formatter.format(reader, writer);
        }

        try (FileReader reader1 = new FileReader("./src/test/resources/fileOutput");
             FileReader reader2 = new FileReader("./src/test/resources/fileOutputStandard")) {
            while (reader1.hasNext() && reader2.hasNext()) {
                assertEquals(reader1.read(), reader2.read());
            }
        }
    }
}
