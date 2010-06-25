package com.google.code.reelcash.data.documents;

import com.google.code.reelcash.ReelcashException;
import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.LeafLayoutNode;
import com.google.code.reelcash.data.layout.fields.StringField;
import com.google.code.reelcash.model.DataRowTableModel;
import java.sql.SQLException;

/**
 * Instances of this class provide a table model.
 * 
 * @author andrei.olar
 */
public class InvoiceDetailsTableModel extends DataRowTableModel {

    private static final long serialVersionUID = -5304642088984957684L;
    private static final LeafLayoutNode detailNode;

    static {
        detailNode = new LeafLayoutNode(InvoiceNode.getInstance(), "invoice_details");
        detailNode.getFieldList().addAll(InvoiceDetailNode.getInstance().getFieldList());
        detailNode.getFieldList().add(detailNode.getFieldList().indexOf("quantity"),
                new StringField("unit_code", KeyRole.NONE, false));
    }

    /**
     * Creates a new node based on the invoice details node.
     */
    public InvoiceDetailsTableModel() {
        super(detailNode);
    }

    /**
     * Populates the table model with the invoice details corresponding to the invoice ID. If a negative value is
     * passed, the model is cleared.
     *
     * @param invoiceId the ID of the parent invoice.
     */
    public void loadData(Integer invoiceId) {
        try {
            if (0 > invoiceId)
                clear();
            else {
                clearQuietly();
                for (DataRow row : InvoiceMediator.getInstance().fetch(detailNode.getFieldList(), "select d.id as id, d.invoice_id as invoice_id, d.position as position, d.good_id as good_id, coalesce(g.code, d.detail_text) as detail_text, d.unit_id as unit_id, u.code as unit_code, d.unit_price as unit_price, d.tax_amount as tax_amount, d.excise_amount as excise_amount, d.amount as amount, d.vat_percent as vat_percent, d.vat_amount as vat_amount, d.price as price from invoice_details d inner join units u on u.id = d.unit_id left join goods g on g.id = d.good_id where d.invoice_id = ?", invoiceId)) {
                    add(row);
                }
            }
        }
        catch (SQLException e) {
            throw new ReelcashException(e);
        }
    }
}
