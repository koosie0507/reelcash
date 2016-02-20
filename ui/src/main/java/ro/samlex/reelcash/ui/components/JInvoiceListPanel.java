package ro.samlex.reelcash.ui.components;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import ro.samlex.reelcash.Application;
import ro.samlex.reelcash.Reelcash;
import ro.samlex.reelcash.SysUtils;
import ro.samlex.reelcash.data.Invoice;
import ro.samlex.reelcash.data.Party;
import ro.samlex.reelcash.io.FileOutputSink;
import ro.samlex.reelcash.io.InvoiceDataFolderSource;
import ro.samlex.reelcash.ui.ApplicationMessages;
import ro.samlex.reelcash.viewmodels.InvoiceViewModel;

public class JInvoiceListPanel extends javax.swing.JPanel {

    private static final Path INVOICE_FOLDER_PATH = FileSystems.getDefault().getPath(
            SysUtils.getDbFolderPath(),
            Reelcash.INVOICES_DATA_FOLDER_NAME);
    private boolean isInvoiceEditModeActive;

    public JInvoiceListPanel() {
        isInvoiceEditModeActive = false;
        initComponents();
        showInvoiceList();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        dataContext = new ro.samlex.reelcash.viewmodels.InvoiceListViewModel();
        invoicePanel = new ro.samlex.reelcash.ui.components.JInvoicePanel();
        actionsToolbar = new javax.swing.JToolBar();
        newInvoiceButton = new javax.swing.JButton();
        modifyInvoiceButton = new javax.swing.JButton();
        modifyInvoiceButton.setVisible(false);
        saveButton = new javax.swing.JButton();
        printButton = new javax.swing.JButton();
        hideInvoiceButton = new javax.swing.JButton();
        invoicesScrollPane = new javax.swing.JScrollPane();
        invoicesList = new javax.swing.JList<>();

        setLayout(new java.awt.BorderLayout());

        actionsToolbar.setRollover(true);

        newInvoiceButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/new.png"))); // NOI18N
        newInvoiceButton.setMnemonic('n');
        newInvoiceButton.setText("New");
        newInvoiceButton.setToolTipText("");
        newInvoiceButton.setFocusable(false);
        newInvoiceButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        newInvoiceButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        newInvoiceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newInvoiceButtonActionPerformed(evt);
            }
        });
        actionsToolbar.add(newInvoiceButton);

        modifyInvoiceButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edit.png"))); // NOI18N
        modifyInvoiceButton.setMnemonic('e');
        modifyInvoiceButton.setText("Edit");
        modifyInvoiceButton.setFocusable(false);
        modifyInvoiceButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        modifyInvoiceButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        modifyInvoiceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyInvoiceButtonActionPerformed(evt);
            }
        });
        actionsToolbar.add(modifyInvoiceButton);

        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/save.png"))); // NOI18N
        saveButton.setMnemonic('s');
        saveButton.setText("Save");
        saveButton.setToolTipText("");
        saveButton.setFocusable(false);
        saveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        actionsToolbar.add(saveButton);

        printButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/print.png"))); // NOI18N
        printButton.setMnemonic('p');
        printButton.setText("Print");
        printButton.setToolTipText("");
        printButton.setFocusable(false);
        printButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        printButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        printButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printButtonActionPerformed(evt);
            }
        });
        actionsToolbar.add(printButton);

        hideInvoiceButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/close.png"))); // NOI18N
        hideInvoiceButton.setMnemonic('c');
        hideInvoiceButton.setText("Close");
        hideInvoiceButton.setFocusable(false);
        hideInvoiceButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        hideInvoiceButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        hideInvoiceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hideInvoiceButtonActionPerformed(evt);
            }
        });
        actionsToolbar.add(hideInvoiceButton);

        add(actionsToolbar, java.awt.BorderLayout.PAGE_START);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${items}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dataContext, eLProperty, invoicesList);
        jListBinding.setDetailBinding(org.jdesktop.beansbinding.ELProperty.create("${number} / ${date}"));
        bindingGroup.addBinding(jListBinding);
        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dataContext, org.jdesktop.beansbinding.ELProperty.create("${selectedItem}"), invoicesList, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        invoicesList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                invoicesListValueChanged(evt);
            }
        });
        invoicesScrollPane.setViewportView(invoicesList);

        add(invoicesScrollPane, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void adjustComponents() {
        saveButton.setVisible(isInvoiceEditModeActive);
        printButton.setVisible(isInvoiceEditModeActive);
        hideInvoiceButton.setVisible(isInvoiceEditModeActive);
        modifyInvoiceButton.setVisible(!isInvoiceEditModeActive && invoicesList.getSelectedIndex() >= 0);
        invoicePanel.setVisible(isInvoiceEditModeActive);
        invoicesScrollPane.setVisible(!isInvoiceEditModeActive);
        remove(isInvoiceEditModeActive ? invoicesScrollPane : invoicePanel);
        add(isInvoiceEditModeActive ? invoicePanel : invoicesScrollPane, BorderLayout.CENTER);
    }

    private void showInvoiceDetails() {
        isInvoiceEditModeActive = true;
        adjustComponents();
    }

    private void showInvoiceList() {
        isInvoiceEditModeActive = false;
        adjustComponents();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dataContext.loadAll(
                        new InvoiceDataFolderSource(INVOICE_FOLDER_PATH));
            }

        });
    }

    private void newInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newInvoiceButtonActionPerformed
        Invoice newInvoice = new Invoice();
        newInvoice.setEmitter(Application.getInstance().getCompany());
        newInvoice.setRecipient(new Party());
        invoicePanel.getDataContext().setModel(newInvoice);
        if (!isInvoiceEditModeActive) {
            showInvoiceDetails();
        }
    }//GEN-LAST:event_newInvoiceButtonActionPerformed

    private void modifyInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyInvoiceButtonActionPerformed
        invoicePanel.getDataContext().setModel(dataContext.getSelectedItem());
        if (!isInvoiceEditModeActive) {
            showInvoiceDetails();
        }
    }//GEN-LAST:event_modifyInvoiceButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        try {
            if (!Files.exists(INVOICE_FOLDER_PATH)) {
                Files.createDirectories(INVOICE_FOLDER_PATH);
            }
            final InvoiceViewModel context = invoicePanel.getDataContext();
            Path filePath = FileSystems.getDefault().getPath(
                    INVOICE_FOLDER_PATH.toString(), context.getModel().getUuid() + ".json"
            );
            context.save(new FileOutputSink(filePath));
        } catch (IOException ex) {
            ApplicationMessages.showError(this, ex.getMessage());
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void invoicesListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_invoicesListValueChanged
        if (isInvoiceEditModeActive) {
            return;
        }
        if (invoicesList.getSelectedIndex() >= 0) {
            modifyInvoiceButton.setVisible(true);
        }
    }//GEN-LAST:event_invoicesListValueChanged

    private void printButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printButtonActionPerformed
        JDialog printDialog = new JDialog((Dialog) null, "Preview Invoice", Dialog.ModalityType.APPLICATION_MODAL);
        JReportPanel reportPanel = new JReportPanel();
        reportPanel.loadReport(invoicePanel.getDataContext().getModel());
        printDialog.add(reportPanel);
        printDialog.pack();
        printDialog.setLocationRelativeTo(null);
        printDialog.setVisible(true);
    }//GEN-LAST:event_printButtonActionPerformed

    private void hideInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hideInvoiceButtonActionPerformed
        showInvoiceList();
    }//GEN-LAST:event_hideInvoiceButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar actionsToolbar;
    private ro.samlex.reelcash.viewmodels.InvoiceListViewModel dataContext;
    private javax.swing.JButton hideInvoiceButton;
    private ro.samlex.reelcash.ui.components.JInvoicePanel invoicePanel;
    private javax.swing.JList<String> invoicesList;
    private javax.swing.JScrollPane invoicesScrollPane;
    private javax.swing.JButton modifyInvoiceButton;
    private javax.swing.JButton newInvoiceButton;
    private javax.swing.JButton printButton;
    private javax.swing.JButton saveButton;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
