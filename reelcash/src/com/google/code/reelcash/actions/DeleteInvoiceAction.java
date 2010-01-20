/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.code.reelcash.actions;

import com.google.code.reelcash.data.InvoicesAdapter;
import com.google.code.reelcash.data.PrimaryKeyRow;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cusi
 */
public class DeleteInvoiceAction extends AbstractAction {
    private JTable invoicesTable;
    private InvoicesAdapter adapter;

    public DeleteInvoiceAction(InvoicesAdapter adapter, JTable invoicesTable) {
        this.invoicesTable = invoicesTable;
        this.adapter = adapter;
    }

    public void actionPerformed(ActionEvent e) {
        if(invoicesTable.getSelectedRow()<0)return;
        DefaultTableModel model = (DefaultTableModel)invoicesTable.getModel();
        PrimaryKeyRow invoicePk = new PrimaryKeyRow("invoiceid");
        invoicePk.set("invoiceid", model.getValueAt(invoicesTable.getSelectedRow(), 0));
        adapter.delete(invoicePk);
        adapter.populateTableModel(model);
    }
}
