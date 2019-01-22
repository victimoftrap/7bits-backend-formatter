package it.sevenbits.formatter.writers;

import it.sevenbits.formatter.writers.implementations.FileWriter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.*;

public class FileWriterTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void correctWriteTest() throws IOException, WriterException {
        File file = folder.newFile();
        assertEquals(0, Files.size(file.toPath()));

        try (FileWriter writer = new FileWriter(file.getPath())) {
            writer.write('w');
            writer.write('h');
            writer.write('a');
            writer.write('m');
            writer.write('!');
        }
        assertEquals(5, Files.size(file.toPath()));
    }
}
