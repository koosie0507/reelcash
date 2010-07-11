/*
 * JInvoiceDetailsPanel.java
 *
 * Created on May 2, 2010, 1:25:40 PM
 */
package com.google.code.reelcash.swing.invoices;

import com.google.code.reelcash.ReelcashException;
import com.google.code.reelcash.data.DataOperationMode;
import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.documents.DocumentState;
import com.google.code.reelcash.data.documents.InvoiceDetailNode;
import com.google.code.reelcash.data.documents.InvoiceMediator;
import com.google.code.reelcash.data.goods.GoodNode;
import com.google.code.reelcash.data.goods.UnitNode;
import com.google.code.reelcash.model.DataRowComboModel;
import com.google.code.reelcash.model.DataRowTableModel;
import com.google.code.reelcash.swing.ComboListCellRenderer;
import com.google.code.reelcash.swing.ReferenceFieldCellRenderer;
import com.google.code.reelcash.swing.SwingUtils;
import com.google.code.reelcash.util.MsgBox;
import java.awt.CardLayout;
import java.math.BigDecimal;
import java.sql.SQLException;
import javax.swing.ComboBoxModel;
import javax.swing.JFrame;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 *
 * @author cusi
 */
public class JInvoiceDetailsPanel extends javax.swing.JPanel {

    private static final long serialVersionUID = -107018603820150708L;
    private Integer invoiceId;
    private DataRowComboModel goodsModel;
    private DataRowComboModel unitsModel;
    private final ComboListCellRenderer renderer;
    private DataRowTableModel detailsModel;
    private ReferenceFieldCellRenderer goodCellRenderer;
    private ReferenceFieldCellRenderer unitCellRenderer;
    private DataOperationMode operationMode;

    /** Creates new form JInvoiceDetailsPanel */
    public JInvoiceDetailsPanel() {
        renderer = new ComboListCellRenderer(1);
        initComponents();
        initDetailsTable();
        addAncestorListener(new SelfAncestorListener());
        unitsCombo.setRenderer(renderer);
        goodsCombo.setRenderer(renderer);
    }

    public static void main(String[] args) {
        JFrame test = new JFrame();
        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JInvoiceDetailsPanel panel = new JInvoiceDetailsPanel();
        panel.setInvoiceId(2);
        test.add(panel);
        test.pack();
        test.setVisible(true);
    }

    private void putMasterInfo() {
        DataRow row = InvoiceMediator.getInstance().getInvoiceInformation(invoiceId);
        numberText.setText(row.getValue(0).toString());
        dateIssuedText.setText(row.getValue(1).toString());
        dateDueText.setText(row.getValue(2).toString());
        issuerText.setText(row.getValue(3).toString());
        recipientText.setText(row.getValue(4).toString());
    }

    private void clearDetailPanelData() {
        goodsCombo.setSelectedIndex(-1);
        detailDescField.setText("");
        unitsCombo.setSelectedIndex(-1);
        quantityField.setText("");
        unitPriceField.setText("");
    }

    private void initDetailsTable() {
        if (null == detailsTable) {
            return;
        }

        SwingUtils.setTableColumnVisible(detailsTable, "id", false, "", 0);
        SwingUtils.setTableColumnVisible(detailsTable, "invoice_id", false, "", 0);

        SwingUtils.setTableColumnDecimalFormat(detailsTable, "unit_price", 11, 2);
        SwingUtils.setTableColumnDecimalFormat(detailsTable, "tax_amount", 11, 2);
        SwingUtils.setTableColumnDecimalFormat(detailsTable, "excise_amount", 11, 2);
        SwingUtils.setTableColumnDecimalFormat(detailsTable, "amount", 11, 2);
        SwingUtils.setTableColumnDecimalFormat(detailsTable, "vat_amount", 11, 2);
        SwingUtils.setTableColumnDecimalFormat(detailsTable, "price", 11, 2);
        SwingUtils.setTableColumnPercentFormat(detailsTable, "vat_percent", 2, 1);

        detailsTable.getColumn("good_id").setCellRenderer(getGoodCellRenderer());
        detailsTable.getColumn("unit_id").setCellRenderer(getUnitCellRenderer());
    }

    private int indexOfId(DataRowComboModel model, String idColName, Integer id) {
        int idx = model.getSize() - 1;
        while (idx > -1) {
            DataRow row = (DataRow) model.getElementAt(idx);
            if (id.equals(row.getValue(idColName))) {
                break;
            }
            idx--;
        }
        return idx;
    }

    private void loadDetails() {
        detailsModel.clearQuietly();
        for (DataRow row : InvoiceMediator.getInstance().readInvoiceDetails(invoiceId)) {
            detailsModel.add(row);
        }
        operationMode = DataOperationMode.READ;
    }

    private void loadGoods() {
        try {
            ((DataRowComboModel) getGoodsModel()).fill(
                    InvoiceMediator.getInstance().fetchAll(GoodNode.getInstance()));
            if (1 > ((DataRowComboModel) getGoodsModel()).getSize()) {
                goodsCombo.setEnabled(false);
            }
        } catch (SQLException e) {
            MsgBox.exception(e);
        }
    }

    private void loadUnits() {
        try {
            ((DataRowComboModel) getUnitsModel()).fill(InvoiceMediator.getInstance().fetchAll(UnitNode.getInstance()));
            if (1 > ((DataRowComboModel) getUnitsModel()).getSize()) {
                throw new ReelcashException();
            }
        } catch (SQLException e) {
            MsgBox.exception(e);
        }
    }

    public TableCellRenderer getGoodCellRenderer() {
        if (null == goodCellRenderer) {
            goodCellRenderer = new ReferenceFieldCellRenderer(0, 1, InvoiceMediator.getInstance().getInvoicedGoods(invoiceId));
        }
        return goodCellRenderer;
    }

    public TableCellRenderer getUnitCellRenderer() {
        if (null == unitCellRenderer) {
            unitCellRenderer = new ReferenceFieldCellRenderer(0, 1, InvoiceMediator.getInstance().getInvoicedUnits(invoiceId));
        }
        return unitCellRenderer;
    }

    public TableModel getDetailsModel() {
        if (null == detailsModel) {
            detailsModel = new DataRowTableModel(InvoiceDetailNode.getInstance()) {

                private static final long serialVersionUID = 2279397616444341919L;

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            for (DataRow row : InvoiceMediator.getInstance().readInvoiceDetails(invoiceId)) {
                detailsModel.add(row);
            }
        }

        return detailsModel;
    }

    public ComboBoxModel getGoodsModel() {
        if (null == goodsModel) {
            goodsModel = new DataRowComboModel();
        }
        return goodsModel;
    }

    public ComboBoxModel getUnitsModel() {
        if (null == unitsModel) {
            unitsModel = new DataRowComboModel();
        }
        return unitsModel;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer value) {
        invoiceId = value;
        putMasterInfo();
        loadDetails();
        DocumentState state = InvoiceMediator.getInstance().getState(invoiceId);
        switch (state) {
            case ISSUED:
            case RECEIVED:
                createDetailButton.setEnabled(false);
                editDetailButton.setEnabled(false);
                deleteDetailButton.setEnabled(false);
                break;
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

        masterInfo = new javax.swing.JPanel();
        numberLabel = new javax.swing.JLabel();
        numberText = new javax.swing.JLabel();
        dateIssuedLabel = new javax.swing.JLabel();
        dateIssuedText = new javax.swing.JLabel();
        dateDueLabel = new javax.swing.JLabel();
        dateDueText = new javax.swing.JLabel();
        issuerLabel = new javax.swing.JLabel();
        issuerText = new javax.swing.JLabel();
        recipientLabel = new javax.swing.JLabel();
        recipientText = new javax.swing.JLabel();
        detailsPanel = new javax.swing.JPanel();
        detailsDisplayPanel = new javax.swing.JPanel();
        detailOperationsBar = new javax.swing.JToolBar();
        createDetailButton = new javax.swing.JButton();
        editDetailButton = new javax.swing.JButton();
        deleteDetailButton = new javax.swing.JButton();
        detailsTableScroller = new javax.swing.JScrollPane();
        detailsTable = new javax.swing.JTable();
        detailDataPanel = new javax.swing.JPanel();
        positionLabel = new javax.swing.JLabel();
        positionSpinner = new javax.swing.JSpinner();
        goodLabel = new javax.swing.JLabel();
        goodsCombo = new javax.swing.JComboBox();
        detailDescLabel = new javax.swing.JLabel();
        detailDescField = new javax.swing.JTextField();
        unitLabel = new javax.swing.JLabel();
        quantityLabel = new javax.swing.JLabel();
        unitPriceLabel = new javax.swing.JLabel();
        unitsCombo = new javax.swing.JComboBox();
        quantityField = new javax.swing.JFormattedTextField();
        unitPriceField = new javax.swing.JFormattedTextField();
        detailControlPanel = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        masterInfo.setMinimumSize(new java.awt.Dimension(0, 100));
        masterInfo.setPreferredSize(new java.awt.Dimension(400, 100));
        masterInfo.setLayout(new java.awt.GridBagLayout());

        numberLabel.setText("Invoice");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 3, 0);
        masterInfo.add(numberLabel, gridBagConstraints);

        numberText.setFont(numberText.getFont().deriveFont(numberText.getFont().getStyle() | java.awt.Font.BOLD));
        numberText.setForeground(java.awt.Color.blue);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 3, 5);
        masterInfo.add(numberText, gridBagConstraints);

        dateIssuedLabel.setText("Issued on");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 3, 0);
        masterInfo.add(dateIssuedLabel, gridBagConstraints);

        dateIssuedText.setFont(dateIssuedText.getFont().deriveFont(dateIssuedText.getFont().getStyle() | java.awt.Font.BOLD));
        dateIssuedText.setForeground(java.awt.Color.blue);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 3, 5);
        masterInfo.add(dateIssuedText, gridBagConstraints);

        dateDueLabel.setText("Due by");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 3, 0);
        masterInfo.add(dateDueLabel, gridBagConstraints);

        dateDueText.setFont(dateDueText.getFont().deriveFont(dateDueText.getFont().getStyle() | java.awt.Font.BOLD));
        dateDueText.setForeground(java.awt.Color.blue);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 3, 15);
        masterInfo.add(dateDueText, gridBagConstraints);

        issuerLabel.setText("Issuer");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 15, 3, 0);
        masterInfo.add(issuerLabel, gridBagConstraints);

        issuerText.setFont(issuerText.getFont().deriveFont((issuerText.getFont().getStyle() | java.awt.Font.ITALIC)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 3, 15);
        masterInfo.add(issuerText, gridBagConstraints);

        recipientLabel.setText("Recipient");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 15, 10, 0);
        masterInfo.add(recipientLabel, gridBagConstraints);

        recipientText.setFont(recipientText.getFont().deriveFont((recipientText.getFont().getStyle() | java.awt.Font.ITALIC)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 10, 15);
        masterInfo.add(recipientText, gridBagConstraints);

        add(masterInfo, java.awt.BorderLayout.PAGE_START);

        detailsPanel.setLayout(new java.awt.CardLayout());

        detailsDisplayPanel.setLayout(new java.awt.BorderLayout());

        detailOperationsBar.setFloatable(false);
        detailOperationsBar.setDoubleBuffered(true);

        createDetailButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/google/code/reelcash/images/toolbar/add_small.png"))); // NOI18N
        createDetailButton.setMnemonic('n');
        createDetailButton.setText("New");
        createDetailButton.setActionCommand("create detail");
        createDetailButton.setFocusable(false);
        createDetailButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        createDetailButton.setMaximumSize(new java.awt.Dimension(100, 23));
        createDetailButton.setMinimumSize(new java.awt.Dimension(75, 23));
        createDetailButton.setPreferredSize(new java.awt.Dimension(75, 23));
        createDetailButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onCreateDetailRequested(evt);
            }
        });
        detailOperationsBar.add(createDetailButton);
        createDetailButton.getAccessibleContext().setAccessibleName("create detail");

        editDetailButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/google/code/reelcash/images/toolbar/edit_small.png"))); // NOI18N
        editDetailButton.setMnemonic('e');
        editDetailButton.setText("Edit");
        editDetailButton.setActionCommand("edit");
        editDetailButton.setFocusable(false);
        editDetailButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        editDetailButton.setMaximumSize(new java.awt.Dimension(100, 23));
        editDetailButton.setMinimumSize(new java.awt.Dimension(75, 23));
        editDetailButton.setPreferredSize(new java.awt.Dimension(75, 23));
        editDetailButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onEditDetailPerformed(evt);
            }
        });
        detailOperationsBar.add(editDetailButton);
        editDetailButton.getAccessibleContext().setAccessibleName("edit");

        deleteDetailButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/google/code/reelcash/images/toolbar/delete_small.png"))); // NOI18N
        deleteDetailButton.setMnemonic('d');
        deleteDetailButton.setText("Delete");
        deleteDetailButton.setActionCommand("delete");
        deleteDetailButton.setFocusable(false);
        deleteDetailButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        deleteDetailButton.setMaximumSize(new java.awt.Dimension(100, 23));
        deleteDetailButton.setMinimumSize(new java.awt.Dimension(75, 23));
        deleteDetailButton.setPreferredSize(new java.awt.Dimension(75, 23));
        deleteDetailButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteDetailButtonActionPerformed(evt);
            }
        });
        detailOperationsBar.add(deleteDetailButton);

        detailsDisplayPanel.add(detailOperationsBar, java.awt.BorderLayout.PAGE_START);

        detailsTable.setModel(getDetailsModel());
        detailsTableScroller.setViewportView(detailsTable);

        detailsDisplayPanel.add(detailsTableScroller, java.awt.BorderLayout.CENTER);

        detailsPanel.add(detailsDisplayPanel, "disp");

        detailDataPanel.setLayout(new java.awt.GridBagLayout());

        positionLabel.setText("Position");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 5, 5);
        detailDataPanel.add(positionLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 15);
        detailDataPanel.add(positionSpinner, gridBagConstraints);

        goodLabel.setText("Good");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 5, 5);
        detailDataPanel.add(goodLabel, gridBagConstraints);

        goodsCombo.setModel(getGoodsModel());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 15);
        detailDataPanel.add(goodsCombo, gridBagConstraints);

        detailDescLabel.setText("Description");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 5, 5);
        detailDataPanel.add(detailDescLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 15);
        detailDataPanel.add(detailDescField, gridBagConstraints);

        unitLabel.setText("Unit");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 5, 5);
        detailDataPanel.add(unitLabel, gridBagConstraints);

        quantityLabel.setText("Quantity");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 5, 5);
        detailDataPanel.add(quantityLabel, gridBagConstraints);

        unitPriceLabel.setText("Unit price");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 5, 5);
        detailDataPanel.add(unitPriceLabel, gridBagConstraints);

        unitsCombo.setModel(getUnitsModel());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 15);
        detailDataPanel.add(unitsCombo, gridBagConstraints);

        quantityField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 15);
        detailDataPanel.add(quantityField, gridBagConstraints);

        unitPriceField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 15);
        detailDataPanel.add(unitPriceField, gridBagConstraints);

        detailControlPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        okButton.setMnemonic('o');
        okButton.setActionCommand("ok");
        okButton.setLabel("OK");
        okButton.setPreferredSize(new java.awt.Dimension(85, 23));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveDetailDataPerformed(evt);
            }
        });
        detailControlPanel.add(okButton);

        cancelButton.setMnemonic('c');
        cancelButton.setText("Cancel");
        cancelButton.setActionCommand("cancel");
        cancelButton.setPreferredSize(new java.awt.Dimension(85, 23));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onCancelDetailDataPerformed(evt);
            }
        });
        detailControlPanel.add(cancelButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 15, 15);
        detailDataPanel.add(detailControlPanel, gridBagConstraints);

        detailsPanel.add(detailDataPanel, "data");

        add(detailsPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void deleteDetailButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteDetailButtonActionPerformed
        int selectedDetail = detailsTable.getSelectedRow();
        if (0 > selectedDetail) {
            MsgBox.warn(InvoiceResources.getString("select_detail_row"));
            return;
        }
        operationMode = DataOperationMode.DELETE;
        DataRow detailRow = ((DataRowTableModel) getDetailsModel()).getRow(selectedDetail);
        try {
            InvoiceMediator.getInstance().delete(InvoiceDetailNode.getInstance(), detailRow);
            ((DataRowTableModel) getDetailsModel()).delete(selectedDetail);
        } catch (SQLException ex) {
            MsgBox.exception(ex);
        }
        operationMode = DataOperationMode.READ;
    }//GEN-LAST:event_deleteDetailButtonActionPerformed

    private void onCreateDetailRequested(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onCreateDetailRequested
        Integer min = InvoiceMediator.getInstance().getNextDetailPosition(invoiceId);
        ((SpinnerNumberModel) positionSpinner.getModel()).setMinimum(min);
        ((SpinnerNumberModel) positionSpinner.getModel()).setValue(min);
        ((SpinnerNumberModel) positionSpinner.getModel()).setMaximum(min);
        positionSpinner.setEnabled(true);
        clearDetailPanelData();
        operationMode = DataOperationMode.CREATE;
        ((CardLayout) detailsPanel.getLayout()).show(detailsPanel, "data");
    }//GEN-LAST:event_onCreateDetailRequested

    private void saveDetailDataPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveDetailDataPerformed
        try {
            DataRow selectedGoodsRow = (DataRow) goodsCombo.getSelectedItem();
            DataRow selectedUnitRow = (DataRow) unitsCombo.getSelectedItem();
            if (null == selectedUnitRow) {
                MsgBox.warn(InvoiceResources.getString("choose_unit_message")); // NOI18N
                return;
            }

            Integer position = Integer.valueOf(positionSpinner.getValue().toString());
            Integer goodId = (Integer) (null == selectedGoodsRow ? null : selectedGoodsRow.getValue(0));
            String desc = detailDescField.getText();
            if (1 > desc.trim().length()) {
                MsgBox.warn(InvoiceResources.getString("enter_description_message")); // NOI18N
                return;
            }
            Integer unitId = (Integer) (null == selectedUnitRow ? null : selectedUnitRow.getValue(0));
            BigDecimal qty = BigDecimal.valueOf(((Number) quantityField.getValue()).doubleValue());
            BigDecimal unitPrice = BigDecimal.valueOf(((Number) unitPriceField.getValue()).doubleValue());
            switch (operationMode) {
                case CREATE:
                    ((DataRowTableModel) getDetailsModel()).add(InvoiceMediator.getInstance().createInvoiceDetail(invoiceId, position, goodId, desc, unitId, qty, unitPrice));
                    break;
                case UPDATE:
                    DataRow detailRow = ((DataRowTableModel) getDetailsModel()).getRow(detailsTable.getSelectedRow());
                    InvoiceMediator.getInstance().updateInvoiceDetail(detailRow, qty, unitPrice);
                    break;
            }

            ((CardLayout) detailsPanel.getLayout()).show(detailsPanel, "disp");
            operationMode = DataOperationMode.READ;
        } catch (Throwable t) {
            MsgBox.exception(t);
        }
    }//GEN-LAST:event_saveDetailDataPerformed

    private void onCancelDetailDataPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onCancelDetailDataPerformed
        ((CardLayout) detailsPanel.getLayout()).show(detailsPanel, "disp");
    }//GEN-LAST:event_onCancelDetailDataPerformed

    private void onEditDetailPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onEditDetailPerformed
        int selectedDetail = detailsTable.getSelectedRow();
        if (0 > selectedDetail) {
            MsgBox.warn(InvoiceResources.getString("select_detail_row"));
            return;
        }

        DataRow detailRow = ((DataRowTableModel) getDetailsModel()).getRow(selectedDetail);
        ((SpinnerNumberModel) positionSpinner.getModel()).setMinimum((Comparable) detailRow.getValue("position"));
        ((SpinnerNumberModel) positionSpinner.getModel()).setValue((Comparable) detailRow.getValue("position"));
        ((SpinnerNumberModel) positionSpinner.getModel()).setMaximum((Comparable) detailRow.getValue("position"));
        goodsCombo.setSelectedIndex(indexOfId((DataRowComboModel) goodsCombo.getModel(),
                "id", (Integer) detailRow.getValue("good_id")));
        detailDescField.setText((String) detailRow.getValue("detail_text"));
        quantityField.setValue(detailRow.getValue("quantity"));
        unitPriceField.setValue(detailRow.getValue("unit_price"));
        unitsCombo.setSelectedIndex(indexOfId((DataRowComboModel) unitsCombo.getModel(),
                "id", (Integer) detailRow.getValue("unit_id")));
        operationMode = DataOperationMode.UPDATE;
        ((CardLayout) detailsPanel.getLayout()).show(detailsPanel, "data");
    }//GEN-LAST:event_onEditDetailPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton createDetailButton;
    private javax.swing.JLabel dateDueLabel;
    private javax.swing.JLabel dateDueText;
    private javax.swing.JLabel dateIssuedLabel;
    private javax.swing.JLabel dateIssuedText;
    private javax.swing.JButton deleteDetailButton;
    private javax.swing.JPanel detailControlPanel;
    private javax.swing.JPanel detailDataPanel;
    private javax.swing.JTextField detailDescField;
    private javax.swing.JLabel detailDescLabel;
    private javax.swing.JToolBar detailOperationsBar;
    private javax.swing.JPanel detailsDisplayPanel;
    private javax.swing.JPanel detailsPanel;
    private javax.swing.JTable detailsTable;
    private javax.swing.JScrollPane detailsTableScroller;
    private javax.swing.JButton editDetailButton;
    private javax.swing.JLabel goodLabel;
    private javax.swing.JComboBox goodsCombo;
    private javax.swing.JLabel issuerLabel;
    private javax.swing.JLabel issuerText;
    private javax.swing.JPanel masterInfo;
    private javax.swing.JLabel numberLabel;
    private javax.swing.JLabel numberText;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel positionLabel;
    private javax.swing.JSpinner positionSpinner;
    private javax.swing.JFormattedTextField quantityField;
    private javax.swing.JLabel quantityLabel;
    private javax.swing.JLabel recipientLabel;
    private javax.swing.JLabel recipientText;
    private javax.swing.JLabel unitLabel;
    private javax.swing.JFormattedTextField unitPriceField;
    private javax.swing.JLabel unitPriceLabel;
    private javax.swing.JComboBox unitsCombo;
    // End of variables declaration//GEN-END:variables

    private class SelfAncestorListener implements AncestorListener {

        public void ancestorAdded(AncestorEvent event) {
            loadUnits();
            loadGoods();
        }

        public void ancestorRemoved(AncestorEvent event) {
        }

        public void ancestorMoved(AncestorEvent event) {
        }
    }
}
