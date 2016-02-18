package ro.samlex.reelcash.io;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileInputSource implements InputSource {

    private final Path file;

    public FileInputSource(Path file) {
        if (file == null || Files.isDirectory(file)) {
            throw new IllegalArgumentException();
        }
        this.file = file;
    }

    @Override
    public Reader newReader() throws IOException {
        if (!Files.exists(file)) {
            throw new IllegalStateException();
        }
        return new InputStreamReader(Files.newInputStream(file));
    }

}
