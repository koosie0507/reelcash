package ro.samlex.reelcash.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InvoiceDataFolderSource implements Iterable<InputSource> {

    private final Path invoiceDirectory;

    public InvoiceDataFolderSource(Path invoiceDirectoryPath) {
        invoiceDirectory = invoiceDirectoryPath;
    }

    @Override
    public Iterator<InputSource> iterator() {
        final List<InputSource> fileSources = new ArrayList<>();
        try {
            for (Path filePath : Files.newDirectoryStream(invoiceDirectory, "*.json")) {
                fileSources.add(new FileInputSource(filePath));
            }
        } catch (IOException e) {
            Logger.getLogger(InvoiceDataFolderSource.class.getName()).log(Level.WARNING, "I/O error on reading folder: {0}", e.getMessage());
        }
        return fileSources.iterator();
    }
}
