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
import ro.samlex.reelcash.tests.io.StringListOutputSink;
import ro.samlex.reelcash.viewmodels.InvoiceViewModel;
import ro.samlex.reelcash.viewmodels.SelectorViewModel;

public class InvoiceViewModelTests {
    
    private static Invoice invoice(int number, Date date, Party emitter, Party recipient) {
        Invoice result = new Invoice();
        result.setNumber(number);
        result.setDate(date);
        result.setEmitter(emitter);
        result.setRecipient(recipient);
        return result;
    }

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
}
