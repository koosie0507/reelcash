package com.google.code.reelcash.data.documents;

import com.google.code.reelcash.model.DataRowTableModel;

/**
 * Instances of this class provide a table model.
 * 
 * @author andrei.olar
 */
public class InvoiceDetailsTableModel extends DataRowTableModel {

    private static final long serialVersionUID = -5304642088984957684L;

    /**
     * Creates a new node based on the invoice details node.
     */
    public InvoiceDetailsTableModel() {
        super(InvoiceDetailNode.getInstance());
    }
}
