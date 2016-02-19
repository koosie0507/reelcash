package ro.samlex.reelcash.tests.data;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import ro.samlex.reelcash.data.Invoice;
import ro.samlex.reelcash.data.InvoiceItem;
import ro.samlex.reelcash.data.Party;

public class InvoiceTests {

    @Test(expected = IllegalArgumentException.class)
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

    @Test(expected = IllegalArgumentException.class)
    public void given_new_invoice__setting_a_null_emitter__throws_NullPointerException() {
        final Invoice sut = new Invoice();

        sut.setEmitter(null);
    }

    @Test
    public void given_two_new_invoices__just_after_they_were_created__their_identifiers_are_different() {
        final Invoice sut1 = new Invoice();
        final Invoice sut2 = new Invoice();
        
        Assert.assertNotEquals(sut1.getUuid(), sut2.getUuid());
    }
}
