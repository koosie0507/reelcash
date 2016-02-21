package ro.samlex.reelcash.tests.io;

import java.io.IOException;
import java.io.Writer;
import ro.samlex.reelcash.io.OutputSink;

public class NullWriterOutputSink implements OutputSink {

    @Override
    public Writer newWriter() throws IOException {
        return null;
    }

}
