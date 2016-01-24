package ro.samlex.reelcash.ui;

import java.io.IOException;
import java.io.OutputStream;
import ro.samlex.reelcash.data.Invoice;
import ro.samlex.reelcash.io.InvoiceStreamFactory;

public class MainWindow extends javax.swing.JFrame {

    public MainWindow() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        modifyInvoicePanel = new javax.swing.JPanel();
        invoicePanel = new ro.samlex.reelcash.ui.components.JInvoicePanel();
        invoiceActionsPanel = new javax.swing.JPanel();
        saveInvoiceButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        invoiceListPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());

        modifyInvoicePanel.setLayout(new javax.swing.BoxLayout(modifyInvoicePanel, javax.swing.BoxLayout.Y_AXIS));
        modifyInvoicePanel.add(invoicePanel);

        saveInvoiceButton.setMnemonic('s');
        saveInvoiceButton.setText("Save");
        saveInvoiceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveInvoiceButtonActionPerformed(evt);
            }
        });
        invoiceActionsPanel.add(saveInvoiceButton);

        cancelButton.setMnemonic('c');
        cancelButton.setText("Cancel");
        cancelButton.setToolTipText("");
        invoiceActionsPanel.add(cancelButton);

        modifyInvoicePanel.add(invoiceActionsPanel);

        getContentPane().add(modifyInvoicePanel, "card3");
        getContentPane().add(invoiceListPanel, "card4");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveInvoiceButtonActionPerformed
        try {
            Invoice invoice = invoicePanel.createInvoice();
            try (OutputStream os = new InvoiceStreamFactory(invoice.getUuid().toString()).createOutputStream()) {
                invoice.save(os);
            } catch (IOException e) {
                ApplicationMessages.showError(this, "Couldn't save invoice: " + e.getMessage());
            }
        } catch (NullPointerException e) {
            ApplicationMessages.showError(this, "Missing information: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            ApplicationMessages.showError(this, "Invalid information: " + e.getMessage());
        }
    }//GEN-LAST:event_saveInvoiceButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel invoiceActionsPanel;
    private javax.swing.JPanel invoiceListPanel;
    private ro.samlex.reelcash.ui.components.JInvoicePanel invoicePanel;
    private javax.swing.JPanel modifyInvoicePanel;
    private javax.swing.JButton saveInvoiceButton;
    // End of variables declaration//GEN-END:variables
}
