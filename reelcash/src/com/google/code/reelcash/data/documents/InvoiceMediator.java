package com.google.code.reelcash.data.documents;

import com.google.code.reelcash.ReelcashException;
import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.ReelcashDataSource;
import com.google.code.reelcash.data.layout.fields.DateField;
import com.google.code.reelcash.data.layout.fields.FieldList;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.StringField;
import com.google.code.reelcash.data.sql.QueryMediator;
import com.google.code.reelcash.data.taxes.ExciseTypeNode;
import com.google.code.reelcash.data.taxes.TaxTypeNode;
import com.google.code.reelcash.data.taxes.TaxesMediator;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides persistence related operations for invoices.
 * 
 * @author cusi
 */
public class InvoiceMediator extends QueryMediator {

    private static InvoiceMediator instance;
    private Integer invoiceDocTypeId;
    /**
     * Represents a constant which is the default percent of VAT if no
     * default can be found in the database.
     */
    public static final BigDecimal DEFAULT_VAT_PERCENT = BigDecimal.valueOf(0.24);

    private InvoiceMediator() {
        super(ReelcashDataSource.getInstance());
    }

    /**
     * Returns a singleton instance of the current class.
     *
     * @return an instance of the current class.
     */
    public static InvoiceMediator getInstance() {
        if (null == instance) {
            instance = new InvoiceMediator();
        }
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
            if (null == invoiceDocTypeId) // we don't have the invoice type in the DB
            {
                if (0 < execute(sql2, DocumentType.INVOICE.getName(), DocumentType.INVOICE.getLocalizedDescription())) {
                    invoiceDocTypeId = (Integer) executeScalar(sql3);
                }
            }
            commit();
        } catch (SQLException ex) {
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
        final String sql = "insert into invoices(document_id, currency_id, issuer_rep_id, recipient_rep_id, exchange_rate_id, total_amount, total_excise, total_taxes, total_vat, total) values(?,?,?,?,?,?,?,?,?,?);";
        Integer invoiceId = null;
        try {
            beginTransaction();
            if (0 < execute(sql, documentId, currencyId, issuerRep, recipientRep, exchangeRate, 0, 0, 0, 0, 0)) {
                invoiceId = (Integer) executeScalar("select last_insert_rowid();");
            }
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ReelcashException(e);
        }
        return invoiceId;
    }

    public DataRow createInvoiceDetail(Integer invoiceId, Integer position, Integer goodId, String detailText, Integer unitId, BigDecimal quantity, BigDecimal unitPrice) {
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
                DataRow defaultVatType = TaxesMediator.getInstance().getDefaultVatType();
                BigDecimal vatPercent = (null == defaultVatType)
                        ? DEFAULT_VAT_PERCENT
                        : (BigDecimal) defaultVatType.getValue("percent");
                BigDecimal amount = quantity.multiply(unitPrice);
                BigDecimal vatAmount = amount.multiply(vatPercent);

                // this is the easy case: no taxes, nothing... just the invoice
                invoiceDetail.setValue(8, BigDecimal.ZERO);
                invoiceDetail.setValue(9, BigDecimal.ZERO);
                invoiceDetail.setValue(10, amount);
                invoiceDetail.setValue(11, vatPercent);
                invoiceDetail.setValue(12, vatAmount);
                invoiceDetail.setValue(13, amount.add(vatAmount));
                if (createRow(InvoiceDetailNode.getInstance().getName(), invoiceDetail)) {
                    invoiceDetail.setValue(0, executeScalar("select last_insert_rowid();"));
                }
            } else {
                if (createRow(InvoiceDetailNode.getInstance().getName(), invoiceDetail)) {
                    invoiceDetail.setValue(0, executeScalar("select last_insert_rowid();"));
                    Number ret = (Number) executeScalar("select vt.percent from vat_types vt inner join goods g on vt.id=g.vat_type_id where g.id=?", goodId);
                    BigDecimal vatPercent = BigDecimal.valueOf(ret.doubleValue());
                    BigDecimal basicPrice = quantity.multiply(unitPrice);

                    // compute applied taxes
                    DataRow[] taxes = TaxesMediator.getInstance().getAppliedTaxesForGood(goodId);
                    BigDecimal taxAmount = BigDecimal.ZERO;
                    for (DataRow tax : taxes) {
                        boolean isPercent = ((Boolean) tax.getValue(TaxTypeNode.IS_PERCENT_FIELD)).booleanValue();
                        BigDecimal taxValue = (BigDecimal) tax.getValue(TaxTypeNode.VALUE_FIELD);

                        if (isPercent) {
                            taxValue = taxValue.multiply(basicPrice);
                        }

                        taxAmount = taxAmount.add(taxValue);
                        execute("insert into invoice_detail_taxes(invoice_detail_id, tax_code, amount) values(?, ?, ?);",
                                invoiceDetail.getValue(0), tax.getValue(TaxTypeNode.CODE_FIELD), taxValue);
                    }

                    // compute applied excises
                    BigDecimal exciseAmount = BigDecimal.ZERO;
                    DataRow[] excises = TaxesMediator.getInstance().getAppliedExcisesForGood(goodId);
                    for (DataRow excise : excises) {
                        boolean isPercent = ((Boolean) excise.getValue(ExciseTypeNode.IS_PERCENT_FIELD)).booleanValue();
                        BigDecimal exciseValue = (BigDecimal) excise.getValue(ExciseTypeNode.VALUE_FIELD);
                        if (isPercent) {
                            exciseValue = exciseValue.multiply(basicPrice);
                        }

                        exciseAmount = exciseAmount.add(exciseValue);
                        execute("insert into invoice_detail_excises(invoice_detail_id, excise_code, amount) values(?, ?, ?);",
                                invoiceDetail.getValue(0), excise.getValue(ExciseTypeNode.CODE_FIELD), exciseValue);
                    }

                    // compute totals on detail
                    invoiceDetail.setValue(8,taxAmount);
                    invoiceDetail.setValue(9, exciseAmount);
                    BigDecimal amount = basicPrice.add(taxAmount).add(exciseAmount);
                    invoiceDetail.setValue(10, amount);
                    invoiceDetail.setValue(11, vatPercent);
                    BigDecimal vatAmount = amount.multiply(vatPercent);
                    invoiceDetail.setValue(12, vatAmount);
                    invoiceDetail.setValue(13, amount.add(vatAmount));

                    // persist the data row
                    updateRow(InvoiceDetailNode.getInstance(), invoiceDetail);

                    // invoice totals are updated with triggers
                }
            }
            commit();
            return invoiceDetail;
        } catch (SQLException e) {
            rollback();
            throw new ReelcashException(e);
        }
    }

    public void updateInvoiceDetail(DataRow detail, BigDecimal newQuantity, BigDecimal newUnitPrice) {
        try {
            detail.setValue("quantity", newQuantity);
            detail.setValue("unit_price", newUnitPrice);
            BigDecimal taxAmount = (BigDecimal) detail.getValue("tax_amount");
            BigDecimal exciseAmount = (BigDecimal) detail.getValue("excise_amount");
            BigDecimal vatPercent = (BigDecimal) detail.getValue("vat_percent");
            BigDecimal amount = newQuantity.multiply(newUnitPrice);
            detail.setValue("amount", amount);
            amount = amount.add(taxAmount);
            amount = amount.add(exciseAmount);
            BigDecimal vatAmount = amount.multiply(vatPercent);
            detail.setValue("price", vatAmount.add(amount));

            beginTransaction();
            updateRow(InvoiceDetailNode.getInstance(), detail);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ReelcashException(e);
        }
    }

    public void deleteInvoice(Integer invoiceId) {
        try {
            beginTransaction();
            execute("delete from documents where id in (select distinct document_id from invoices where id = ?);", invoiceId);
            execute("delete from invoice_detail_taxes where invoice_detail_id in (select distinct id from invoice_details where invoice_id = ?);", invoiceId);
            execute("delete from invoice_detail_excises where invoice_detail_id in (select distinct id from invoice_details where invoice_id = ?);", invoiceId);
            execute("delete from invoice_details where invoice_id = ?;", invoiceId);
            execute("delete from invoices where id = ?;", invoiceId);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ReelcashException(e);
        }
    }

    public DataRow getInvoiceInformation(Integer invoiceId) {
        final String sql = "select d.number, d.date_issued, d.date_due, b1.name || ', ' || b1.address || ' - ' || b1.city || ', ' || b1.county as issuer, b2.name || ', ' || b2.address || ' - ' || b2.city || ', ' || b2.county as recipient, ds.name, d.create_date from invoices i inner join documents d on d.id = i.document_id inner join business_addresses b1 on b1.business_id = d.issuer_id inner join business_addresses b2 on b2.business_id = d.recipient_id inner join document_states ds on ds.id = d.state_id where i.id=?";
        try {
            FieldList list = new FieldList();
            list.add(new StringField("d.number", KeyRole.NONE, true));
            list.add(new DateField("d.date_issued", KeyRole.NONE, true));
            list.add(new DateField("d.date_due", KeyRole.NONE, true));
            list.add(new StringField("issuer", KeyRole.NONE, true));
            list.add(new StringField("recipient", KeyRole.NONE, true));
            list.add(new StringField("ds.name", KeyRole.NONE, true));
            list.add(new DateField("d.create_date", KeyRole.NONE, true));

            DataRow[] row = fetch(list, sql, invoiceId);
            if (1 > row.length) {
                throw new ReelcashException(DocumentResources.getString("invoice_information_not_found")); // NOI18N
            }
            return row[0];
        } catch (SQLException e) {
            throw new ReelcashException(e);
        }
    }

    public Integer getNextDetailPosition(Integer invoiceId) {
        final String sql = "select max(position) + 1 from invoice_details where invoice_id=?";
        try {
            Object val = executeScalar(sql, invoiceId);
            return (Integer) (null == val ? 1 : val);
        } catch (SQLException e) {
            throw new ReelcashException(e);
        }
    }

    public List<DataRow> getInvoicedGoods(Integer invoiceId) {
        try {
            ArrayList<DataRow> ret = new ArrayList<DataRow>();
            for (DataRow row : fetch("select g.id, g.name from invoice_details d inner join goods g on g.id=d.good_id where d.invoice_id=?", invoiceId)) {
                ret.add(row);
            }
            return ret;
        } catch (SQLException ex) {
            throw new ReelcashException(ex);
        }
    }

    public List<DataRow> getInvoicedUnits(Integer invoiceId) {
        try {
            ArrayList<DataRow> ret = new ArrayList<DataRow>();
            for (DataRow row : fetch("select u.id, u.code from invoice_details d inner join units u on u.id=d.unit_id where d.invoice_id=?", invoiceId)) {
                ret.add(row);
            }
            return ret;
        } catch (SQLException ex) {
            throw new ReelcashException(ex);
        }
    }

    /**
     * Returns the state of the invoice (emitted, new, etc).
     * @param invoiceId the ID of the invoice
     * @return a document state
     * @exception ReelcashException is thrown if an invoice with the specified
     * ID could not be found or a database error has occured.
     */
    public DocumentState getState(Integer invoiceId) {
        try {
            DataRow[] doc = fetch(DocumentNode.getInstance().getFieldList(),
                    "select documents.* from documents inner join invoices on documents.id = invoices.document_id where invoices.id=?",
                    invoiceId);
            if (1 > doc.length) {
                throw new ReelcashException();
            }

            return DocumentMediator.getInstance().getState(
                    (Integer) doc[0].getValue("state_id"));

        } catch (SQLException e) {
            throw new ReelcashException(e);
        }
    }

    public DataRow[] readInvoiceDetails(Integer invoiceId) {
        try {
            return fetch(InvoiceDetailNode.getInstance().getFieldList(), "select * from invoice_details where invoice_id=?", invoiceId);
        } catch (SQLException ex) {
            throw new ReelcashException(ex);
        }
    }

    /**
     * Returns a list of invoices. The structure of the returned data rows is not necessarily the one described by the
     * @see InvoiceNode class.
     * @return an array of data rows. Each data row contains information about an invoice header.
     */
    public DataRow[] readInvoices() {
        final String selectListInvoices = "select `invoices`.`id` as invoice_id, `documents`.`id` as document_id, `documents`.`number` as invoice_number, `documents`.`create_date` as create_date, `documents`.`date_issued` as date_issued, `documents`.`date_due` as date_due, issuers.`id` as issuer_id, issuers.`name` as issuer_name, recipients.`id` as recipient_id, recipients.`name` as recipient_name from `invoices` inner join `documents` on `documents`.`id` = `invoices`.`document_id` inner join `businesses` issuers on issuers.`id` = `documents`.`issuer_id` inner join `businesses` recipients on recipients.`id` = `documents`.`recipient_id`";
        try {
            FieldList list = new FieldList();
            list.add(new IntegerField("invoice_id", KeyRole.NONE, true));
            list.add(new IntegerField("document_id", KeyRole.NONE, true));
            list.add(new StringField("invoice_number", KeyRole.NONE, true));
            list.add(new DateField("create_date", KeyRole.NONE, true));
            list.add(new DateField("date_issued", KeyRole.NONE, true));
            list.add(new DateField("date_due", KeyRole.NONE, true));
            list.add(new IntegerField("issuer_id", KeyRole.NONE, true));
            list.add(new StringField("issuer_name", KeyRole.NONE, true));
            list.add(new IntegerField("recipient_id", KeyRole.NONE, true));
            list.add(new StringField("recipient_name", KeyRole.NONE, true));

            return fetch(list, selectListInvoices);
        } catch (SQLException ex) {
            throw new ReelcashException(ex);
        }
    }
}
