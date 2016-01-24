package ro.samlex.reelcash.ui;

import java.awt.CardLayout;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import ro.samlex.reelcash.data.Invoice;
import ro.samlex.reelcash.io.InvoiceStreamFactory;

public class MainWindow extends javax.swing.JFrame {

    private final DefaultListModel listModel = new DefaultListModel();

    private class LoadInvoicesRunnable implements Runnable {

        @Override
        public void run() {
            File f = new File(InvoiceStreamFactory.DIR_PATH);
            if (!f.exists() || !f.isDirectory()) {
                return;
            }
            for (File invoiceFile : f.listFiles()) {
                String fileName = invoiceFile.getName();
                fileName = fileName.substring(0, fileName.length()-5);
                try (InputStream is = new InvoiceStreamFactory(fileName).createInputStream()) {
                    Invoice invoiceOnDisk = new Invoice();
                    invoiceOnDisk.load(is);
                    listModel.addElement(invoiceOnDisk);
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public MainWindow() {
        initComponents();
        listModel.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                switchCard("list");
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
                if(listModel.size()<1) switchCard("welcome");
            }

            @Override
            public void contentsChanged(ListDataEvent e) {
                if(listModel.size()>0) switchCard("list");
                else switchCard("welcome");
            }            
        });
        SwingUtilities.invokeLater(new LoadInvoicesRunnable());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        instructionsPanel = new javax.swing.JPanel();
        instructionsTextArea = new javax.swing.JTextArea();
        addInvoiceButton = new javax.swing.JButton();
        invoiceListPanel = new javax.swing.JPanel();
        invoiceListScrollPane = new javax.swing.JScrollPane();
        invoiceList = new javax.swing.JList<>();
        actionsPanel = new javax.swing.JPanel();
        newInvoiceButton = new javax.swing.JButton();
        modifyInvoicePanel = new javax.swing.JPanel();
        invoicePanel = new ro.samlex.reelcash.ui.components.JInvoicePanel();
        invoiceActionsPanel = new javax.swing.JPanel();
        saveInvoiceButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());

        instructionsPanel.setLayout(new javax.swing.BoxLayout(instructionsPanel, javax.swing.BoxLayout.Y_AXIS));

        instructionsTextArea.setEditable(false);
        instructionsTextArea.setColumns(20);
        instructionsTextArea.setRows(5);
        instructionsTextArea.setTabSize(0);
        instructionsTextArea.setText("Seems you haven't added any invoices yet. You can do that by pressing the button at the bottom of the screen.");
        instructionsTextArea.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        instructionsTextArea.setFocusable(false);
        instructionsTextArea.setHighlighter(null);
        instructionsPanel.add(instructionsTextArea);

        addInvoiceButton.setText("Add Your First Invoice");
        addInvoiceButton.setAlignmentX(0.5F);
        addInvoiceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addInvoiceButtonActionPerformed(evt);
            }
        });
        instructionsPanel.add(addInvoiceButton);

        getContentPane().add(instructionsPanel, "welcome");

        invoiceListPanel.setLayout(new javax.swing.BoxLayout(invoiceListPanel, javax.swing.BoxLayout.Y_AXIS));

        invoiceList.setModel(listModel);
        invoiceList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        invoiceList.setCellRenderer(new ro.samlex.reelcash.ui.renderers.list.InvoiceRenderer());
        invoiceListScrollPane.setViewportView(invoiceList);

        invoiceListPanel.add(invoiceListScrollPane);

        newInvoiceButton.setMnemonic('n');
        newInvoiceButton.setText("New Invoice");
        newInvoiceButton.setToolTipText("");
        newInvoiceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newInvoiceButtonActionPerformed(evt);
            }
        });
        actionsPanel.add(newInvoiceButton);

        invoiceListPanel.add(actionsPanel);

        getContentPane().add(invoiceListPanel, "list");

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

        getContentPane().add(modifyInvoicePanel, "invoice");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void switchCard(String name) {
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), name);
    }

    private void put(Invoice invoice) {
        int index = listModel.indexOf(invoice);
        if (index < 0) {
            listModel.addElement(invoice);
        } else {
            listModel.setElementAt(invoice, index);
        }
    }

    private void saveInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveInvoiceButtonActionPerformed
        try {
            Invoice invoice = invoicePanel.createInvoice();
            try (OutputStream os = new InvoiceStreamFactory(invoice.getUuid().toString()).createOutputStream()) {
                invoice.save(os);
                put(invoice);
                switchCard("list");
            } catch (IOException e) {
                ApplicationMessages.showError(this, "Couldn't save invoice: " + e.getMessage());
            }
        } catch (NullPointerException e) {
            ApplicationMessages.showError(this, "Missing information: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            ApplicationMessages.showError(this, "Invalid information: " + e.getMessage());
        }
    }//GEN-LAST:event_saveInvoiceButtonActionPerformed

    private void addInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addInvoiceButtonActionPerformed
        switchCard("invoice");
    }//GEN-LAST:event_addInvoiceButtonActionPerformed

    private void newInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newInvoiceButtonActionPerformed
        switchCard("invoice");
    }//GEN-LAST:event_newInvoiceButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionsPanel;
    private javax.swing.JButton addInvoiceButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel instructionsPanel;
    private javax.swing.JTextArea instructionsTextArea;
    private javax.swing.JPanel invoiceActionsPanel;
    private javax.swing.JList<String> invoiceList;
    private javax.swing.JPanel invoiceListPanel;
    private javax.swing.JScrollPane invoiceListScrollPane;
    private ro.samlex.reelcash.ui.components.JInvoicePanel invoicePanel;
    private javax.swing.JPanel modifyInvoicePanel;
    private javax.swing.JButton newInvoiceButton;
    private javax.swing.JButton saveInvoiceButton;
    // End of variables declaration//GEN-END:variables
}
