package ro.samlex.reelcash.tests.data;

import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;
import ro.samlex.reelcash.data.InvoiceItem;


public class InvoiceItemTests {

    @Test(expected = NullPointerException.class)
    public void given_new_invoice_item__setting_null_name__throws_NullPointerException() {
        final InvoiceItem item = new InvoiceItem();

        item.setName(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void given_invoice_item__setting_vat_to_negative_value__throws_IllegalArgumentException() {
        final InvoiceItem item = new InvoiceItem();
        
        item.setVat(0-Math.ulp(1.0));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void given_invoice_item__setting_vat_to_more_than_1__throws_IllegalArgumentException() {
        final InvoiceItem item = new InvoiceItem();
        
        item.setVat(1+Math.ulp(1.0));
    }
    
    @Test
    public void given_invoice_item__setting_VAT_to_percentage_value__gets_the_set_VAT() {
        final InvoiceItem item = new InvoiceItem();
        
        item.setVat(0.5);
        
        assertEquals(0.5, item.getVAT(), Math.ulp(1.0));
    }
    
    @Test
    public void given_invoice_item__setting_positive_quantity__gets_set_quantity() {
        final InvoiceItem item = new InvoiceItem();
        final double qty = 0+Math.ulp(1.0);
        
        item.setQuantity(qty);
        
        assertEquals(qty, item.getQuantity(), Math.ulp(1.0));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void given_invoice_item___setting_negative_quantity__throws_IllegalArgumentException() {
        final InvoiceItem item = new InvoiceItem();
        
        item.setQuantity(0 - Math.ulp(1.0));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void given_invoice_item__setting_zero_quantity__throws_IllegalArgumentException() {
        final InvoiceItem item = new InvoiceItem();
        
        item.setQuantity(0);
    }
}
