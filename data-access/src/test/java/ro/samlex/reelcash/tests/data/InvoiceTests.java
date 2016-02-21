package ro.samlex.reelcash.tests.data;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import static org.junit.Assert.*;
import ro.samlex.reelcash.data.Invoice;
import ro.samlex.reelcash.data.InvoiceItem;
import ro.samlex.reelcash.data.Party;

public class InvoiceTests {

    @Test(expected = IllegalArgumentException.class)
    public void givenNewInvoice_settingNullRecipient_throwsIllegalArgumentException() {
        Invoice sut = new Invoice();
        sut.setRecipient(null);
    }

    @Test
    public void givenNewInvoice_settingNonNullRecipient_storesRecipientValue() {
        final Party party = new Party();
        Invoice sut = new Invoice();

        sut.setRecipient(party);

        assertSame(party, sut.getRecipient());
    }

    @Test
    public void givenNewInvoice_inInitialState_invoicedItemsIsEmpty() {
        final Invoice sut = new Invoice();

        List<InvoiceItem> invoicedItems = sut.getInvoicedItems();

        assertNotNull(invoicedItems);
        assertTrue(invoicedItems.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenNewInvoice_settingNullEmitter_throwsIllegalArgumentException() {
        final Invoice sut = new Invoice();

        sut.setEmitter(null);
    }

    @Test
    public void givenNewInvoice_settingNonNullEmitter_storesEmitter() {
        final Invoice sut = new Invoice();
        final Party party = new Party();

        sut.setEmitter(party);

        assertSame(party, sut.getEmitter());
    }

    @Test
    public void givenTwoNewInvoices_inInitialState_theyAreDifferent() {
        final Invoice sut1 = new Invoice();
        final Invoice sut2 = new Invoice();

        assertNotEquals(sut1, sut2);
    }

    @Test
    public void givenTwoNewInvoices_inInitialState_theirHashCodesAreDifferent() {
        final Invoice sut1 = new Invoice();
        final Invoice sut2 = new Invoice();

        assertNotEquals(sut1.hashCode(), sut2.hashCode());
    }

    @Test
    public void givenInvoice_inInitialState_isNotEqualToNull() {
        final Invoice sut = new Invoice();
        assertFalse(sut.equals(null));
    }

    @Test
    public void givenInvoice_inInitialState_isNotEqualToInstanceOfOtherType() {
        final Invoice sut = new Invoice();
        assertFalse(sut.equals(new Object()));
    }

    @Test
    public void givenInvoice_inInitialState_dateDoesNotHaveTimeComponent() {
        final Invoice sut = new Invoice();
        final Calendar actual = Calendar.getInstance();

        actual.setTime(sut.getDate());

        assertEquals(0, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, actual.get(Calendar.MINUTE));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(0, actual.get(Calendar.MILLISECOND));
    }
    
    @Test
    public void givenTwoInvoices_inInitialState_theirStringRepresentationsDiffer() {
        Invoice sut1 = new Invoice();
        Invoice sut2 = new Invoice();
        
        assertNotEquals(sut1.toString(), sut2.toString());
    }
    
    @Test
    public void givenInvoice_inInitialState_stringRepresentationIsValidUUID() {
        assertNotNull(UUID.fromString(new Invoice().toString()));
    }
}
