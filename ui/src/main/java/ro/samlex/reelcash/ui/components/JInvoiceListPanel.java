package ro.samlex.reelcash.ui.components;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
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
    private final ComponentListener componentSwitcher;

    public JInvoiceListPanel() {
        this.componentSwitcher = new ComponentSwitcher();
        initComponents();
        showInvoiceList();
        invoicesScrollPane.addComponentListener(componentSwitcher);
        invoicePanel.addComponentListener(componentSwitcher);
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

    private void showInvoiceDetails() {
        remove(invoicesScrollPane);
        invoicePanel.setVisible(true);
        saveButton.setVisible(true);
        modifyInvoiceButton.setVisible(false);
        add(invoicePanel, java.awt.BorderLayout.CENTER);
    }

    private void showInvoiceList() {
        remove(invoicePanel);
        invoicesScrollPane.setVisible(true);
        saveButton.setVisible(false);
        add(invoicesScrollPane, java.awt.BorderLayout.CENTER);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dataContext.loadAll(
                        new InvoiceDataFolderSource(INVOICE_FOLDER_PATH));
            }

        });
    }

    private void newInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newInvoiceButtonActionPerformed
        if (invoicesScrollPane.isVisible()) {
            Invoice newInvoice = new Invoice();
            newInvoice.setEmitter(Application.getInstance().getCompany());
            newInvoice.setRecipient(new Party());
            invoicePanel.getDataContext().setModel(newInvoice);
            invoicesScrollPane.setVisible(false);
        }
    }//GEN-LAST:event_newInvoiceButtonActionPerformed

    private void modifyInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyInvoiceButtonActionPerformed
        if (invoicesScrollPane.isVisible()) {
            invoicePanel.getDataContext().setModel(dataContext.getSelectedItem());
            invoicesScrollPane.setVisible(false);
        }
    }//GEN-LAST:event_modifyInvoiceButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        if (invoicePanel.isVisible()) {
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
            } finally {
                invoicePanel.setVisible(false);
            }
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void invoicesListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_invoicesListValueChanged
        if(invoicesList.getSelectedIndex() >=0 && invoicesScrollPane.isVisible()){
            modifyInvoiceButton.setVisible(true);
        }
    }//GEN-LAST:event_invoicesListValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar actionsToolbar;
    private ro.samlex.reelcash.viewmodels.InvoiceListViewModel dataContext;
    private ro.samlex.reelcash.ui.components.JInvoicePanel invoicePanel;
    private javax.swing.JList<String> invoicesList;
    private javax.swing.JScrollPane invoicesScrollPane;
    private javax.swing.JButton modifyInvoiceButton;
    private javax.swing.JButton newInvoiceButton;
    private javax.swing.JButton saveButton;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private final class ComponentSwitcher extends ComponentAdapter {

        @Override
        public void componentHidden(ComponentEvent e) {
            if (e.getComponent() == invoicePanel) {
                showInvoiceList();
                return;
            }
            showInvoiceDetails();
        }

    }
}
