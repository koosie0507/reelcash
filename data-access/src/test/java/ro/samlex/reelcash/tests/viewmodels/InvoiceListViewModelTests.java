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
import ro.samlex.reelcash.data.Invoice;
import ro.samlex.reelcash.io.InputSource;

import ro.samlex.reelcash.viewmodels.InvoiceListViewModel;
import ro.samlex.reelcash.viewmodels.SelectorViewModel;

public class InvoiceListViewModelTests {

    @Test
    public void givenNewInstance_inAnyCase_itIsASelectorViewModel() {
        assertThat(new InvoiceListViewModel(), instanceOf(SelectorViewModel.class));
    }

    @Test
    public void givenNewInstance_afterExecutingLoadCommand_invoiceListContainsExpectedItems() {
        Invoice testData = new Invoice();
        testData.setNumber(42);
        String json = new Gson().toJson(testData);
        final InvoiceListViewModel sut = new InvoiceListViewModel();

        sut.loadAll(new StringInputSourceSeries(json));

        assertThat(sut.getItems().size(), is(1));
        assertThat(sut.getItems(), everyItem(equalTo(testData)));
    }

    @Test
    public void givenNewInstance_afterExecutingLoadCommandWhenInputSourceDoesNotContainJson_invoiceListIsEmpty() {
        final InvoiceListViewModel sut = new InvoiceListViewModel();

        sut.loadAll(new StringInputSourceSeries("some-invalid-data"));

        assertTrue(sut.getItems().isEmpty());
    }

    @Test
    public void givenNewInstance_whenIOExceptionOccurs_invoiceIsEmpty() {
        final InvoiceListViewModel sut = new InvoiceListViewModel();

        sut.loadAll(Arrays.asList((InputSource)new IOExceptionReaderSource()));

        assertTrue(sut.getItems().isEmpty());
    }

    @Test
    public void givenNewInstance_loadingTwice_keepsDataOnlyFromOneOperation() {
        String str1 = "{\"uuid\":\"685db19f-c70d-4677-b28c-94d468854ff1\",\"number\":42,\"date\":\"Feb 19, 2016 10:00:00 AM\",\"invoicedItems\":[]}";
        String str2 = "{\"uuid\":\"685db19f-c70e-4677-b28c-94d468854ff1\",\"number\":43,\"date\":\"Feb 20, 2016 10:00:00 AM\",\"invoicedItems\":[]}";
        final InvoiceListViewModel sut = new InvoiceListViewModel();
        sut.loadAll(new StringInputSourceSeries(str1));

        sut.loadAll(new StringInputSourceSeries(str2));

        assertThat(sut.getItems().size(), is(1));
        assertThat(sut.getItems().get(0).getNumber(), equalTo(43));
    }

    private static class StringInputSourceSeries implements Iterable<InputSource> {

        private final String[] data;

        public StringInputSourceSeries(String... data) {
            this.data = data;
        }

        @Override
        public Iterator<InputSource> iterator() {
            return new Iterator<InputSource>() {
                private final Iterator<String> dataIterator = Arrays.asList(data).iterator();

                @Override
                public boolean hasNext() {
                    return dataIterator.hasNext();
                }

                @Override
                public InputSource next() {
                    return new StringReaderSource(dataIterator.next());
                }
            };
        }
    }

    private static class StringReaderSource implements InputSource {

        private final String str;

        public StringReaderSource(String str) {
            this.str = str;
        }

        @Override
        public Reader newReader() {
            return new StringReader(this.str);
        }
    }

    private static class IOExceptionReaderSource implements InputSource {

        @Override
        public Reader newReader() throws java.io.IOException {
            throw new IOException();
        }
    }
}
