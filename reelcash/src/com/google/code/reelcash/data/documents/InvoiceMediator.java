package com.google.code.reelcash.data.documents;

import com.google.code.reelcash.ReelcashException;
import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.ReelcashDataSource;
import com.google.code.reelcash.data.layout.fields.DateField;
import com.google.code.reelcash.data.layout.fields.FieldList;
import com.google.code.reelcash.data.layout.fields.StringField;
import com.google.code.reelcash.data.sql.QueryMediator;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Provides persistence related operations for invoices.
 * 
 * @author cusi
 */
public class InvoiceMediator extends QueryMediator {

    private static InvoiceMediator instance;
    private Integer invoiceDocTypeId;

    private InvoiceMediator() {
        super(ReelcashDataSource.getInstance());
    }

    /**
     * Returns a singleton instance of the current class.
     *
     * @return an instance of the current class.
     */
    public static InvoiceMediator getInstance() {
        if (null == instance)
            instance = new InvoiceMediator();
        return instance;
    }

    /**
     * Ensures the existence of the invoice document type in the database.
     */
    public void ensureDocumentType() {
        final String sql1 = "select id from document_types where name=?";
        final String sql2 = "insert into document_types(name, description) values(?,?);";
        final String sql3 = "select last_insert_rowid();";
        try {
            beginTransaction();
            invoiceDocTypeId = (Integer) executeScalar(sql1, DocumentType.INVOICE.getName());
            if (null == invoiceDocTypeId)
                // we don't have the invoice type in the DB
                if (0 < execute(sql2, DocumentType.INVOICE.getName(), DocumentType.INVOICE.getLocalizedDescription()))
                    invoiceDocTypeId = (Integer) executeScalar(sql3);
            commit();
        }
        catch (SQLException ex) {
            rollback();
            throw new ReelcashException(ex);
        }
    }

    /**
     * Returns the document type of the given invoice.
     * @return the invoice document type ID.
     */
    public Integer getInvoiceDocTypeId() {
        return invoiceDocTypeId;
    }

    /**
     * Creates a new invoice.
     *
     * @param documentId master document ID
     * @param currencyId currency of invoice
     * @param issuerRep representative of issuing party.
     * @param recipientRep representative of receiving party.
     * @param exchangeRate used exchange rate ID.
     * @return invoice ID.
     */
    public Integer createInvoice(Integer documentId, Integer currencyId, Integer issuerRep, Integer recipientRep, Integer exchangeRate) {
        final String sql = "insert into invoices(document_id, currency_id, issuer_rep_id, recipient_rep_id, exchange_rate_id, total_amount, total_excise, total_taxes, total_vat) values(?,?,?,?,?,?,?,?,?);";
        Integer invoiceId = null;
        try {
            beginTransaction();
            if (0 < execute(sql, documentId, currencyId, issuerRep, recipientRep, exchangeRate, 0, 0, 0, 0))
                invoiceId = (Integer) executeScalar("select last_insert_rowid();");
            commit();
        }
        catch (SQLException e) {
            rollback();
            throw new ReelcashException(e);
        }
        return invoiceId;
    }

    public void createInvoiceDetail(Integer invoiceId, Integer position, Integer goodId, String detailText, Integer unitId, BigDecimal quantity, BigDecimal unitPrice) {
        final DataRow invoiceDetail = InvoiceDetailNode.getInstance().createRow();
        invoiceDetail.setValue(1, invoiceId);
        invoiceDetail.setValue(2, position);
        invoiceDetail.setValue(3, goodId);
        invoiceDetail.setValue(4, detailText);
        invoiceDetail.setValue(5, unitId);
        invoiceDetail.setValue(6, quantity);
        invoiceDetail.setValue(7, unitPrice);
        try {
            beginTransaction();
            if (null == goodId) {
                BigDecimal vatPercent = BigDecimal.valueOf(0.19);
                BigDecimal amount = quantity.multiply(unitPrice);
                BigDecimal vatAmount = amount.multiply(vatPercent);

                // this is the easy case: no taxes, nothing... just the invoice
                invoiceDetail.setValue(8, BigDecimal.ZERO);
                invoiceDetail.setValue(9, BigDecimal.ZERO);
                invoiceDetail.setValue(10, amount);
                invoiceDetail.setValue(11, vatPercent);
                invoiceDetail.setValue(12, vatAmount);
                invoiceDetail.setValue(13, amount.add(vatAmount));
                if (createRow(InvoiceDetailNode.getInstance().getName(), invoiceDetail))
                    invoiceDetail.setValue(0, executeScalar("select last_insert_rowid();"));
            }
            else {
                if (createRow(InvoiceDetailNode.getInstance().getName(), invoiceDetail))
                    invoiceDetail.setValue(0, executeScalar("select last_insert_rowid();"));
                Object ret = executeScalar("select vt.percent from vat_types vt inner join goods g on vt.id=g.vat_type_id where g.id=?", goodId);
                BigDecimal vatPercent = BigDecimal.valueOf(((Number) ret).doubleValue());
                BigDecimal basicPrice = quantity.multiply(unitPrice);

                // compute applied taxes
                DataRow[] taxes = fetch("select tt.code, tt.is_percent, tt.value from tax_types tt inner join good_taxes gt on gt.tax_type_id=tt.id where gt.good_id=?", goodId);
                for (DataRow tax : taxes) {
                    BigDecimal taxValue = (0 == ((Number) tax.getValue(1)).intValue())
                            ? BigDecimal.valueOf(((Number) tax.getValue(2)).doubleValue())
                            : BigDecimal.valueOf(((Number) tax.getValue(2)).doubleValue()).multiply(basicPrice);

                    execute("insert into invoice_detail_taxes(invoice_detail_id, tax_code, amount) values(?, ?, ?);",
                            invoiceDetail.getValue(0), tax.getValue(0), taxValue);
                }

                // compute applied excises
                DataRow[] excises = fetch("select et.code, et.is_percent, et.value from excise_types et inner join good_excises ge on ge.excise_type_id = et.id where ge.good_id=?", goodId);
                for (DataRow excise : excises) {
                    BigDecimal exciseValue = (0 == ((Number) excise.getValue(1)).intValue())
                            ? BigDecimal.valueOf(((Number) excise.getValue(2)).doubleValue())
                            : BigDecimal.valueOf(((Number) excise.getValue(2)).doubleValue()).multiply(basicPrice);
                    execute("insert into invoice_detail_excises(invoice_detail_id, excise_code, amount) values(?, ?, ?);",
                            invoiceDetail.getValue(0), excise.getValue(0), exciseValue);
                }

                // compute totals on detail
                Number buf = (Number) executeScalar("select sum(amount) from invoice_detail_taxes where invoice_detail_id=?;", invoiceDetail.getValue(0));
                BigDecimal taxAmount = BigDecimal.valueOf((null == buf) ? 0 : buf.doubleValue());
                buf = (Number) executeScalar("select sum(amount) from invoice_detail_excises where invoice_detail_id=?;", invoiceDetail.getValue(0));
                BigDecimal exciseAmount = BigDecimal.valueOf(null == buf ? 0 : buf.doubleValue());
                execute("update invoice_details set tax_amount=?, excise_amount=? where id=?;",
                        taxAmount, exciseAmount, invoiceDetail.getValue(0));
                execute("update invoice_details set amount=tax_amount+excise_amount+(unit_price*quantity), vat_percent=? where id=?;",
                        vatPercent, invoiceDetail.getValue(0));
                execute("update invoice_details set vat_amount=vat_percent*amount, price=amount*(1+vat_percent) where id=?",
                        invoiceDetail.getValue(0));
            }
            commit();
        }
        catch (SQLException e) {
            rollback();
            throw new ReelcashException(e);
        }
    }

    public DataRow getInvoiceInformation(Integer invoiceId) {
        final String sql = "select d.number, d.date_issued, d.date_due, b1.name || ', ' || b1.address || ' - ' || b1.city || ', ' || b1.county as issuer, b2.name || ', ' || b2.address || ' - ' || b2.city || ', ' || b2.county as recipient from invoices i inner join documents d on d.id = i.document_id inner join business_addresses b1 on b1.business_id = d.issuer_id inner join business_addresses b2 on b2.business_id = d.recipient_id where i.id=?";
        try {
            FieldList list = new FieldList();
            list.add(new StringField("d.number", KeyRole.NONE, true));
            list.add(new DateField("d.date_issued", KeyRole.NONE, true));
            list.add(new DateField("d.date_due", KeyRole.NONE, true));
            list.add(new StringField("issuer", KeyRole.NONE, true));
            list.add(new StringField("recipient", KeyRole.NONE, true));

            DataRow[] row = fetch(list, sql, invoiceId);
            if (1 > row.length)
                throw new ReelcashException(DocumentResources.getString("invoice_information_not_found")); // NOI18N
            return row[0];
        }
        catch (SQLException e) {
            throw new ReelcashException(e);
        }
    }

    public Integer getNextDetailPosition(Integer invoiceId) {
        final String sql = "select max(position) + 1 from invoice_details where invoice_id=?";
        try {
            Object val = executeScalar(sql, invoiceId);
            return (Integer) (null == val ? 1 : val);
        }
        catch (SQLException e) {
            throw new ReelcashException(e);
        }
    }
}
