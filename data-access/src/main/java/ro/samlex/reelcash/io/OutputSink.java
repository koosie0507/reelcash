package ro.samlex.reelcash.io;

import java.io.IOException;
import java.io.Writer;

public interface OutputSink {

    public Writer newWriter() throws IOException;
}
