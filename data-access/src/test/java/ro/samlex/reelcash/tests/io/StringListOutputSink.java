package ro.samlex.reelcash.tests.io;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import ro.samlex.reelcash.io.OutputSink;

public final class StringListOutputSink implements OutputSink {

    private final List<String> writtenValues;

    public StringListOutputSink() {
        this.writtenValues = new ArrayList<>();
    }

    @Override
    public Writer newWriter() throws IOException {
        return new StringListWriter();
    }

    public List<String> getWrittenValues() {
        return this.writtenValues;
    }

    private final class StringListWriter extends Writer {

        @Override
        public void write(char[] chars, int i, int i1) throws IOException {
            writtenValues.add(new String(chars, i, i1));
        }

        @Override
        public void flush() throws IOException {
        }

        @Override
        public void close() throws IOException {
        }
    }

}
