package ro.samlex.reelcash.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import ro.samlex.reelcash.Reelcash;
import ro.samlex.reelcash.SysUtils;

public class CompanyDataStreamFactory extends StreamFactory {
    private static final String FILE_PATH;
    
    static {
        FILE_PATH = SysUtils.createPath(
                SysUtils.getDbFolderPath(),
                Reelcash.COMPANY_DATA_FILE_NAME);
    }
    
    @Override
    public OutputStream createOutputStream() throws IOException{
        return new FileOutputStream(FILE_PATH);
    }

    @Override
    public InputStream createInputStream() throws IOException {
        return new FileInputStream(FILE_PATH);
    }
}
