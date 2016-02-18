package ro.samlex.reelcash.tests.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Test;
import ro.samlex.reelcash.io.FileOutputSink;

public class FileOutputSinkTests {

    private static final Path TEST_FILE_PATH = FileSystems.getDefault().getPath("abc.json");

    private static void assertFileContentsEquals(String expected, Path filePath) {
        try (BufferedReader r = Files.newBufferedReader(filePath)) {
            StringBuilder contentBuilder = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                contentBuilder.append(line);
            }
            assertEquals(expected, contentBuilder.toString());
        } catch (IOException ex) {
            fail("Failed to read from test file: " + ex.getMessage());
        }
    }

    @After
    public void deleteTestFile() {
        try {
            if (Files.exists(TEST_FILE_PATH)) {
                Files.delete(TEST_FILE_PATH);
            }
        } catch (IOException e) {
            fail("Unable to clean up test file: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenSink_nullFilePath_throwsIllegalArgumentException() {
        new FileOutputSink(null);
    }

    @Test
    public void givenSink_fileDoesNotExist_writesContentToFile() {
        FileOutputSink sut = new FileOutputSink(TEST_FILE_PATH);

        try (Writer w = sut.newWriter()) {
            w.write("test");
        } catch (IOException ex) {
            fail("Failed to write to test file: " + ex.getMessage());
        }

        assertFileContentsEquals("test", TEST_FILE_PATH);
    }

    @Test
    public void givenSink_fileExists_contentIsOverwritten() {
        try (Writer w = Files.newBufferedWriter(TEST_FILE_PATH)) {
            w.append("abc");
        } catch (IOException ex) {
            fail("Failed to set up test file: " + ex.getMessage());
        }
        FileOutputSink sut = new FileOutputSink(TEST_FILE_PATH);

        try (Writer w = sut.newWriter()) {
            w.write("test");
        } catch (IOException ex) {
            fail("Failed to write to test file: " + ex.getMessage());
        }

        assertFileContentsEquals("test", TEST_FILE_PATH);
    }
}
