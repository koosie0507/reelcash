/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JDocumentPanel.java
 *
 * Created on Apr 27, 2010, 10:49:38 AM
 */
package com.google.code.reelcash.swing.invoices;

import com.google.code.reelcash.Permission;
import com.google.code.reelcash.ReelcashException;
import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.ReelcashDataSource;
import com.google.code.reelcash.data.documents.DocumentNode;
import com.google.code.reelcash.data.sql.QueryMediator;
import com.google.code.reelcash.model.DataRowComboModel;
import com.google.code.reelcash.swing.ComboListCellRenderer;
import com.google.code.reelcash.util.MsgBox;
import com.google.code.reelcash.util.SysUtils;
import java.awt.Font;
import java.sql.SQLException;

/**
 *
 * @author andrei.olar
 */
public class JDocumentPanel extends javax.swing.JPanel {

    private static final long serialVersionUID = -6048890672950334891L;
    private static final String selectBusinessWithPermission = "select b.id, b.name from businesses b inner join business_permissions bp on bp.business_id = b.id inner join permissions p on p.id = bp.permission_id where p.name=?;";
    private static final String selectDocTypes = "select id, name from document_types;";
    private QueryMediator mediator;
    private DataRowComboModel issuerModel;
    private DataRowComboModel recipientModel;
    private DataRowComboModel docTypeModel;
    private ComboListCellRenderer renderer;

    /** Creates new form JDocumentPanel */
    public JDocumentPanel() {
        initComponents();
        issuerCombo.setModel(getIssuerModel());
        recipientCombo.setModel(getRecipientModel());
        typeCombo.setModel(getDocTypeModel());

        renderer = new ComboListCellRenderer(1);
        issuerCombo.setRenderer(renderer);
        recipientCombo.setRenderer(renderer);
        typeCombo.setRenderer(renderer);
    }

    private QueryMediator getMediator() {
        if (null == mediator)
            mediator = new QueryMediator(ReelcashDataSource.getInstance());
        return mediator;
    }

    private DataRowComboModel getDocTypeModel() {
        if (null == docTypeModel) {
            docTypeModel = new DataRowComboModel();
            try {
                docTypeModel.fill(getMediator().fetchSimple(selectDocTypes));
            }
            catch (SQLException e) {
                MsgBox.exception(e);
            }
        }
        return docTypeModel;
    }

    private DataRowComboModel getIssuerModel() {
        if (null == issuerModel) {
            issuerModel = new DataRowComboModel();
            try {
                issuerModel.fill(getMediator().fetch(selectBusinessWithPermission, Permission.EMIT.getData()));
                issuerModel.setValueMemberIndex(0);
                issuerModel.setDisplayMemberIndex(1);
            }
            catch (SQLException e) {
                MsgBox.exception(e);
            }
        }
        return issuerModel;
    }

    private DataRowComboModel getRecipientModel() {
        if (null == recipientModel) {
            recipientModel = new DataRowComboModel();
            try {
                recipientModel.fill(getMediator().fetch(selectBusinessWithPermission, Permission.RECEIVE.getData()));
                recipientModel.setValueMemberIndex(0);
                recipientModel.setDisplayMemberIndex(1);
            }
            catch (SQLException e) {
                MsgBox.exception(e);
            }
        }
        return recipientModel;
    }

    private boolean validateValues() {
        return (issuerCombo.getSelectedIndex() >= 0)
                && (recipientCombo.getSelectedIndex() >= 0)
                && (typeCombo.getSelectedIndex() >= 0)
                && (numberField.getText().length() > 0)
                && (dateIssuedField.getValue() != null)
                && (dateDueField.getValue() != null);
    }

    public void setDocumentNo(Object documentNo) {
        numberField.setEditable(null == documentNo);
        String docText = (null == documentNo)
                ? ""
                : documentNo.toString();
        Font fieldFont = (null == documentNo)
                ? numberField.getFont().deriveFont(Font.PLAIN)
                : numberField.getFont().deriveFont(Font.BOLD);
        numberField.setText(docText);
        numberField.setFont(fieldFont);
    }

    public DataRow getDocumentRow() {
        if (!validateValues())
            throw new ReelcashException("Vitacom, mai aproape de om!");
        DataRow row = DocumentNode.getInstance().createRow();
        row.setValue(1, numberField.getText());
        row.setValue(2, ((DataRow) typeCombo.getSelectedItem()).getValue(0));
        row.setValue(3, ((DataRow) issuerCombo.getSelectedItem()).getValue(0));
        row.setValue(4, ((DataRow) recipientCombo.getSelectedItem()).getValue(0));
        row.setValue(5, 1);
        row.setValue(6, SysUtils.now());
        row.setValue(7, dateIssuedField.getValue());
        row.setValue(8, dateDueField.getValue());

        return row;
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

    numberLabel = new javax.swing.JLabel();
    numberField = new javax.swing.JTextField();
    issuerLabel = new javax.swing.JLabel();
    issuerCombo = new javax.swing.JComboBox();
    recipientLabel = new javax.swing.JLabel();
    recipientCombo = new javax.swing.JComboBox();
    typeLabel = new javax.swing.JLabel();
    typeCombo = new javax.swing.JComboBox();
    dateIssuedLabel = new javax.swing.JLabel();
    dateIssuedField = new javax.swing.JFormattedTextField();
    dateDueLabel = new javax.swing.JLabel();
    dateDueField = new javax.swing.JFormattedTextField();

    setMinimumSize(new java.awt.Dimension(145, 275));
    setPreferredSize(new java.awt.Dimension(145, 275));
    setLayout(new java.awt.GridBagLayout());

    numberLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    numberLabel.setText("Document No.");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 0.2;
    gridBagConstraints.insets = new java.awt.Insets(15, 15, 0, 5);
    add(numberLabel, gridBagConstraints);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 15);
    add(numberField, gridBagConstraints);

    issuerLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
    issuerLabel.setText("Issuer");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 0.2;
    gridBagConstraints.insets = new java.awt.Insets(15, 15, 0, 5);
    add(issuerLabel, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 15);
    add(issuerCombo, gridBagConstraints);

    recipientLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
    recipientLabel.setText("Recipient");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 0.2;
    gridBagConstraints.insets = new java.awt.Insets(15, 15, 0, 5);
    add(recipientLabel, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 15);
    add(recipientCombo, gridBagConstraints);

    typeLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
    typeLabel.setText("Document type");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 0.2;
    gridBagConstraints.insets = new java.awt.Insets(15, 15, 0, 5);
    add(typeLabel, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 15);
    add(typeCombo, gridBagConstraints);

    dateIssuedLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
    dateIssuedLabel.setText("Issued on");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 0.2;
    gridBagConstraints.insets = new java.awt.Insets(15, 15, 0, 5);
    add(dateIssuedLabel, gridBagConstraints);

    dateIssuedField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd.MM.yyyy"))));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 15);
    add(dateIssuedField, gridBagConstraints);

    dateDueLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
    dateDueLabel.setText("Due by");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 5;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 0.2;
    gridBagConstraints.insets = new java.awt.Insets(15, 15, 20, 5);
    add(dateDueLabel, gridBagConstraints);

    dateDueField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd.MM.yyyy"))));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 5;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(15, 0, 20, 15);
    add(dateDueField, gridBagConstraints);
  }// </editor-fold>//GEN-END:initComponents
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JFormattedTextField dateDueField;
  private javax.swing.JLabel dateDueLabel;
  private javax.swing.JFormattedTextField dateIssuedField;
  private javax.swing.JLabel dateIssuedLabel;
  private javax.swing.JComboBox issuerCombo;
  private javax.swing.JLabel issuerLabel;
  private javax.swing.JTextField numberField;
  private javax.swing.JLabel numberLabel;
  private javax.swing.JComboBox recipientCombo;
  private javax.swing.JLabel recipientLabel;
  private javax.swing.JComboBox typeCombo;
  private javax.swing.JLabel typeLabel;
  // End of variables declaration//GEN-END:variables
}
