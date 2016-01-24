package ro.samlex.reelcash.tests.data;

import com.google.gson.Gson;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;
import ro.samlex.reelcash.data.Invoice;
import ro.samlex.reelcash.data.InvoiceItem;
import ro.samlex.reelcash.data.Party;

public class InvoiceTests {

    @Test(expected = NullPointerException.class)
    public void given_new_invoice__setting_null_contact__throws_NullPointerException() {
        Invoice sut = new Invoice();
        sut.setRecipient(null);
    }

    @Test
    public void given_new_invoice__it_is_allowed_to_set_non_null_invoiced_party() {
        final Party party = new Party();
        Invoice sut = new Invoice();

        sut.setRecipient(party);

        Assert.assertSame(party, sut.getRecipient());
    }

    @Test
    public void given_new_invoice__it_has_an_empty_list_of_invoiced_items() {
        final Invoice sut = new Invoice();

        List<InvoiceItem> invoicedItems = sut.getInvoicedItems();

        Assert.assertNotNull(invoicedItems);
        Assert.assertTrue(invoicedItems.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void given_new_invoice__setting_a_null_emitter__throws_NullPointerException() {
        final Invoice sut = new Invoice();

        sut.setEmitter(null);
    }

    @Test(expected = IllegalStateException.class)
    public void given_new_invoice__attempting_to_save__throws_IllegalStateException() throws IOException {
        final Invoice sut = new Invoice();

        sut.save(new ByteArrayOutputStream());
    }

    @Test(expected = NullPointerException.class)
    public void given_new_invoice__save_on_null_OutputStream__throws_NullPointerException() throws IOException {
        final Invoice sut = new Invoice();

        sut.save(null);
    }

    @Test
    public void given_invoice_with_data__save_on_valid_stream__saves_expected_content() {
        final Invoice sut = new Invoice();
        sut.setEmitter(new Party());
        sut.setRecipient(new Party());
        final String expected = new Gson().toJson(sut);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            sut.save(baos);
            String actual = baos.toString("utf-8");

            Assert.assertEquals(expected, actual);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }
    
    @Test(expected = IllegalStateException.class)
    public void given_invoice_without_recipient__attempting_to_save__throws_IllegalStateException() throws IOException {
        final Invoice sut = new Invoice();
        sut.setEmitter(new Party());

        sut.save(new ByteArrayOutputStream());
    }
    
    @Test
    public void given_two_new_invoices__just_after_they_were_created__their_identifiers_are_different() {
        final Invoice sut1 = new Invoice();
        final Invoice sut2 = new Invoice();
        
        Assert.assertNotEquals(sut1.getUuid(), sut2.getUuid());
    }
    
    @Test(expected = NullPointerException.class)
    public void load_invoice__input_stream_is_null__throws_NullPointerException() throws IOException {
        final Invoice sut = new Invoice();
        
        sut.load(null);
    }
    
    @Test
    public void load_invoice__valid_data_on_input_stream__loads_data_to_invoice() throws IOException {
        final Invoice sut = new Invoice();
        final Invoice savedInvoice = new Invoice();
        final UUID savedUuid = savedInvoice.getUuid();
        String json = new Gson().toJson(savedInvoice);
        
        try(ByteArrayInputStream bais = new ByteArrayInputStream(json.getBytes())) {
            sut.load(bais);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertEquals(savedUuid, sut.getUuid());
    }
}
