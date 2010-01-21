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
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 * Provides an adapter which works with the 'contacts' table from the database.
 * @author andrei.olar
 */
public class ContactsAdapter implements DbAdapter {

    public PrimaryKeyRow create(Object... params) {
        PrimaryKeyRow key = new PrimaryKeyRow("contactid");
        try {
            DbManager mgr = new DbManager();
            PreparedStatement stmt = mgr.getConnection().prepareStatement("INSERT INTO contacts(name, siccode, commerceregno, address, region, country, iban, bank, socialcapital, repname, repidtype, repidentification, repaddress) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setObject(1, params[0]);
            stmt.setObject(2, params[1]);
            stmt.setObject(3, params[2]);
            stmt.setObject(4, params[3]);
            stmt.setObject(5, params[4]);
            stmt.setObject(6, params[5]);
            stmt.setObject(7, params[6]);
            stmt.setObject(8, params[7]);
            stmt.setObject(9, params[8]);
            stmt.setObject(10, params[9]);
            stmt.setObject(11, params[10]);
            stmt.setObject(12, params[11]);
            stmt.setObject(13, params[12]);
            stmt.executeUpdate();

            key.set("contactid", mgr.getLastInsertedId());
            return key;
        } catch (SQLException ex) {
            Log.write().warning(ex.toString());
            return null;
        }
    }

    public boolean update(PrimaryKeyRow key, Object... params) {
        try {
            DbManager mgr = new DbManager();
            PreparedStatement stmt = mgr.getConnection().prepareStatement("update contacts set name=?, siccode=?, commerceregno=?, address=?, region=?, country=?, iban=?, bank=?, socialcapital=?, repname=?, repidtype=?, repidentification=?, repaddress=? WHERE contactid=?");
            stmt.setObject(1, params[0]);
            stmt.setObject(2, params[1]);
            stmt.setObject(3, params[2]);
            stmt.setObject(4, params[3]);
            stmt.setObject(5, params[4]);
            stmt.setObject(6, params[5]);
            stmt.setObject(7, params[6]);
            stmt.setObject(8, params[7]);
            stmt.setObject(9, params[8]);
            stmt.setObject(10, params[9]);
            stmt.setObject(11, params[10]);
            stmt.setObject(12, params[11]);
            stmt.setObject(13, params[12]);
            stmt.setObject(14, key.get("contactid"));
            return stmt.executeUpdate() >= 0;
        } catch (SQLException ex) {
            Log.write().warning(ex.toString());
            return false;
        }
    }

    public boolean delete(PrimaryKeyRow key) {
        try {
            DbManager mgr = new DbManager();
            PreparedStatement stmt = mgr.getConnection().prepareStatement("delete from contacts where contactid=?");
            stmt.setObject(1, key.get("contactid"));
            return stmt.executeUpdate() >= 0;
        } catch (SQLException ex) {
            Log.write().warning(ex.toString());
            return false;
        }
    }

    public boolean clear() {
        try {
            DbManager mgr = new DbManager();
            return mgr.getConnection().createStatement().executeUpdate("delete from contacts;") >= 0;
        } catch (SQLException ex) {
            Log.write().warning(ex.toString());
            return false;
        }
    }

    public boolean populateTableModel(DefaultTableModel model) {
        try {
            DbManager mgr = new DbManager();
            mgr.populateTableModel(model, "select * from contacts;");
            return true;
        } catch (SQLException ex) {
            Log.write().warning(ex.toString());
            return false;
        }
    }

    public boolean populateTableModel(DefaultTableModel model, FilterMode mode, String[] filterExpressions, Object... params) {
        try {
            DbManager mgr = new DbManager();
            mgr.populateTableModel(model, "select * from contacts", mode, filterExpressions, params);
            return true;
        } catch (SQLException ex) {
            Log.write().warning(ex.toString());
            return false;
        }
    }

    public Hashtable<String, Object> get(PrimaryKeyRow key) {
        try {
            DbManager mgr = new DbManager();
            PreparedStatement stmt = mgr.getConnection().prepareStatement("select name, siccode, commerceregno, address, region, country, iban, bank, socialcapital, repname, repidtype, repidentification, repaddress from contacts where contactid=?");
            stmt.setObject(1, key.get("contactid"));

            ResultSet rs = null;
            Hashtable<String, Object> ret = null;
            try {
                rs = stmt.executeQuery();
                if (rs.next()) {
                    ret = new Hashtable<String, Object>();
                    ret.put("name", rs.getString(1));
                    ret.put("siccode", rs.getString(2));
                    ret.put("commerceregno", rs.getString(3));
                    ret.put("address", rs.getString(4));
                    ret.put("region", rs.getString(5));
                    ret.put("country", rs.getString(6));
                    ret.put("iban", rs.getString(7));
                    ret.put("bank", rs.getString(8));
                    ret.put("socialcapital", rs.getString(9));
                    ret.put("repname", rs.getString(10));
                    ret.put("repidtype", rs.getString(11));
                    ret.put("repidentification", rs.getString(12));
                    ret.put("repaddress", rs.getString(13));
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
        return null;
    }

    public boolean populateComboBoxModel(DefaultComboBoxModel model) {
        ResultSet rs = null;
        try {
            model.removeAllElements();
            DbManager mgr = new DbManager();
            rs = mgr.getConnection().createStatement().executeQuery("select contactid, name from contacts;");
            while (rs.next()) {
                model.addElement(new Contact(rs.getInt(1), rs.getString(2)));
            }
            return true;
        } catch (SQLException ex) {
            Log.write().warning(ex.toString());
            return false;
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Log.write().warning(ex.toString());
                return false;
            }
        }
    }

    private class Contact extends PrimaryKeyRow {

        public Contact(int contactId, String contactName) {
            super("contactid", "name");
            set("contactid", contactId);
            set("name", contactName);
        }

        @Override
        public String toString() {
            return get("name").toString();
        }
    }
}

