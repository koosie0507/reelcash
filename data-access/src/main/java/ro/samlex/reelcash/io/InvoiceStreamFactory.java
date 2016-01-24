package ro.samlex.reelcash.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import ro.samlex.reelcash.SysUtils;

public class InvoiceStreamFactory extends StreamFactory {
    private static final String DIR_PATH;
    
    static {
        DIR_PATH = SysUtils.createPath(
                SysUtils.getDbFolderPath(),
                "invoices");
        SysUtils.mkdirs(DIR_PATH);
    }
    
    private final String filePath;
    public InvoiceStreamFactory(String invoiceUid) {
        filePath = SysUtils.createPath(DIR_PATH, invoiceUid + ".json");
    }
    
    @Override
    public OutputStream createOutputStream() throws IOException {
        return new FileOutputStream(filePath);
    }

    @Override
    public InputStream createInputStream() throws IOException {
        return new FileInputStream(filePath);
    }
    
}
