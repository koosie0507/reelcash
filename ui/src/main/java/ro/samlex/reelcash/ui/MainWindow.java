package ro.samlex.reelcash.ui;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.table.TableModel;
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

        headerPanel = new javax.swing.JPanel();
        lblNumber = new javax.swing.JLabel();
        numberSpinner = new javax.swing.JSpinner();
        lblDate = new javax.swing.JLabel();
        dateSpinner = new javax.swing.JSpinner();
        invoicedContactPanel = new ro.samlex.reelcash.ui.components.JContactPanel();
        invoicedItemsPanel = new javax.swing.JPanel();
        invoicedItemsTableScroller = new javax.swing.JScrollPane();
        invoiceDetailsTable = new javax.swing.JTable();
        actionItemsPanel = new javax.swing.JPanel();
        saveInvoiceButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        headerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(32, 16, 32, 16));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 4);
        flowLayout1.setAlignOnBaseline(true);
        headerPanel.setLayout(flowLayout1);

        lblNumber.setText("Invoice Number:");
        headerPanel.add(lblNumber);

        numberSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        numberSpinner.setMaximumSize(new java.awt.Dimension(120, 23));
        numberSpinner.setMinimumSize(new java.awt.Dimension(45, 23));
        numberSpinner.setPreferredSize(new java.awt.Dimension(60, 23));
        headerPanel.add(numberSpinner);

        lblDate.setText("/");
        headerPanel.add(lblDate);

        dateSpinner.setModel(new javax.swing.SpinnerDateModel());
        dateSpinner.setEditor(new javax.swing.JSpinner.DateEditor(dateSpinner, "dd.MM.yyyy"));
        dateSpinner.setMaximumSize(new java.awt.Dimension(240, 23));
        dateSpinner.setMinimumSize(new java.awt.Dimension(120, 23));
        dateSpinner.setPreferredSize(new java.awt.Dimension(150, 23));
        headerPanel.add(dateSpinner);

        getContentPane().add(headerPanel);

        invoicedContactPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(16, 8, 8, 8), "Invoiced Party Contact Information", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 0, 14))); // NOI18N
        getContentPane().add(invoicedContactPanel);

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

        getContentPane().add(invoicedItemsPanel);

        saveInvoiceButton.setText("Save Invoice");
        saveInvoiceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveInvoiceButtonActionPerformed(evt);
            }
        });
        actionItemsPanel.add(saveInvoiceButton);

        getContentPane().add(actionItemsPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveInvoiceButtonActionPerformed
        Invoice invoice = new Invoice();
        try {
            invoice.setNumber((Integer)numberSpinner.getModel().getValue());
            invoice.setDate(LocalDate.fromDateFields((java.util.Date)dateSpinner.getModel().getValue()));
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
    private javax.swing.JSpinner dateSpinner;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JTable invoiceDetailsTable;
    private ro.samlex.reelcash.ui.components.JContactPanel invoicedContactPanel;
    private javax.swing.JPanel invoicedItemsPanel;
    private javax.swing.JScrollPane invoicedItemsTableScroller;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblNumber;
    private javax.swing.JSpinner numberSpinner;
    private javax.swing.JButton saveInvoiceButton;
    // End of variables declaration//GEN-END:variables
}
