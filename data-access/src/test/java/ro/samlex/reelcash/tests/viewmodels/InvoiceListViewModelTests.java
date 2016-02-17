package ro.samlex.reelcash.tests.viewmodels;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import ro.samlex.reelcash.commands.Command;
import ro.samlex.reelcash.data.Invoice;

import ro.samlex.reelcash.viewmodels.InvoiceListViewModel;
import ro.samlex.reelcash.viewmodels.SelectorViewModel;

public class InvoiceListViewModelTests {
    @Test
    public void givenNewInstance_inAnyCase_itIsASelectorViewModel() {
        assertThat(new InvoiceListViewModel(new StringReaderSource()), instanceOf(SelectorViewModel.class));
    }
    
    @Test
    public void givenNewInstance_inAnyState_loadInvoicesCommandIsOfExpectedType() {
        assertThat(new InvoiceListViewModel(new StringReaderSource()).getLoadInvoicesCommand(), instanceOf(Command.class));
    }
    
    @Test
    public void givenNewInstance_inInitialState_loadCommandIsExecutable() {
        assertThat(new InvoiceListViewModel(new StringReaderSource()).getLoadInvoicesCommand().isExecutable(), is(true));
    }
    
    @Test
    public void givenNewInstance_afterExecutingLoadCommand_invoiceListContainsExpectedItems() {
        Invoice testData = new Invoice();
        testData.setNumber(42);
        String json = new Gson().toJson(testData);
        final InvoiceListViewModel sut = new InvoiceListViewModel(new StringReaderSource(json));
        
        sut.getLoadInvoicesCommand().execute();
        
        assertThat(sut.getItems().size(), is(1));
        assertThat(sut.getItems(), everyItem(equalTo(testData)));
    }
    
    @Test
    public void givenNewInstance_afterExecutingLoadCommandWhenInputSourceDoesNotContainJson_invoiceListIsEmpty() {
        final InvoiceListViewModel sut = new InvoiceListViewModel(new StringReaderSource("some-invalid-data"));
        
        sut.getLoadInvoicesCommand().execute();
        
        assertTrue(sut.getItems().isEmpty());
    }
    
    @Test
    public void givenNewInstance_whenIOExceptionOccurs_invoiceIsEmpty() {
        final Reader ioExceptionReader = new Reader() {
            @Override
            public int read(char[] chars, int i, int i1) throws IOException {
                return -1;
            }
            @Override
            public void close() throws IOException {
                throw new IOException("Test exception");
            }
        };
        final Iterator<Reader> ioExceptionSource = new Iterator<Reader>() {
            private boolean read = false;
            @Override
            public boolean hasNext() {
                return !read;
            }

            @Override
            public Reader next() {
                read = true;
                return ioExceptionReader;
            }            
        };
        final InvoiceListViewModel sut = new InvoiceListViewModel(ioExceptionSource);
        
        sut.getLoadInvoicesCommand().execute();
        
        assertTrue(sut.getItems().isEmpty());
    }

    private static class StringReaderSource implements Iterator<Reader>{
        private final Iterator<String> sourcesIterator;
        
        public StringReaderSource(String... sources) {
            this.sourcesIterator = Arrays.asList(sources).iterator();
        }

        @Override
        public boolean hasNext() {
            return this.sourcesIterator.hasNext();
        }

        @Override
        public Reader next() {
            String content = (String)this.sourcesIterator.next();
            return new StringReader(content);
        }
    }
}
