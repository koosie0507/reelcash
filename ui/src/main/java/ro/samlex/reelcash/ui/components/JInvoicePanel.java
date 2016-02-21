package ro.samlex.reelcash.ui.components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import jdk.nashorn.internal.objects.annotations.Getter;
import ro.samlex.reelcash.data.InvoiceItem;
import ro.samlex.reelcash.ui.validation.ValidationErrorCollector;
import ro.samlex.reelcash.viewmodels.InvoiceViewModel;

public class JInvoicePanel extends javax.swing.JPanel {

    private final PropertyChangeListener modelChangeListener;

    public JInvoicePanel() {
        this.modelChangeListener = new ModelChangeListener();
        initComponents();
    }

    @Getter
    public InvoiceViewModel getDataContext() {
        return this.dataContext;
    }
    
    public ValidationErrorCollector getValidationErrorCollector() {
        return this.invoicedContactPanel.getValidationErrorCollector();
    }
    
    public void forceValidation (){ 
        this.invoicedContactPanel.forceValidation();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        dataContext = new ro.samlex.reelcash.viewmodels.InvoiceViewModel();
        headerPanel = new javax.swing.JPanel();
        lblNumber = new javax.swing.JLabel();
        numberSpinner = new javax.swing.JSpinner();
        lblDate = new javax.swing.JLabel();
        dateSpinner = new javax.swing.JSpinner();
        invoicedContactPanel = new ro.samlex.reelcash.ui.components.JContactPanel();
        invoicedItemsPanel = new javax.swing.JPanel();
        invoicedItemsActions = new javax.swing.JToolBar();
        addItemButton = new javax.swing.JButton();
        removeItemButton = new javax.swing.JButton();
        invoicedItemsTableScroller = new javax.swing.JScrollPane();
        invoiceDetailsTable = new javax.swing.JTable();

        this.dataContext.addPropertyChangeListener(this.modelChangeListener);

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

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

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dataContext, org.jdesktop.beansbinding.ELProperty.create("${model.number}"), numberSpinner, org.jdesktop.beansbinding.BeanProperty.create("value"), "numberBinding");
        bindingGroup.addBinding(binding);

        headerPanel.add(numberSpinner);

        lblDate.setText("/");
        headerPanel.add(lblDate);

        dateSpinner.setModel(new javax.swing.SpinnerDateModel());
        dateSpinner.setEditor(new javax.swing.JSpinner.DateEditor(dateSpinner, "dd-MM-yyyy"));
        dateSpinner.setMaximumSize(new java.awt.Dimension(240, 23));
        dateSpinner.setMinimumSize(new java.awt.Dimension(120, 23));
        dateSpinner.setPreferredSize(new java.awt.Dimension(150, 23));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dataContext, org.jdesktop.beansbinding.ELProperty.create("${model.date}"), dateSpinner, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        headerPanel.add(dateSpinner);

        add(headerPanel);

        invoicedContactPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(16, 8, 8, 8), "Invoiced Party Contact Information", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 0, 14))); // NOI18N
        invoicedContactPanel.getDataContext().addPropertyChangeListener(this.modelChangeListener);
        add(invoicedContactPanel);

        invoicedItemsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(16, 8, 8, 8), "Invoiced items", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 14))); // NOI18N
        invoicedItemsPanel.setLayout(new java.awt.BorderLayout());

        invoicedItemsActions.setFloatable(false);
        invoicedItemsActions.setAlignmentX(0.0F);
        invoicedItemsActions.setAlignmentY(0.5F);

        addItemButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/plus.png"))); // NOI18N
        addItemButton.setMnemonic('a');
        addItemButton.setFocusable(false);
        addItemButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        addItemButton.setMaximumSize(new java.awt.Dimension(32, 32));
        addItemButton.setMinimumSize(new java.awt.Dimension(32, 32));
        addItemButton.setPreferredSize(new java.awt.Dimension(32, 32));
        addItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addItemButtonActionPerformed(evt);
            }
        });
        invoicedItemsActions.add(addItemButton);
        addItemButton.getAccessibleContext().setAccessibleDescription("");

        removeItemButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/minus.png"))); // NOI18N
        removeItemButton.setMnemonic('r');
        removeItemButton.setFocusable(false);
        removeItemButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        removeItemButton.setMaximumSize(new java.awt.Dimension(32, 32));
        removeItemButton.setMinimumSize(new java.awt.Dimension(32, 32));
        removeItemButton.setPreferredSize(new java.awt.Dimension(32, 32));
        removeItemButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        removeItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeItemButtonActionPerformed(evt);
            }
        });
        invoicedItemsActions.add(removeItemButton);

        invoicedItemsPanel.add(invoicedItemsActions, java.awt.BorderLayout.NORTH);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${items}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dataContext, eLProperty, invoiceDetailsTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${name}"));
        columnBinding.setColumnName("Item name");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${unitPrice}"));
        columnBinding.setColumnName("Price");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create(" "));
        columnBinding.setColumnName("");
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        invoicedItemsTableScroller.setViewportView(invoiceDetailsTable);
        if (invoiceDetailsTable.getColumnModel().getColumnCount() > 0) {
            invoiceDetailsTable.getColumnModel().getColumn(0).setMinWidth(160);
            invoiceDetailsTable.getColumnModel().getColumn(0).setPreferredWidth(240);
            invoiceDetailsTable.getColumnModel().getColumn(0).setMaxWidth(400);
            invoiceDetailsTable.getColumnModel().getColumn(1).setMinWidth(50);
            invoiceDetailsTable.getColumnModel().getColumn(1).setPreferredWidth(65);
            invoiceDetailsTable.getColumnModel().getColumn(1).setMaxWidth(120);
            invoiceDetailsTable.getColumnModel().getColumn(2).setResizable(false);
        }

        invoicedItemsPanel.add(invoicedItemsTableScroller, java.awt.BorderLayout.CENTER);

        add(invoicedItemsPanel);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void addItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addItemButtonActionPerformed
        getDataContext().getItems().add(new InvoiceItem());
    }//GEN-LAST:event_addItemButtonActionPerformed

    private void removeItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeItemButtonActionPerformed
        int selectedIndex = invoiceDetailsTable.getSelectedRow();
        if (selectedIndex < 0) {
            return;
        }
        getDataContext().getItems().remove(selectedIndex);
    }//GEN-LAST:event_removeItemButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addItemButton;
    private ro.samlex.reelcash.viewmodels.InvoiceViewModel dataContext;
    private javax.swing.JSpinner dateSpinner;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JTable invoiceDetailsTable;
    private ro.samlex.reelcash.ui.components.JContactPanel invoicedContactPanel;
    private javax.swing.JToolBar invoicedItemsActions;
    private javax.swing.JPanel invoicedItemsPanel;
    private javax.swing.JScrollPane invoicedItemsTableScroller;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblNumber;
    private javax.swing.JSpinner numberSpinner;
    private javax.swing.JButton removeItemButton;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private class ModelChangeListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (!"model".equals(evt.getPropertyName())) {
                return;
            }
            if (evt.getSource() == dataContext) {
                invoicedContactPanel.getDataContext().setModel(dataContext.getModel().getRecipient());

            } else if (evt.getSource() == invoicedContactPanel.getDataContext()) {
                dataContext.getModel().setRecipient(invoicedContactPanel.getDataContext().getModel());
            }
        }
    }

}
