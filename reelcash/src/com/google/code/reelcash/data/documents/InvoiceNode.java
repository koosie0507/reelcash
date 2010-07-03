package com.google.code.reelcash.data.documents;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.banks.CurrencyNode;
import com.google.code.reelcash.data.contacts.ContactNode;
import com.google.code.reelcash.data.layout.RootLayoutNode;
import com.google.code.reelcash.data.layout.fields.BigDecimalField;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.ReferenceField;

/**
 * The singleton instance of this class provides the data layout information
 * for an invoice.
 * 
 * @author cusi
 */
public class InvoiceNode extends RootLayoutNode {

    private static final String TABLE_NAME = "invoices";
    private static final Object SYNC_ROOT = new Object();
    private static InvoiceNode instance;

    private InvoiceNode() {
        super(TABLE_NAME);

        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new ReferenceField(DocumentNode.getInstance().getIdField(), "document_id", true));
        getFieldList().add(new ReferenceField(CurrencyNode.getInstance().getIdField(), "currency_id", true));
        getFieldList().add(new ReferenceField(ContactNode.getInstance().getIdField(), "issuer_rep_id", false));
        getFieldList().add(new ReferenceField(ContactNode.getInstance().getIdField(), "recipient_rep_id", false));
        getFieldList().add(new IntegerField("exchange_rate_id", KeyRole.NONE, false));
        getFieldList().add(new BigDecimalField("total_amount", KeyRole.NONE, true, 4, 15));
        getFieldList().add(new BigDecimalField("total_excise", KeyRole.NONE, true, 4, 15));
        getFieldList().add(new BigDecimalField("total_taxes", KeyRole.NONE, true, 4, 15));
        getFieldList().add(new BigDecimalField("total_vat", KeyRole.NONE, true, 4, 15));

    }

    /**
     * Returns the singleton instance of this class.
     *
     * @return the singleton instance which describes an invoice.
     */
    public static InvoiceNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new InvoiceNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public ReferenceField getDocumentIdField() {
        return (ReferenceField) getFieldList().get(1);
    }

    public ReferenceField getCurrencyIdField() {
        return (ReferenceField) getFieldList().get(2);
    }

    public ReferenceField getIssuerRepIdField() {
        return (ReferenceField) getFieldList().get(3);
    }

    public ReferenceField getRecipientRepIdField() {
        return (ReferenceField) getFieldList().get(4);
    }

    public IntegerField getExchangeRateIdField() {
        return (IntegerField) getFieldList().get(5);
    }

    public BigDecimalField getTotalAmountField() {
        return (BigDecimalField) getFieldList().get(6);
    }

    public BigDecimalField getTotalExciseField() {
        return (BigDecimalField) getFieldList().get(7);
    }

    public BigDecimalField getTotalTaxesField() {
        return (BigDecimalField) getFieldList().get(8);
    }

    public BigDecimalField getTotalVatField() {
        return (BigDecimalField) getFieldList().get(9);
    }
}
