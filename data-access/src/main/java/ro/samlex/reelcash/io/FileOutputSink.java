package ro.samlex.reelcash.io;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;

public class FileOutputSink implements OutputSink {

    private final java.nio.file.Path filePath;

    public FileOutputSink(java.nio.file.Path filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException();
        }
        this.filePath = filePath;
    }

    @Override
    public Writer newWriter() throws IOException {
        return Files.newBufferedWriter(filePath);
    }
}
