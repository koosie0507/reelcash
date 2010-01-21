/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data;

import com.google.code.reelcash.Log;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author andrei.olar
 */
public class InvoicesAdapter implements DbAdapter {

    private InvoiceDetailsAdapter detailsAdapter;

    public InvoicesAdapter() {
        detailsAdapter = new InvoiceDetailsAdapter();
    }

    public boolean clear() {
        try {
            if (detailsAdapter.clear()) {
                DbManager mgr = new DbManager();
                return mgr.getConnection().createStatement().executeUpdate("delete from invoices;") >= 0;
            }
        } catch (SQLException ex) {
            Log.write().warning(ex.toString());
        }
        return false;
    }

    public PrimaryKeyRow create(Object... params) {
        try {
            DbManager mgr = new DbManager();
            PrimaryKeyRow row = new PrimaryKeyRow("invoiceid");
            PreparedStatement stmt = mgr.getConnection().prepareStatement(
                    "insert into invoices(number, series, invoicedate, duedate, customer, billingaddress, region, country, siccode, commerceregno, iban, bank) values(?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setInt(1, (Integer) params[0]);
            stmt.setString(2, (String) params[1]);
            stmt.setDate(3, (java.sql.Date) params[2]);
            stmt.setDate(4, (java.sql.Date) params[3]);
            stmt.setString(5, (String) params[4]);
            stmt.setString(6, (String) params[5]);
            stmt.setString(7, (String) params[6]);
            stmt.setString(8, (String) params[7]);
            stmt.setString(9, (String) params[8]);
            stmt.setString(10, (String) params[9]);
            stmt.setString(11, (String) params[10]);
            stmt.setString(12, (String) params[11]);
            stmt.executeUpdate();
            row.set("invoiceid", mgr.getLastInsertedId());
            return row;
        } catch (SQLException ex) {
            Log.write().warning(ex.toString());
            return null;
        }

    }

    public boolean delete(PrimaryKeyRow key) {
        try {
            if (detailsAdapter.deleteAll(key)) {
                DbManager mgr = new DbManager();
                PreparedStatement stmt = mgr.getConnection().prepareStatement("delete from invoices where invoiceid=?;");
                stmt.setObject(1, key.get("invoiceid"));
                return stmt.executeUpdate() >= 0;
            }
        } catch (SQLException ex) {
            Log.write().warning(ex.toString());
        }
        return false;
    }

    public Hashtable<String, Object> get(PrimaryKeyRow key) {
        try {
            DbManager mgr = new DbManager();
            PreparedStatement stmt = mgr.getConnection().prepareStatement(
                    "select invoiceid, number, series, invoicedate, duedate, customer, billingaddress, region, country, siccode, commerceregno, iban, bank from invoices where invoiceid=?");
            stmt.setObject(1, key.get("invoiceid"));

            ResultSet rs = null;
            Hashtable<String, Object> ret = null;
            try {
                rs = stmt.executeQuery();
                if (rs.next()) {
                    ret = new Hashtable<String, Object>();
                    ret.put("invoiceid", rs.getInt(1));
                    ret.put("number", rs.getInt(2));
                    ret.put("series", rs.getString(3));
                    ret.put("invoicedate", rs.getDate(4));
                    ret.put("duedate", rs.getDate(5));
                    ret.put("customer", rs.getString(6));
                    ret.put("billingaddress", rs.getString(7));
                    ret.put("region", rs.getString(8));
                    ret.put("country", rs.getString(9));
                    ret.put("siccode", rs.getString(10));
                    ret.put("commerceregno", rs.getString(11));
                    ret.put("iban", rs.getString(12));
                    ret.put("bank", rs.getString(13));
                }
            } finally {
                if (null != rs) {
                    rs.close();
                }
            }
            return ret;
        } catch (SQLException ex) {
            Log.write().warning(ex.toString());
            return null;
        }
    }

    public DbAdapter getDetailsAdapter() {
        return detailsAdapter;
    }

    public boolean populateTableModel(DefaultTableModel model) {
        ResultSet rs = null;
        try {
            DbManager mgr = new DbManager();
            model.setRowCount(0);
            rs = mgr.getConnection().createStatement().executeQuery("select invoiceid, number, series, invoicedate, duedate, customer from invoices;");
            while (rs.next()) {
                Object[] rowData = new Object[]{
                    rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDate(4), rs.getDate(5), rs.getString(6)};
                for (int col = 0; col < rowData.length; col++) {
                    rowData[col] = rs.getObject(col + 1);
                }
                model.addRow(rowData);
            }
            return true;
        } catch (SQLException ex) {
            Log.write().warning(ex.toString());
            return false;
        } finally {
            if (null != rs) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Log.write().warning(ex.toString());
                }
            }
        }
    }

    public boolean populateTableModel(DefaultTableModel model, FilterMode mode, String[] filterExpressions, Object... params) {
        try {
            DbManager mgr = new DbManager();
            mgr.populateTableModel(model, "select invoiceid, number, series, date(invoicedate)*1000, date(duedate)*1000, customer from invoices", mode, filterExpressions, params);
            return true;
        } catch (SQLException ex) {
            Log.write().warning(ex.toString());
            return false;
        }

    }

    public boolean update(PrimaryKeyRow key, Object... params) {
        try {
            // TODO: de revazut logica de update - nu se poate schimba chiar orice informatie intr-o factura!
            DbManager mgr = new DbManager();
            PreparedStatement stmt = mgr.getConnection().prepareStatement(
                    "update invoices set number=?, series=?, invoicedate=?, duedate=?, customer=?, billingaddress=?, region=?, country=?, siccode=?, commerceregno=?, iban=?, bank=? where invoiceid=?");
            stmt.setInt(1, (Integer) params[0]);
            stmt.setString(2, (String) params[1]);
            stmt.setDate(3, (java.sql.Date) params[2]);
            stmt.setDate(4, (java.sql.Date) params[3]);
            stmt.setString(5, (String) params[4]);
            stmt.setString(6, (String) params[5]);
            stmt.setString(7, (String) params[6]);
            stmt.setString(8, (String) params[7]);
            stmt.setString(9, (String) params[8]);
            stmt.setString(10, (String) params[9]);
            stmt.setString(11, (String) params[10]);
            stmt.setString(12, (String) params[11]);
            stmt.setInt(13, (Integer) key.get("invoiceid"));

            return stmt.executeUpdate() >= 0;
        } catch (SQLException ex) {
            Log.write().warning(ex.toString());
            return false;
        }
    }
}
