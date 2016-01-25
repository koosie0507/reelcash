package ro.samlex.reelcash.ui;

import java.awt.CardLayout;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
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
                fileName = fileName.substring(0, fileName.length() - 5);
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
                if (listModel.size() < 1) {
                    switchCard("welcome");
                }
            }

            @Override
            public void contentsChanged(ListDataEvent e) {
                if (listModel.size() > 0) {
                    switchCard("list");
                } else {
                    switchCard("welcome");
                }
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
        invoiceActionsToolbar = new javax.swing.JToolBar();
        newInvoiceButton = new javax.swing.JButton();
        modifyInvoiceButton = new javax.swing.JButton();
        invoiceListScrollPane = new javax.swing.JScrollPane();
        invoiceList = new javax.swing.JList<>();
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

        invoiceListPanel.setLayout(new java.awt.BorderLayout());

        invoiceActionsToolbar.setRollover(true);

        newInvoiceButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/new.png"))); // NOI18N
        newInvoiceButton.setMnemonic('n');
        newInvoiceButton.setText("New");
        newInvoiceButton.setToolTipText("");
        newInvoiceButton.setFocusable(false);
        newInvoiceButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        newInvoiceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newInvoiceButtonActionPerformed(evt);
            }
        });
        invoiceActionsToolbar.add(newInvoiceButton);

        modifyInvoiceButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edit.png"))); // NOI18N
        modifyInvoiceButton.setMnemonic('e');
        modifyInvoiceButton.setText("Edit");
        modifyInvoiceButton.setEnabled(false);
        modifyInvoiceButton.setFocusable(false);
        modifyInvoiceButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        modifyInvoiceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyInvoiceButtonActionPerformed(evt);
            }
        });
        invoiceActionsToolbar.add(modifyInvoiceButton);

        invoiceListPanel.add(invoiceActionsToolbar, java.awt.BorderLayout.NORTH);

        invoiceList.setModel(listModel);
        invoiceList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        invoiceList.setCellRenderer(new ro.samlex.reelcash.ui.renderers.list.InvoiceRenderer());
        invoiceList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                invoiceListValueChanged(evt);
            }
        });
        invoiceListScrollPane.setViewportView(invoiceList);

        invoiceListPanel.add(invoiceListScrollPane, java.awt.BorderLayout.CENTER);

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
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
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

    private void showInvoiceList() {
        switchCard("list");
    }

    private void saveInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveInvoiceButtonActionPerformed
        try {
            Invoice invoice = invoicePanel.getModel();
            try (OutputStream os = new InvoiceStreamFactory(invoice.getUuid().toString()).createOutputStream()) {
                invoice.save(os);
                put(invoice);
                showInvoiceList();
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
        showInvoice();
    }//GEN-LAST:event_addInvoiceButtonActionPerformed

    private void newInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newInvoiceButtonActionPerformed
        showInvoice();
    }//GEN-LAST:event_newInvoiceButtonActionPerformed

    private void showInvoice() {
        switchCard("invoice");
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        invoicePanel.clearData();
        showInvoiceList();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void invoiceListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_invoiceListValueChanged
        JList list = (JList) evt.getSource();
        int selectedIndex = list.getSelectedIndex();
        modifyInvoiceButton.setEnabled(selectedIndex >= 0);
    }//GEN-LAST:event_invoiceListValueChanged

    private void modifyInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyInvoiceButtonActionPerformed
        showInvoice();
        invoicePanel.setModel((Invoice)listModel.elementAt(invoiceList.getSelectedIndex()));
    }//GEN-LAST:event_modifyInvoiceButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addInvoiceButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel instructionsPanel;
    private javax.swing.JTextArea instructionsTextArea;
    private javax.swing.JPanel invoiceActionsPanel;
    private javax.swing.JToolBar invoiceActionsToolbar;
    private javax.swing.JList<String> invoiceList;
    private javax.swing.JPanel invoiceListPanel;
    private javax.swing.JScrollPane invoiceListScrollPane;
    private ro.samlex.reelcash.ui.components.JInvoicePanel invoicePanel;
    private javax.swing.JButton modifyInvoiceButton;
    private javax.swing.JPanel modifyInvoicePanel;
    private javax.swing.JButton newInvoiceButton;
    private javax.swing.JButton saveInvoiceButton;
    // End of variables declaration//GEN-END:variables
}
