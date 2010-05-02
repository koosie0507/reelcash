package com.google.code.reelcash.data.documents;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.LeafLayoutNode;
import com.google.code.reelcash.data.layout.fields.BigDecimalField;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.ReferenceField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 * The singleton instance of this class provides the data layout for a
 * detail of the invoice node.
 *
 * @author cusi
 */
public class InvoiceDetailTaxNode extends LeafLayoutNode {

    private static final String TABLE_NAME = "invoice_detail_taxes";
    private static final Object SYNC_ROOT = new Object();
    private static InvoiceDetailTaxNode instance;

    private InvoiceDetailTaxNode() {
        super(InvoiceDetailNode.getInstance(), TABLE_NAME);

        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new ReferenceField(InvoiceDetailNode.getInstance().getIdField(), "invoice_detail_id", true));
        getFieldList().add(new StringField("tax_code", KeyRole.NONE, true));
        getFieldList().add(new BigDecimalField("amount", KeyRole.NONE, true, 4, 15));

    }

    /**
     * Returns the singleton instance of this class.
     *
     * @return the singleton instance which describes an invoice.
     */
    public static InvoiceDetailTaxNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance) {
                instance = new InvoiceDetailTaxNode();
            }
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public ReferenceField getInvoiceDetailIdField() {
        return (ReferenceField) getFieldList().get(1);
    }

    public StringField getTaxCodeField() {
        return (StringField) getFieldList().get(2);
    }

    public BigDecimalField getAmountField() {
        return (BigDecimalField) getFieldList().get(3);
    }
}
