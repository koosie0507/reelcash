package ro.samlex.reelcash.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class StreamFactory {
    public abstract OutputStream createOutputStream() throws IOException;
    public abstract InputStream createInputStream() throws IOException;
}
