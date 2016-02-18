package ro.samlex.reelcash.tests.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import ro.samlex.reelcash.SysUtils;
import ro.samlex.reelcash.io.FileInputSource;

public class FileInputSourceTests {

    private Path tempFile;

    @Before
    public void createTempFile() {
        try {
            tempFile = Files.createTempFile("pre", ".json");
        } catch (IOException e) {
            fail("Create temp file failed: " + e.getMessage());
        }
    }

    @After
    public void deleteTempFile() {
        if (Files.exists(tempFile)) {
            try {
                Files.delete(tempFile);
            } catch (IOException ex) {
                fail("Delete temp file failed: " + ex.getMessage());
            }
        }
    }

    @Test(expected = IllegalStateException.class)
    public void givenFileInputSource_whenFileIsDeletedAfterSourceIsInitialized_throwsIllegalStateException() throws IOException {
        FileInputSource sut = new FileInputSource(
                FileSystems.getDefault().getPath("path-does-not-exist"));
        sut.newReader();
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenNewFileInputSource_whenPathIsNull_throwsIllegalArgumentException() {
        new FileInputSource(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenNewFileInputSource_whenPathIsADirectory_throwsIllegalArgumentException() {
        new FileInputSource(FileSystems.getDefault().getPath(SysUtils.getUserHome()));
    }

    @Test
    public void givenNewFileInputSource_happyFlow_returnsInputStreamReader() {
        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            writer.append("test");
        } catch (IOException ex) {
            fail("Write to temp file failed: " + ex.getMessage());
        }

        FileInputSource sut = new FileInputSource(tempFile);

        try (Reader reader = sut.newReader()) {
            assertThat(reader, instanceOf(InputStreamReader.class));
            try (BufferedReader br = new BufferedReader(reader)) {
                assertEquals("test", br.readLine());
            }
        } catch (IOException ex) {
            fail("Could not read by using SUT: " + ex.getMessage());
        }
    }
}
