package ro.samlex.reelcash.ui;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.table.TableModel;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import ro.samlex.reelcash.Application;
import ro.samlex.reelcash.data.Invoice;
import ro.samlex.reelcash.data.InvoiceItem;
import ro.samlex.reelcash.io.InvoiceStreamFactory;

public class MainWindow extends javax.swing.JFrame {

    public MainWindow() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        invoicedContactPanel = new ro.samlex.reelcash.ui.components.JContactPanel();
        invoicedItemsPanel = new javax.swing.JPanel();
        invoicedItemsTableScroller = new javax.swing.JScrollPane();
        invoiceDetailsTable = new javax.swing.JTable();
        actionItemsPanel = new javax.swing.JPanel();
        saveInvoiceButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        invoicedContactPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(16, 8, 8, 8), "Invoiced Party Contact Information", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 0, 14))); // NOI18N
        getContentPane().add(invoicedContactPanel, java.awt.BorderLayout.PAGE_START);

        invoicedItemsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(16, 8, 8, 8), "Invoiced items", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 14))); // NOI18N
        invoicedItemsPanel.setLayout(new javax.swing.BoxLayout(invoicedItemsPanel, javax.swing.BoxLayout.Y_AXIS));

        invoiceDetailsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"",  new Integer(1), null,  new Double(0.24)}
            },
            new String [] {
                "Name", "Quantity", "Unit Price", "VAT"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        invoicedItemsTableScroller.setViewportView(invoiceDetailsTable);

        invoicedItemsPanel.add(invoicedItemsTableScroller);

        getContentPane().add(invoicedItemsPanel, java.awt.BorderLayout.CENTER);

        saveInvoiceButton.setText("Save Invoice");
        saveInvoiceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveInvoiceButtonActionPerformed(evt);
            }
        });
        actionItemsPanel.add(saveInvoiceButton);

        getContentPane().add(actionItemsPanel, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveInvoiceButtonActionPerformed
        Invoice invoice = new Invoice();
        try {
            invoice.setDate(new LocalDate(DateTimeZone.forOffsetHours(2)));
            invoice.setEmitter(Application.getInstance().getCompany());
            invoice.setRecipient(invoicedContactPanel.createParty());
            final TableModel tableModel = invoiceDetailsTable.getModel();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                InvoiceItem item = new InvoiceItem();
                item.setName((String) tableModel.getValueAt(i, 0));
                item.setQuantity((Integer) tableModel.getValueAt(i, 1));
                item.setUnitPrice((Double) tableModel.getValueAt(i, 2));
                item.setVat((Double) tableModel.getValueAt(i, 3));
                invoice.getInvoicedItems().add(item);
            }
            try (OutputStream os = new InvoiceStreamFactory(invoice.getUuid().toString()).createOutputStream()) {
                invoice.save(os);
            } catch (IOException e) {
                ApplicationMessages.showError(this, e.getMessage());
            }
        } catch (NullPointerException e) {
            ApplicationMessages.showError(this, "Some required information is missing: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            ApplicationMessages.showError(this, "There is invalid data in this invoice: " + e.getMessage());
        }
    }//GEN-LAST:event_saveInvoiceButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionItemsPanel;
    private javax.swing.JTable invoiceDetailsTable;
    private ro.samlex.reelcash.ui.components.JContactPanel invoicedContactPanel;
    private javax.swing.JPanel invoicedItemsPanel;
    private javax.swing.JScrollPane invoicedItemsTableScroller;
    private javax.swing.JButton saveInvoiceButton;
    // End of variables declaration//GEN-END:variables
}
