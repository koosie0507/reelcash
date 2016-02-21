package ro.samlex.reelcash.tests.io;

import java.io.IOException;
import java.io.Writer;
import ro.samlex.reelcash.io.OutputSink;

public final class IOExceptionOutputSink implements OutputSink {

    private boolean throwOnNew;
    private boolean throwOnWrite;
    private boolean throwOnClose;

    @Override
    public Writer newWriter() throws IOException {
        return new WriterAdapter();
    }

    public IOExceptionOutputSink throwOnNewWriter() {
        throwOnNew = true;
        return this;
    }

    public IOExceptionOutputSink throwOnWrite() {
        throwOnWrite = true;
        return this;
    }

    public IOExceptionOutputSink throwOnCloseWriter() {
        throwOnClose = true;
        return this;
    }

    private final class WriterAdapter extends Writer {

        public WriterAdapter() throws IOException {
            if (throwOnNew) {
                throw new IOException();
            }
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            if (throwOnWrite) {
                throw new IOException();
            }
        }

        @Override
        public void flush() throws IOException {
        }

        @Override
        public void close() throws IOException {
            if (throwOnClose) {
                throw new IOException();
            }
        }
    }
}
