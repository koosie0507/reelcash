package ro.samlex.reelcash.ui;

import java.awt.CardLayout;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.observablecollections.ObservableListListener;
import ro.samlex.reelcash.Application;
import ro.samlex.reelcash.Reelcash;
import ro.samlex.reelcash.SysUtils;
import ro.samlex.reelcash.data.Invoice;
import ro.samlex.reelcash.data.Party;
import ro.samlex.reelcash.io.FileOutputSink;
import ro.samlex.reelcash.io.InvoiceDataFolderSource;

public class MainWindow extends javax.swing.JFrame {

    private final InvoiceDataFolderSource invoiceFolderSource;
    private final Path invoiceFolderPath;

    public MainWindow() {
        invoiceFolderPath = FileSystems.getDefault().getPath(
                SysUtils.getDbFolderPath(),
                Reelcash.INVOICES_DATA_FOLDER_NAME);
        this.invoiceFolderSource = new InvoiceDataFolderSource(invoiceFolderPath);
        initComponents();
        this.dataContext.getItems().addObservableListListener(new InvoiceListChangeListener());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dataContext.getLoadInvoicesCommand().execute();
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        dataContext = new ro.samlex.reelcash.viewmodels.InvoiceListViewModel(this.invoiceFolderSource);
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

        invoiceList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${items}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dataContext, eLProperty, invoiceList);
        jListBinding.setDetailBinding(org.jdesktop.beansbinding.ELProperty.create("${number} / ${date} -> [${recipient.name}]"));
        bindingGroup.addBinding(jListBinding);
        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dataContext, org.jdesktop.beansbinding.ELProperty.create("${selectedItem}"), invoiceList, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

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

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void switchCard(String name) {
        final CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
        cardLayout.show(getContentPane(), name);
    }

    private void showInvoiceList() {
        switchCard("list");
    }

    private void showInvoice() {
        switchCard("invoice");
    }

    private void saveInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveInvoiceButtonActionPerformed
        try {
            if (!Files.exists(invoiceFolderPath)) {
                Files.createDirectories(invoiceFolderPath);
            }
            Path invoiceFilePath = FileSystems.getDefault().getPath(
                    invoiceFolderPath.toString(), invoicePanel.getDataContext().getModel().getUuid() + ".json");

            invoicePanel.getDataContext().save(new FileOutputSink(invoiceFilePath));
            put(invoicePanel.getDataContext().getModel());
            showInvoiceList();

        } catch (IOException e) {
            ApplicationMessages.showError(this, "Couldn't save invoice: " + e.getMessage());
        } catch (NullPointerException e) {
            ApplicationMessages.showError(this, "Missing information: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            ApplicationMessages.showError(this, "Invalid information: " + e.getMessage());
        }
    }//GEN-LAST:event_saveInvoiceButtonActionPerformed

    private void put(Invoice invoice) {
        int index = dataContext.getItems().indexOf(invoice);
        if (index < 0) {
            dataContext.getItems().add(invoice);
        } else {
            dataContext.getItems().set(index, invoice);
        }
    }

    private Invoice newEmptyInvoice() {
        Invoice result = new Invoice();
        result.setEmitter(Application.getInstance().getCompany());
        result.setRecipient(new Party());
        return result;
    }
    
    private void addInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addInvoiceButtonActionPerformed
        invoicePanel.getDataContext().setModel(newEmptyInvoice());
        showInvoice();
    }//GEN-LAST:event_addInvoiceButtonActionPerformed

    private void newInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newInvoiceButtonActionPerformed
        invoicePanel.getDataContext().setModel(newEmptyInvoice());
        showInvoice();
    }//GEN-LAST:event_newInvoiceButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        showInvoiceList();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void invoiceListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_invoiceListValueChanged
        JList list = (JList) evt.getSource();
        int selectedIndex = list.getSelectedIndex();
        modifyInvoiceButton.setEnabled(selectedIndex >= 0);
    }//GEN-LAST:event_invoiceListValueChanged

    private void modifyInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyInvoiceButtonActionPerformed
        invoicePanel.getDataContext().setModel(dataContext.getSelectedItem());
        showInvoice();
    }//GEN-LAST:event_modifyInvoiceButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addInvoiceButton;
    private javax.swing.JButton cancelButton;
    private ro.samlex.reelcash.viewmodels.InvoiceListViewModel dataContext;
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
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private final class InvoiceListChangeListener implements ObservableListListener {

        @Override
        public void listElementsAdded(ObservableList ol, int i, int i1) {
            showInvoiceList();
        }

        @Override
        public void listElementsRemoved(ObservableList ol, int i, List list) {
            if (ol.isEmpty()) {
                switchCard("welcome");
            }
        }

        @Override
        public void listElementReplaced(ObservableList ol, int i, Object o) {
        }

        @Override
        public void listElementPropertyChanged(ObservableList ol, int i) {
        }
    }
}
