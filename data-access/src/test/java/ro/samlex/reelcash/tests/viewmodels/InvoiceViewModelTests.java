package ro.samlex.reelcash.tests.viewmodels;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import ro.samlex.reelcash.data.Invoice;
import ro.samlex.reelcash.data.InvoiceItem;
import ro.samlex.reelcash.data.Party;
import ro.samlex.reelcash.tests.io.IOExceptionOutputSink;
import ro.samlex.reelcash.tests.io.NullWriterOutputSink;
import ro.samlex.reelcash.tests.io.StringListOutputSink;
import ro.samlex.reelcash.viewmodels.InvoiceViewModel;
import ro.samlex.reelcash.viewmodels.SelectorViewModel;

public class InvoiceViewModelTests {

    private static void setUpViewModel(InvoiceViewModel viewModel) {
        Party emitter = new Party();
        emitter.setName("emitter");
        Party recipient = new Party();
        recipient.setName("recipient");
        emitter.street("test street 1").country("test 1").accountNumber("some account 1");
        recipient.street("test street 2").country("test 2").accountNumber("some account 2");
        viewModel.getModel().setEmitter(emitter);
        viewModel.getModel().setRecipient(recipient);
        viewModel.getModel().getInvoicedItems().add(new InvoiceItem());
    }

    @Test
    public void givenViewModel_inInitialState_modelIsNotNull() {
        assertNotNull(new InvoiceViewModel().getModel());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenViewModel_whenSavingToNullOutputSink_throwsIllegalArgumentException() {
        InvoiceViewModel sut = new InvoiceViewModel();

        try {
            sut.save(null);
        } catch (IOException e) {
            fail("Unable to save due to I/O error: " + e.getMessage());
        }
    }

    @Test
    public void givenViewModel_whenSavingTestInvoice_savedTextIsAsExpected() {
        final StringListOutputSink outputSink = new StringListOutputSink();
        InvoiceViewModel sut = new InvoiceViewModel();
        setUpViewModel(sut);

        try {
            sut.save(outputSink);
        } catch (IOException e) {
            fail("Unable to save: " + e.getMessage());
        }

        String expected = new Gson().toJson(sut.getModel());
        assertEquals(expected, outputSink.getWrittenValues().get(0));
    }

    @Test
    public void givenViewModel_inAnyCase_extendsSelectionViewModel() {
        assertThat(new InvoiceViewModel(), instanceOf(SelectorViewModel.class));
    }

    @Test
    public void givenViewModel_settingNewModel_savesNewModel() {
        final StringListOutputSink outputSink = new StringListOutputSink();
        Invoice expectedModel = new Invoice();
        expectedModel.setNumber(42);
        expectedModel.setDate(new Date());
        InvoiceViewModel sut = new InvoiceViewModel();
        setUpViewModel(sut);
        sut.setModel(expectedModel);

        try {
            sut.save(outputSink);
        } catch (IOException e) {
            fail("Unable to save: " + e.getMessage());
        }

        String expected = new Gson().toJson(expectedModel);
        assertEquals(expected, outputSink.getWrittenValues().get(0));
    }

    @Test(expected = IOException.class)
    public void givenViewModel_savingToSinkThatCannotCreateWriter_throwsIOException() throws IOException {
        InvoiceViewModel sut = new InvoiceViewModel();

        sut.save(new IOExceptionOutputSink().throwOnNewWriter());
    }

    @Test(expected = IOException.class)
    public void givenViewModel_savingToSinkThatCannotWriteContent_throwsIOException() throws IOException {
        InvoiceViewModel sut = new InvoiceViewModel();

        sut.save(new IOExceptionOutputSink().throwOnWrite());
    }

    @Test(expected = IOException.class)
    public void givenViewModel_savingToSinkThatCannotCloseWriter_throwsIOException() throws IOException {
        InvoiceViewModel sut = new InvoiceViewModel();

        sut.save(new IOExceptionOutputSink().throwOnCloseWriter());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenViewModel_savingToSinkThatReturnsNullWriter_throwsIllegalArgumentException() throws IOException {
        InvoiceViewModel sut = new InvoiceViewModel();

        sut.save(new NullWriterOutputSink());
    }

    @Test
    public void givenViewModel_addingItem_itemIsAddedToModel() {
        InvoiceItem expected = new InvoiceItem();
        expected.setName("expected");
        expected.setUnitPrice(3.14159265);
        InvoiceViewModel sut = new InvoiceViewModel();
        sut.setModel(new Invoice());

        sut.getItems().add(expected);

        assertThat(sut.getModel().getInvoicedItems().size(), is(1));
        assertThat(sut.getModel().getInvoicedItems(), everyItem(equalTo(expected)));
    }

    @Test
    public void givenViewModel_removingItem_itemIsRemovedFromModel() {
        InvoiceItem unexpected = new InvoiceItem();
        unexpected.setName("unexpected");
        unexpected.setUnitPrice(3.14159265);
        InvoiceViewModel sut = new InvoiceViewModel();
        sut.setModel(new Invoice());
        sut.getItems().add(unexpected);

        sut.getItems().remove(unexpected);

        assertThat(sut.getModel().getInvoicedItems().size(), is(0));
    }

    @Test
    public void givenViewModel_replacingItem_itemIsReplacedInModel() {
        InvoiceItem expected = new InvoiceItem();
        expected.setName("expected");
        expected.setUnitPrice(3.14159265);
        InvoiceViewModel sut = new InvoiceViewModel();
        sut.setModel(new Invoice());
        sut.getItems().add(new InvoiceItem());

        sut.getItems().set(0, expected);

        assertThat(sut.getModel().getInvoicedItems().size(), is(1));
        assertThat(sut.getModel().getInvoicedItems(), everyItem(equalTo(expected)));
    }

    @Test
    public void givenViewModel_changingItemProperty_itemIsReplacedInModel() {
        InvoiceItem item = new InvoiceItem();
        item.setName("not expected");
        item.setUnitPrice(3.14159265);
        InvoiceViewModel sut = new InvoiceViewModel();
        sut.getItems().add(item);

        item.setName("expected");

        assertThat(sut.getModel().getInvoicedItems().size(), is(1));
        assertThat(sut.getModel().getInvoicedItems().get(0).getName(), is("expected"));
    }

    @Test
    public void givenViewModel_settingTheModel_itemsAreAddedToViewModel() {
        InvoiceItem expected = new InvoiceItem();
        expected.setName("expected");
        expected.setUnitPrice(3.14159265);
        Invoice model = new Invoice();
        model.getInvoicedItems().add(expected);
        InvoiceViewModel sut = new InvoiceViewModel();

        sut.setModel(model);

        assertThat(sut.getItems().size(), is(1));
        assertThat(sut.getItems(), everyItem(is(expected)));
    }

    @Test
    public void givenViewModel_settingNullModel_itemsAreCleared() {
        InvoiceItem item = new InvoiceItem();
        item.setName("expected");
        item.setUnitPrice(3.14159265);
        InvoiceViewModel sut = new InvoiceViewModel();
        sut.getModel().getInvoicedItems().add(item);

        sut.setModel(null);

        assertThat(sut.getItems().size(), is(0));
    }

    @Test(expected = IllegalStateException.class)
    public void givenViewModel_savingWhenModelIsNull_throwsIllegalStateException() {
        InvoiceViewModel sut = new InvoiceViewModel();
        sut.setModel(null);

        try {
            sut.save(new StringListOutputSink());
        } catch (IOException ex) {
            fail("i/o error on save: " + ex.getMessage());
        }
    }
}
