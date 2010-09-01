package com.google.code.reelcash.data.documents;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.banks.CurrencyNode;
import com.google.code.reelcash.data.goods.UnitNode;
import com.google.code.reelcash.data.layout.LinkLayoutNode;
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
public class InvoiceDetailNode extends LinkLayoutNode {

    private static final String TABLE_NAME = "invoice_details";
    private static final Object SYNC_ROOT = new Object();
    private static InvoiceDetailNode instance;

    private InvoiceDetailNode() {
        super(InvoiceNode.getInstance(), TABLE_NAME);

        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new ReferenceField(InvoiceNode.getInstance().getIdField(), "invoice_id", true));
        getFieldList().add(new IntegerField("position", KeyRole.NONE, true));
        getFieldList().add(new ReferenceField(CurrencyNode.getInstance().getIdField(), "good_id", false));
        getFieldList().add(new StringField("detail_text", KeyRole.NONE, false));

        getFieldList().add(new ReferenceField(UnitNode.getInstance().getIdField(), "unit_id", false));
        getFieldList().add(new BigDecimalField("quantity", KeyRole.NONE, true, 2, 9));
        getFieldList().add(new BigDecimalField("unit_price", KeyRole.NONE, true, 4, 11));
        getFieldList().add(new BigDecimalField("tax_amount", KeyRole.NONE, true, 2, 9));
        getFieldList().add(new BigDecimalField("excise_amount", KeyRole.NONE, true, 2, 9));
        getFieldList().add(new BigDecimalField("amount", KeyRole.NONE, true, 4, 15));
        getFieldList().add(new BigDecimalField("vat_percent", KeyRole.NONE, true, 2, 3));
        getFieldList().add(new BigDecimalField("vat_amount", KeyRole.NONE, true, 4, 15));
        getFieldList().add(new BigDecimalField("price", KeyRole.NONE, true, 4, 15));

    }

    /**
     * Returns the singleton instance of this class.
     *
     * @return the singleton instance which describes an invoice.
     */
    public static InvoiceDetailNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance) {
                instance = new InvoiceDetailNode();
            }
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public ReferenceField getInvoiceIdField() {
        return (ReferenceField) getFieldList().get(1);
    }

    public IntegerField getPositionField() {
        return (IntegerField) getFieldList().get(2);
    }

    public ReferenceField getGoodIdField() {
        return (ReferenceField) getFieldList().get(3);
    }

    public StringField getDetailTextField() {
        return (StringField) getFieldList().get(4);
    }

    public ReferenceField getUnitIdField() {
        return (ReferenceField) getFieldList().get(5);
    }

    public BigDecimalField getQuantityField() {
        return (BigDecimalField) getFieldList().get(6);
    }

    public BigDecimalField getUnitPriceField() {
        return (BigDecimalField) getFieldList().get(7);
    }

    public BigDecimalField getTaxAmountField() {
        return (BigDecimalField) getFieldList().get(8);
    }

    public BigDecimalField getExciseAmountField() {
        return (BigDecimalField) getFieldList().get(9);
    }
    
    public BigDecimalField getAmountField() {
        return (BigDecimalField) getFieldList().get(10);
    }

    public BigDecimalField getVatPercentField() {
        return (BigDecimalField) getFieldList().get(11);
    }

    public BigDecimalField getVatAmountField() {
        return (BigDecimalField) getFieldList().get(12);
    }

    public BigDecimalField getPriceField() {
        return (BigDecimalField) getFieldList().get(13);
    }
}
