package ro.samlex.reelcash.tests.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.fail;
import ro.samlex.reelcash.io.InputSource;
import ro.samlex.reelcash.io.InvoiceDataFolderSource;

public class InvoiceDataFolderSourceTests {

    private static Path tempDirPath;

    @BeforeClass
    public static void setUpFolderStructure() {
        try {
            tempDirPath = Files.createTempDirectory("invoices");
        } catch (IOException ex) {
            Assert.fail("Failed to set up temp directory: " + ex.getMessage());
        }
    }

    @AfterClass
    public static void tearDownFolderStructure() {
        try {
            Files.walkFileTree(tempDirPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }

            });
        } catch (IOException ex) {
            Assert.fail("Failed to clean up temp directory: " + ex.getMessage());
        }
    }

    @After
    public void cleanUpFiles() {
        try {
            Files.walkFileTree(tempDirPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    return FileVisitResult.TERMINATE;
                }

            });
        } catch (IOException ex) {
            Assert.fail("Failed to clean up temp directory: " + ex.getMessage());
        }

    }

    @Test
    public void givenInvoiceFolderIterator_onEmptyFolder_hasNextReturnsFalse() {
        InvoiceDataFolderSource sut = new InvoiceDataFolderSource(tempDirPath);

        Assert.assertFalse(sut.iterator().hasNext());
    }

    @Test
    public void givenInvoiceFolderIterator_onFolderWithOneJsonFile_hasNextReturnsTrue() throws IOException {
        Files.createTempFile(tempDirPath, "invoice", ".json");

        InvoiceDataFolderSource sut = new InvoiceDataFolderSource(tempDirPath);

        Assert.assertTrue(sut.iterator().hasNext());
    }

    @Test
    public void givenInvoiceFolderIterator_onFolderWithOneJsonFile_nextReturnsFileWithExpectedContent() throws IOException {
        Path p = Files.createTempFile(tempDirPath, "invoice", ".json");
        try (OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(p))) {
            writer.append("text");
            writer.flush();
        }
        InvoiceDataFolderSource sut = new InvoiceDataFolderSource(tempDirPath);

        String text;
        try (BufferedReader reader = new BufferedReader(sut.iterator().next().newReader())) {
            text = reader.readLine();
        }

        Assert.assertEquals("text", text);
    }

    @Test
    public void givenInvoiceFolderIterator_onFolderWithMoreJsonFiles_nextWillIterateFiles() throws IOException {
        Path p1 = Files.createTempFile(tempDirPath, "invoice1", ".json");
        Path p2 = Files.createTempFile(tempDirPath, "invoice2", ".json");
        int counter = 0;
        String[] expected = {"text 1", "text 2 expected"};
        for (Path p : Arrays.asList(p1, p2)) {
            try (OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(p))) {
                writer.append(expected[counter++]);
                writer.flush();
            }
        }
        InvoiceDataFolderSource sut = new InvoiceDataFolderSource(tempDirPath);

        List<String> text = new ArrayList<>();
        for (InputSource input : sut) {
            try (BufferedReader reader = new BufferedReader(input.newReader())) {
                text.add(reader.readLine());
            }
        }

        Assert.assertArrayEquals(expected, text.toArray(new String[0]));
    }

    @Test
    public void givenInvoiceFolderIterator_onFolderWithMixedFiles_nextWillOnlyIterateJsonFiles() throws IOException {
        Path p1 = Files.createTempFile(tempDirPath, "invoice", ".json");
        Path p2 = Files.createTempFile(tempDirPath, "invoice", ".abc");
        int counter = 0;
        for (Path p : Arrays.asList(p1, p2)) {
            try (OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(p))) {
                writer.append("text " + (++counter));
                writer.flush();
            }
        }
        InvoiceDataFolderSource sut = new InvoiceDataFolderSource(tempDirPath);

        List<String> text = new ArrayList<>();
        for (InputSource input : sut) {
            try (BufferedReader reader = new BufferedReader(input.newReader())) {
                text.add(reader.readLine());
            }
        }

        String[] actual = new String[1];
        String[] expected = {"text 1"};
        Assert.assertArrayEquals(expected, text.toArray(actual));
    }

    @Test
    public void givenInvoiceFolderIterator_onFolderWhichHasDissappeared_anEmptySequenceWillBeIterated() {
        InvoiceDataFolderSource sut = null;
        try {
            Path dirPath = Files.createTempDirectory(tempDirPath, "test21");
            Path filePath = Files.createTempFile(dirPath, "invoice", ".json");
            sut = new InvoiceDataFolderSource(dirPath);
            Files.delete(filePath);
            Files.delete(dirPath);
        } catch (IOException e) {
            fail("Unable to create test setup: " + e.getMessage());
        }

        Assert.assertFalse(sut.iterator().hasNext());
    }
}
