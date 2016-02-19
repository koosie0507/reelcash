package ro.samlex.reelcash.tests.data;

import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;
import ro.samlex.reelcash.data.InvoiceItem;

public class InvoiceItemTests {

    @Test(expected = IllegalArgumentException.class)
    public void givenItem_settingNegativeVat_throwsIllegalArgumentException() {
        final InvoiceItem item = new InvoiceItem();

        item.setVat(0 - Math.ulp(1.0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenItem_settingVatGreaterThanOne_throwsIllegalArgumentException() {
        final InvoiceItem item = new InvoiceItem();

        item.setVat(1 + Math.ulp(1.0));
    }

    @Test
    public void givenItem_setValidVat_isSuccessful() {
        final InvoiceItem item = new InvoiceItem();

        item.setVat(0.5);

        assertEquals(0.5, item.getVAT(), Math.ulp(1.0));
    }

    @Test
    public void givenItem_setValidQuantity_isSuccessful() {
        final InvoiceItem item = new InvoiceItem();
        final double qty = 0 + Math.ulp(1.0);

        item.setQuantity(qty);

        assertEquals(qty, item.getQuantity(), Math.ulp(1.0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenItem_settingNegativeQuantity_throwsIllegalArgumentException() {
        final InvoiceItem item = new InvoiceItem();

        item.setQuantity(0 - Math.ulp(1.0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenItem_settingZeroQuantity_throwsIllegalArgumentException() {
        final InvoiceItem item = new InvoiceItem();

        item.setQuantity(0);
    }

    @Test
    public void givenTwoInvoiceItems_withSameData_itemsAreEqual() {
        assertEquals(
                item("abc", 1, 0.1, 0.1),
                item("abc", 1, 0.1, 0.1)
        );
    }

    @Test
    public void givenTwoInvoiceItems_whenNamesDiffer_itemsAreNotEqual() {
        assertNotEquals(
                item("a", 1, 0.1, 0.1),
                item("b", 1, 0.1, 0.1)
        );
    }

    @Test
    public void givenTwoInvoiceItems_whenQuantitiesDiffer_itemsAreNotEqual() {
        assertNotEquals(
                item("a", 1, 0.1, 0.1),
                item("a", 2, 0.1, 0.1)
        );
    }

    @Test
    public void givenTwoInvoiceItems_whenPricesDiffer_itemsAreNotEqual() {
        assertNotEquals(
                item("a", 1, 0.1, 0.1),
                item("a", 1, 0.2, 0.1)
        );
    }

    @Test
    public void givenTwoInvoiceItems_whenVatsDiffer_itemsAreNotEqual() {
        assertNotEquals(
                item("a", 1, 0.1, 0.1),
                item("a", 1, 0.1, 0.2)
        );
    }

    @Test
    public void hashCode_whenDataExists_returnsNonZero() {
        assertNotEquals(0, item("a", 2, 0.1, 0.2).hashCode());
    }

    private static InvoiceItem item(String name, int qty, double price, double vat) {
        InvoiceItem result = new InvoiceItem();
        result.setName(name);
        result.setQuantity(qty);
        result.setUnitPrice(price);
        result.setVat(vat);
        return result;
    }
}
