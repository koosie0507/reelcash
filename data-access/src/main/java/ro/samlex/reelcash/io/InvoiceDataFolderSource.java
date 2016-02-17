package ro.samlex.reelcash.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InvoiceDataFolderSource implements Iterable<InputSource> {

    private final File invoiceDirectory;

    public InvoiceDataFolderSource(Path invoiceDirectoryPath) {
        invoiceDirectory = invoiceDirectoryPath.toFile();
    }

    @Override
    public Iterator<InputSource> iterator() {
        final List<InputSource> fileSources = new ArrayList<>();
        for (File f : invoiceDirectory.listFiles(new JsonFilenameFilter())) {
            fileSources.add(new FileInputSource(f));
        }
        return fileSources.iterator();
    }

    private static class FileInputSource implements InputSource {

        private final File file;

        public FileInputSource(File file) {
            this.file = file;
        }

        @Override
        public Reader newReader() throws IOException {
            return new InputStreamReader(new FileInputStream(this.file));
        }
    }

    private static class JsonFilenameFilter implements FilenameFilter {

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".json");
        }
    }
}
