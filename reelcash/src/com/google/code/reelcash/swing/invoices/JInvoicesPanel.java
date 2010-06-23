package com.google.code.reelcash.swing.invoices;

import com.google.code.reelcash.ReelcashException;
import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.documents.InvoiceDetailsTableModel;
import com.google.code.reelcash.data.documents.InvoiceMediator;
import com.google.code.reelcash.util.MsgBox;
import javax.swing.DefaultListModel;

/**
 * This class of panels works as a master/detail view for invoices.
 *
 * @author andrei.olar
 */
public class JInvoicesPanel extends javax.swing.JPanel {

    private static final long serialVersionUID = 5635559986924134535L;

    /** Creates new form JInvoicesPanel */
    public JInvoicesPanel() {
        initComponents();
    }

    private void loadInvoices() {
        DefaultListModel listModel = (DefaultListModel) invoiceList.getModel();
        listModel.removeAllElements();
        for (DataRow row : InvoiceMediator.getInstance().readInvoices()) {
            listModel.addElement(row);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    masterDetailPane = new javax.swing.JSplitPane();
    masterPane = new javax.swing.JPanel();
    operationBar = new javax.swing.JToolBar();
    addButton = new javax.swing.JButton();
    delButton = new javax.swing.JButton();
    masterListPanel = new javax.swing.JPanel();
    filterField = new javax.swing.JTextField();
    filterButton = new javax.swing.JButton();
    listScroller = new javax.swing.JScrollPane();
    invoiceList = new javax.swing.JList();
    detailPane = new javax.swing.JPanel();
    invoiceNoLabel = new javax.swing.JLabel();
    invoiceNoText = new javax.swing.JLabel();
    invoiceStateText = new javax.swing.JLabel();
    invoiceDateLabel = new javax.swing.JLabel();
    invoiceDateText = new javax.swing.JLabel();
    invoiceIssueDateLabel = new javax.swing.JLabel();
    invoiceIssueDateText = new javax.swing.JLabel();
    invoiceDueDateLabel = new javax.swing.JLabel();
    invoiceDueDateText = new javax.swing.JLabel();
    detailsScroller = new javax.swing.JScrollPane();
    detailsTable = new javax.swing.JTable();

    setLayout(new java.awt.BorderLayout());

    masterDetailPane.setDividerLocation(160);
    masterDetailPane.setResizeWeight(0.33);

    masterPane.setLayout(new java.awt.BorderLayout());

    operationBar.setFloatable(false);
    operationBar.setDoubleBuffered(true);

    addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/google/code/reelcash/images/toolbar/add_small.png"))); // NOI18N
    addButton.setFocusable(false);
    addButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    addButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    addButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        onAddInvoicePerformed(evt);
      }
    });
    operationBar.add(addButton);

    delButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/google/code/reelcash/images/toolbar/delete_small.png"))); // NOI18N
    delButton.setFocusable(false);
    delButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    delButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    delButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        onDeleteInvoicePerformed(evt);
      }
    });
    operationBar.add(delButton);

    masterPane.add(operationBar, java.awt.BorderLayout.PAGE_START);

    masterListPanel.setLayout(new java.awt.GridBagLayout());

    filterField.setMaximumSize(new java.awt.Dimension(1200, 20));
    filterField.setMinimumSize(new java.awt.Dimension(75, 20));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 50;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(15, 15, 0, 5);
    masterListPanel.add(filterField, gridBagConstraints);

    filterButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/google/code/reelcash/images/toolbar/filter.png"))); // NOI18N
    filterButton.setMnemonic('f');
    filterButton.setToolTipText("");
    filterButton.setMaximumSize(new java.awt.Dimension(20, 20));
    filterButton.setMinimumSize(new java.awt.Dimension(20, 20));
    filterButton.setPreferredSize(new java.awt.Dimension(20, 20));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
    gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 5);
    masterListPanel.add(filterButton, gridBagConstraints);

    invoiceList.setModel(new DefaultListModel());
    invoiceList.setCellRenderer(new DataRowListCellRenderer(InvoiceResources.getString("invoice_format"), 2,3,7,9));
    invoiceList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
      public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
        onSelectionChanged(evt);
      }
    });
    invoiceList.addAncestorListener(new javax.swing.event.AncestorListener() {
      public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
      }
      public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
        onListAddedOnAncestor(evt);
      }
      public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
      }
    });
    listScroller.setViewportView(invoiceList);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(5, 15, 15, 5);
    masterListPanel.add(listScroller, gridBagConstraints);

    masterPane.add(masterListPanel, java.awt.BorderLayout.CENTER);

    masterDetailPane.setLeftComponent(masterPane);

    detailPane.setLayout(new java.awt.GridBagLayout());

    invoiceNoLabel.setFont(invoiceNoLabel.getFont().deriveFont((invoiceNoLabel.getFont().getStyle() | java.awt.Font.ITALIC), 14));
    invoiceNoLabel.setForeground(javax.swing.UIManager.getDefaults().getColor("Label.disabledForeground"));
    invoiceNoLabel.setLabelFor(invoiceNoText);
    invoiceNoLabel.setText(InvoiceResources.getString("invoiceNoLabel_text")); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
    gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 0);
    detailPane.add(invoiceNoLabel, gridBagConstraints);

    invoiceNoText.setFont(invoiceNoText.getFont().deriveFont(invoiceNoText.getFont().getStyle() | java.awt.Font.BOLD, 14));
    invoiceNoText.setForeground(javax.swing.UIManager.getDefaults().getColor("textHighlight"));
    invoiceNoText.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 3;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(0, 4, 10, 0);
    detailPane.add(invoiceNoText, gridBagConstraints);

    invoiceStateText.setFont(invoiceStateText.getFont().deriveFont(invoiceStateText.getFont().getStyle() | java.awt.Font.BOLD, 14));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 5;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
    gridBagConstraints.weightx = 0.1;
    gridBagConstraints.insets = new java.awt.Insets(0, 4, 10, 15);
    detailPane.add(invoiceStateText, gridBagConstraints);

    invoiceDateLabel.setFont(invoiceDateLabel.getFont().deriveFont((invoiceDateLabel.getFont().getStyle() | java.awt.Font.ITALIC), 14));
    invoiceDateLabel.setForeground(javax.swing.UIManager.getDefaults().getColor("Label.disabledForeground"));
    invoiceDateLabel.setLabelFor(invoiceDateText);
    invoiceDateLabel.setText(InvoiceResources.getString("invoiceDateLabel_text")); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
    gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
    detailPane.add(invoiceDateLabel, gridBagConstraints);

    invoiceDateText.setFont(invoiceDateText.getFont().deriveFont((float)14));
    invoiceDateText.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
    gridBagConstraints.weightx = 0.33;
    gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
    detailPane.add(invoiceDateText, gridBagConstraints);

    invoiceIssueDateLabel.setFont(invoiceIssueDateLabel.getFont().deriveFont((invoiceIssueDateLabel.getFont().getStyle() | java.awt.Font.ITALIC), 14));
    invoiceIssueDateLabel.setForeground(javax.swing.UIManager.getDefaults().getColor("Label.disabledForeground"));
    invoiceIssueDateLabel.setLabelFor(invoiceIssueDateText);
    invoiceIssueDateLabel.setText(InvoiceResources.getString("invoiceIssueDateLabel_text")); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
    detailPane.add(invoiceIssueDateLabel, gridBagConstraints);

    invoiceIssueDateText.setFont(invoiceIssueDateText.getFont().deriveFont((float)14));
    invoiceIssueDateText.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
    gridBagConstraints.weightx = 0.33;
    gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
    detailPane.add(invoiceIssueDateText, gridBagConstraints);

    invoiceDueDateLabel.setFont(invoiceDueDateLabel.getFont().deriveFont((invoiceDueDateLabel.getFont().getStyle() | java.awt.Font.ITALIC), 14));
    invoiceDueDateLabel.setForeground(javax.swing.UIManager.getDefaults().getColor("Label.disabledForeground"));
    invoiceDueDateLabel.setLabelFor(invoiceDueDateText);
    invoiceDueDateLabel.setText(InvoiceResources.getString("invoiceDueDateLabel_text")); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 4;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
    detailPane.add(invoiceDueDateLabel, gridBagConstraints);

    invoiceDueDateText.setFont(invoiceDueDateText.getFont().deriveFont((float)14));
    invoiceDueDateText.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 5;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
    gridBagConstraints.weightx = 0.33;
    gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 15);
    detailPane.add(invoiceDueDateText, gridBagConstraints);

    detailsTable.setModel(new InvoiceDetailsTableModel());
    detailsTable.setToolTipText("");
    detailsScroller.setViewportView(detailsTable);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridwidth = 6;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(5, 5, 15, 15);
    detailPane.add(detailsScroller, gridBagConstraints);

    masterDetailPane.setRightComponent(detailPane);

    add(masterDetailPane, java.awt.BorderLayout.CENTER);
  }// </editor-fold>//GEN-END:initComponents

    private void onListAddedOnAncestor(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_onListAddedOnAncestor
        loadInvoices();
    }//GEN-LAST:event_onListAddedOnAncestor

    private void onAddInvoicePerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onAddInvoicePerformed
        JInvoiceWizard wizard = new JInvoiceWizard();
        wizard.pack();
        wizard.setModal(true);
        wizard.setVisible(true);
        loadInvoices();
    }//GEN-LAST:event_onAddInvoicePerformed

    private void onSelectionChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_onSelectionChanged
        DataRow row = (DataRow) invoiceList.getModel().getElementAt(invoiceList.getSelectedIndex());
        Integer invoiceId = (Integer) row.getValue(0);
        DataRow invoiceInfo = InvoiceMediator.getInstance().getInvoiceInformation(invoiceId);
        invoiceNoText.setText(invoiceInfo.getValue(0).toString());
        invoiceStateText.setText(invoiceInfo.getValue(5).toString());
        invoiceDateText.setText(invoiceInfo.getValue(6).toString());
        invoiceIssueDateText.setText(invoiceInfo.getValue(1).toString());
        invoiceDueDateText.setText(invoiceInfo.getValue(2).toString());
    }//GEN-LAST:event_onSelectionChanged

    private void onDeleteInvoicePerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onDeleteInvoicePerformed
        int selIdx = invoiceList.getSelectedIndex();
        if (0 > selIdx)
            return;
        Integer invoiceId = (Integer) ((DataRow) invoiceList.getModel().getElementAt(selIdx)).getValue(0);
        try {
            InvoiceMediator.getInstance().deleteInvoice(invoiceId);
            ((DefaultListModel)invoiceList.getModel()).remove(selIdx);
        }
        catch (ReelcashException e) {
            MsgBox.error(e.getLocalizedMessage());
        }
    }//GEN-LAST:event_onDeleteInvoicePerformed
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton addButton;
  private javax.swing.JButton delButton;
  private javax.swing.JPanel detailPane;
  private javax.swing.JScrollPane detailsScroller;
  private javax.swing.JTable detailsTable;
  private javax.swing.JButton filterButton;
  private javax.swing.JTextField filterField;
  private javax.swing.JLabel invoiceDateLabel;
  private javax.swing.JLabel invoiceDateText;
  private javax.swing.JLabel invoiceDueDateLabel;
  private javax.swing.JLabel invoiceDueDateText;
  private javax.swing.JLabel invoiceIssueDateLabel;
  private javax.swing.JLabel invoiceIssueDateText;
  private javax.swing.JList invoiceList;
  private javax.swing.JLabel invoiceNoLabel;
  private javax.swing.JLabel invoiceNoText;
  private javax.swing.JLabel invoiceStateText;
  private javax.swing.JScrollPane listScroller;
  private javax.swing.JSplitPane masterDetailPane;
  private javax.swing.JPanel masterListPanel;
  private javax.swing.JPanel masterPane;
  private javax.swing.JToolBar operationBar;
  // End of variables declaration//GEN-END:variables
}
