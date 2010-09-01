/*
 * JInvoiceParamsPanel.java
 *
 * Created on Apr 29, 2010, 9:55:29 AM
 */
package com.google.code.reelcash.swing.invoices;

import com.google.code.reelcash.Log;
import com.google.code.reelcash.ReelcashException;
import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.ReelcashDataSource;
import com.google.code.reelcash.data.sql.QueryMediator;
import com.google.code.reelcash.model.DataRowComboModel;
import com.google.code.reelcash.swing.ComboListCellRenderer;
import com.google.code.reelcash.util.MsgBox;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 *
 * @author andrei.olar
 */
public class JInvoiceParamsPanel extends javax.swing.JPanel {

    private static final long serialVersionUID = 4949072367604042872L;
    private final QueryMediator mediator = new QueryMediator(ReelcashDataSource.getInstance());
    private boolean mustExchange;
    private Integer currencyId;
    private Integer issuerRepId;
    private Integer recipientRepId;
    private Integer exchangeRateId;
    private String referenceCurrencyCode = "";
    private Integer documentId;
    private ComboListCellRenderer renderer = new ComboListCellRenderer(1);

    /** Creates new form JInvoiceParamsPanel */
    public JInvoiceParamsPanel() {
        initComponents();
        addAncestorListener(new PanelAncestorListener());
        currencyCombo.setRenderer(renderer);
        bankCombo.setRenderer(renderer);
        issuerRepCombo.setRenderer(renderer);
        recipientRepCombo.setRenderer(renderer);
        RepStateListener listener = new RepStateListener();
        issuerRepCombo.addItemListener(listener);
        recipientRepCombo.addItemListener(listener);
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public Integer getExchangeRateId() {
        return exchangeRateId;
    }

    public Integer getIssuerRepId() {
        return issuerRepId;
    }

    public Integer getRecipientRepId() {
        return recipientRepId;
    }

    public boolean isDataValid() {
        try {
            if (null == JInvoiceParamsPanel.this.currencyId) {
                throw new ReelcashException(InvoiceResources.getString("invoice_currency_missing_error")); // NOI18N
            }
            if (JInvoiceParamsPanel.this.mustExchange && null == JInvoiceParamsPanel.this.exchangeRateId) {
                throw new ReelcashException(InvoiceResources.getString("invoice_exchange_rate_missing_error")); // NOI18N
            }
            return true;
        } catch (Throwable t) {
            MsgBox.error(t.getLocalizedMessage());
            return false;
        }

    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public void setExchangeRateId(Integer exchangeRateId) {
        this.exchangeRateId = exchangeRateId;
    }

    public void setIssuerRepId(Integer issuerRepId) {
        this.issuerRepId = issuerRepId;
    }

    public void setRecipientRepId(Integer recipientRepId) {
        this.recipientRepId = recipientRepId;
    }

    public static void main(String[] args) {
        JFrame frm = new JFrame();
        JInvoiceParamsPanel pnl = new JInvoiceParamsPanel();
        pnl.setDocumentId(8);
        frm.add(pnl);
        frm.pack();
        frm.setVisible(true);
    }

    private void computeExchangeRate(Integer bankId) {
        // selectu asta ii o blana pentru ca trebuie sa se uite la data emiterii documentului master si nu la date('now')!!!
        final String documentDateSql = "select date_issued from documents where id=?";
        final String exchangeRate = "select exchange_rates.id, currencies.code, exchange_rates.value from exchange_rates inner join currencies on currencies.id = exchange_rates.currency_id where (currency_id=?) and (bank_id=?) and ((? between start_date and end_date) or permanent=1)";
        try {
            Date dateIssued = new Date(((Long) mediator.executeScalar(documentDateSql, getDocumentId())).longValue());
            DataRow[] rows = mediator.fetch(exchangeRate, currencyId, bankId, dateIssued);
            Log.write().warning(exchangeRate.toString());
            Log.write().warning(currencyId.toString());
            Log.write().warning(bankId.toString());
            Log.write().warning(dateIssued.toString());
            if (1 > rows.length) {
                exchangeRateText.setForeground(Color.red);
                exchangeRateText.setText(InvoiceResources.getString("exchange_rate_not_found"));
                exchangeRateId = null;
            } else {
                exchangeRateId = (Integer) rows[0].getValue(0);
                exchangeRateText.setForeground(Color.blue);
                exchangeRateText.setText(String.format("1 %s = %s %s", rows[0].getValue(1), rows[0].getValue(2), referenceCurrencyCode));
            }
        } catch (SQLException e) {
            exchangeRateId = null;
            exchangeRateText.setForeground(Color.RED);
            MsgBox.exception(e);
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

        currencyLabel = new javax.swing.JLabel();
        currencyCombo = new javax.swing.JComboBox();
        bankLabel = new javax.swing.JLabel();
        bankCombo = new javax.swing.JComboBox();
        issuerRepLabel = new javax.swing.JLabel();
        issuerRepCombo = new javax.swing.JComboBox();
        recipientRepLabel = new javax.swing.JLabel();
        recipientRepCombo = new javax.swing.JComboBox();
        exchangeRateLabel = new javax.swing.JLabel();
        exchangeRateText = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(235, 200));
        setPreferredSize(new java.awt.Dimension(235, 200));
        setLayout(new java.awt.GridBagLayout());

        currencyLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
        currencyLabel.setText("Currency:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 0, 5);
        add(currencyLabel, gridBagConstraints);

        currencyCombo.setModel(new DataRowComboModel());
        currencyCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                currencyComboItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 0, 15);
        add(currencyCombo, gridBagConstraints);

        bankLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
        bankLabel.setText("Exchange rate:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 0, 5);
        add(bankLabel, gridBagConstraints);

        bankCombo.setModel(new DataRowComboModel());
        bankCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                bankComboItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 0, 15);
        add(bankCombo, gridBagConstraints);

        issuerRepLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
        issuerRepLabel.setText("Issuer representative:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 0, 5);
        add(issuerRepLabel, gridBagConstraints);

        issuerRepCombo.setModel(new DataRowComboModel());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 0, 15);
        add(issuerRepCombo, gridBagConstraints);

        recipientRepLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
        recipientRepLabel.setText("Recipient representative:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 5);
        add(recipientRepLabel, gridBagConstraints);

        recipientRepCombo.setModel(new DataRowComboModel());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 15, 15);
        add(recipientRepCombo, gridBagConstraints);

        exchangeRateLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
        exchangeRateLabel.setText("Exchange rate:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 0, 5);
        add(exchangeRateLabel, gridBagConstraints);

        exchangeRateText.setFont(new java.awt.Font("Tahoma", 2, 11));
        exchangeRateText.setForeground(java.awt.Color.blue);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 0, 15);
        add(exchangeRateText, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void currencyComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_currencyComboItemStateChanged
        if (null == evt.getItem()) {
            return;
        }
        if (ItemEvent.SELECTED != evt.getStateChange()) {
            return;
        }
        DataRowComboModel model = (DataRowComboModel) bankCombo.getModel();
        DataRow row = (DataRow) evt.getItem();
        currencyId = (Integer) row.getValue(0);
        mustExchange = ((Integer) row.getValue(2)).intValue() != 0;
        if (mustExchange) {
            if (model.getSize() < 1 || (bankCombo.getSelectedIndex() < 0)) {
                try {
                    model.fill(mediator.fetchSimple("select id, name from banks where allow_currency_exchange"));
                } catch (SQLException e) {
                    MsgBox.exception(e);
                }
            } else { // we already loaded the available banks in the combo
                row = (DataRow) bankCombo.getSelectedItem();
                Integer bankId = (Integer) row.getValue(0);
                computeExchangeRate(bankId);
            }
        } else {
            bankCombo.setSelectedIndex(-1);
            model.clear();
            computeExchangeRate(-1);
            exchangeRateId = null;
        }
    }//GEN-LAST:event_currencyComboItemStateChanged

    private void bankComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_bankComboItemStateChanged
        if (null == evt.getItem()) {
            return;
        }
        if (ItemEvent.SELECTED != evt.getStateChange()) {
            return;
        }
        DataRow row = (DataRow) evt.getItem();
        Integer bankId = (Integer) row.getValue(0);
        computeExchangeRate(bankId);
    }//GEN-LAST:event_bankComboItemStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox bankCombo;
    private javax.swing.JLabel bankLabel;
    private javax.swing.JComboBox currencyCombo;
    private javax.swing.JLabel currencyLabel;
    private javax.swing.JLabel exchangeRateLabel;
    private javax.swing.JLabel exchangeRateText;
    private javax.swing.JComboBox issuerRepCombo;
    private javax.swing.JLabel issuerRepLabel;
    private javax.swing.JComboBox recipientRepCombo;
    private javax.swing.JLabel recipientRepLabel;
    // End of variables declaration//GEN-END:variables

    private class PanelAncestorListener implements AncestorListener {

        public void ancestorAdded(AncestorEvent event) {
            if (!JInvoiceParamsPanel.this.equals(event.getSource())) {
                return;
            }

            JInvoiceParamsPanel.this.currencyId = null;
            JInvoiceParamsPanel.this.exchangeRateId = null;
            JInvoiceParamsPanel.this.issuerRepId = null;
            JInvoiceParamsPanel.this.recipientRepId = null;
            JInvoiceParamsPanel.this.referenceCurrencyCode = "";
            JInvoiceParamsPanel.this.mustExchange = false;

            DataRowComboModel currenciesModel = (DataRowComboModel) JInvoiceParamsPanel.this.currencyCombo.getModel();
            DataRowComboModel issuerRepsModel = (DataRowComboModel) JInvoiceParamsPanel.this.issuerRepCombo.getModel();
            DataRowComboModel recipientRepsModel = (DataRowComboModel) JInvoiceParamsPanel.this.recipientRepCombo.getModel();
            QueryMediator m = JInvoiceParamsPanel.this.mediator;
            try {
                currenciesModel.fill(m.fetchSimple("select id, code, must_exchange from currencies;"));
                issuerRepsModel.fill(m.fetch("select c.id, c.name||' '||c.surname as full_name from contacts c inner join business_representatives br on br.contact_id=c.id inner join documents d on d.issuer_id=br.business_id where d.id=?",
                        getDocumentId()));
                recipientRepsModel.fill(m.fetch("select c.id, c.name||' '||c.surname as full_name from contacts c inner join business_representatives br on br.contact_id=c.id inner join documents d on d.recipient_id=br.business_id where d.id=?",
                        getDocumentId()));
                referenceCurrencyCode = (String) JInvoiceParamsPanel.this.mediator.executeScalar("select code from currencies where must_exchange=0;");
                if (null == referenceCurrencyCode) {
                    referenceCurrencyCode = "";
                }
            } catch (SQLException e) {
                MsgBox.exception(e);
            }
        }

        public void ancestorRemoved(AncestorEvent event) {
            try {
                if (!JInvoiceParamsPanel.this.equals(event.getSource())) {
                    return;
                }

                Container cntr = event.getAncestor();
                DataRowComboModel model = (DataRowComboModel) JInvoiceParamsPanel.this.currencyCombo.getModel();
                model.clear();
                model = (DataRowComboModel) JInvoiceParamsPanel.this.bankCombo.getModel();
                model.clear();
                model = (DataRowComboModel) JInvoiceParamsPanel.this.issuerRepCombo.getModel();
                model.clear();
                model = (DataRowComboModel) JInvoiceParamsPanel.this.recipientRepCombo.getModel();
                model.clear();
                JInvoiceParamsPanel.this.currencyCombo.setSelectedIndex(-1);
                JInvoiceParamsPanel.this.bankCombo.setSelectedIndex(-1);
                JInvoiceParamsPanel.this.exchangeRateText.setText("");
                JInvoiceParamsPanel.this.issuerRepCombo.setSelectedIndex(-1);
                JInvoiceParamsPanel.this.recipientRepCombo.setSelectedIndex(-1);
            } catch (Throwable t) {
                MsgBox.error(t.getLocalizedMessage());
            }
        }

        public void ancestorMoved(AncestorEvent event) {
            if (!JInvoiceParamsPanel.this.equals(event.getSource())) {
                return;
            }
        }
    }

    private class RepStateListener implements ItemListener {

        public void itemStateChanged(ItemEvent e) {
            if (JInvoiceParamsPanel.this.issuerRepCombo.equals(e.getSource())) {
                JInvoiceParamsPanel.this.issuerRepId = (Integer) ((DataRowComboModel) JInvoiceParamsPanel.this.issuerRepCombo.getModel()).getSelectedValue();
                return;
            }

            if (JInvoiceParamsPanel.this.recipientRepCombo.equals(e.getSource())) {
                JInvoiceParamsPanel.this.recipientRepId = (Integer) ((DataRowComboModel) JInvoiceParamsPanel.this.recipientRepCombo.getModel()).getSelectedValue();
                return;
            }
        }
    }
}
