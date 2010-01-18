/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data;

import com.google.code.reelcash.Log;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;
import javax.swing.table.DefaultTableModel;

/**
 * Provides a database adapter for working with invoice details.
 * @author andrei.olar
 */
public class InvoiceDetailsAdapter implements DbAdapter {

	public boolean clear() {
		try {
			DbManager mgr = new DbManager();
			return mgr.getConnection().createStatement().executeUpdate("delete from invoicedetails;") >= 0;
		}
		catch (SQLException ex) {
			Log.write().warning(ex.toString());
			return false;
		}
	}

	public PrimaryKeyRow create(Object... params) {
		try {
			DbManager mgr = new DbManager();
			PrimaryKeyRow row = new PrimaryKeyRow("invoicedetailid");
			PreparedStatement stmt = mgr.getConnection().prepareStatement(
					"insert into invoicedetails(invoiceid,position,name,unit,quantity,price,vatvalue,value) values(?,?,?,?,?,?,?,?);");
			stmt.setObject(1, params[0]);
			stmt.setObject(2, params[1]);
			stmt.setObject(3, params[2]);
			stmt.setObject(4, params[3]);
			stmt.setObject(5, params[4]);
			stmt.setObject(6, params[5]);

			int qty = (Integer) params[4];
			double price = (Double) params[5];
			stmt.setDouble(7, 0.19 * (qty * price));
			stmt.setDouble(8, 1.19 * (qty * price));

			stmt.executeUpdate();

			row.set("invoicedetailid", mgr.getLastInsertedId());
			return row;
		}
		catch (SQLException ex) {
			Log.write().warning(ex.toString());
			return null;
		}
	}

	public boolean delete(PrimaryKeyRow key) {
		try {
			DbManager mgr = new DbManager();
			PreparedStatement del = mgr.getConnection().prepareStatement("delete from invoicedetails where invoicedetailid=?;");
			del.setObject(1, key.get("invoicedetailid"));

			return del.executeUpdate() >= 0;
		}
		catch (SQLException ex) {
			Log.write().warning(ex.toString());
			return false;
		}
	}

	public Hashtable<String, Object> get(PrimaryKeyRow key) {
		try {
			DbManager mgr = new DbManager();
			PreparedStatement stmt = mgr.getConnection().prepareStatement("select invoicedetailid, invoiceid, position, name,unit,quantity,price,vatvalue,value from invoicedetails where invoicedetailid=?");
			stmt.setObject(1, key.get("invoicedetailid"));

			ResultSet rs = null;
			Hashtable<String, Object> ret = null;
			try {
				rs = stmt.executeQuery();
				if (rs.next()) {
					ret = new Hashtable<String, Object>();
					ret.put("invoicedetailid", rs.getInt(1));
					ret.put("invoiceid", rs.getInt(2));
					ret.put("position", rs.getInt(3));
					ret.put("name", rs.getString(4));
					ret.put("unit", rs.getString(5));
					ret.put("quantity", rs.getInt(6));
					ret.put("price", rs.getDouble(7));
					ret.put("vatvalue", rs.getDouble(7));
					ret.put("value", rs.getDouble(7));
				}
			}
			finally {
				if (null != rs)
					rs.close();
			}
			return ret;
		}
		catch (SQLException ex) {
			Log.write().warning(ex.toString());
			return null;
		}
	}

	public DbAdapter getDetailsAdapter() {
		return null;
	}

	public boolean populateTableModel(DefaultTableModel model) {
		try {
			DbManager mgr = new DbManager();
			mgr.populateTableModel(model, "select invoicedetailid, position, name, unit, quantity, price from invoicedetails;");
			return true;
		}
		catch (SQLException ex) {
			Log.write().warning(ex.toString());
			return false;
		}
	}

	public boolean populateTableModel(DefaultTableModel model, FilterMode mode, String[] filterExpressions, Object... params) {
		try {
			DbManager mgr = new DbManager();
			mgr.populateTableModel(model, "select invoicedetailid, position, name, unit, quantity, price from invoicedetails", mode, filterExpressions, params);
			return true;
		}
		catch (SQLException ex) {
			Log.write().warning(ex.toString());
			return false;
		}
	}

	public boolean update(PrimaryKeyRow key, Object... params) {
		try {
			DbManager mgr = new DbManager();
			PreparedStatement stmt = mgr.getConnection().prepareStatement(
					"update invoicedetails set name=?,unit=?,quantity=?,price=?,vatvalue=?,value=? WHERE invoicedetailid=?");
			stmt.setObject(1, params[0]);
			stmt.setObject(2, params[1]);
			stmt.setObject(3, params[2]);
			stmt.setObject(4, params[3]);
			int qty = (Integer) params[2];
			double price = (Double) params[3];

			stmt.setDouble(5, 0.19 * (qty * price));
			stmt.setDouble(6, 1.19 * (qty * price));
			stmt.setObject(7, key.get("invoicedetailid"));

			return stmt.executeUpdate() >= 0;
		}
		catch (SQLException ex) {
			Log.write().warning(ex.toString());
			return false;
		}
	}

	public Enumeration<PrimaryKeyRow> selectDetails(PrimaryKeyRow masterKey) {
		Set<PrimaryKeyRow> ret = Collections.emptySet();
		try {
			DbManager mgr = new DbManager();
			PreparedStatement stmt = mgr.getConnection().prepareStatement("select distinct invoicedetailid from invoicedetails where invoiceid=?");
			stmt.setObject(1, masterKey.get("invoiceid"));

			ResultSet rs = null;
			try {
				rs = stmt.executeQuery();

				if (rs.next())
					do {
						PrimaryKeyRow detailKey = new PrimaryKeyRow("invoicedetailid");
						detailKey.set("invoicedetailid", rs.getInt(1));
						ret.add(masterKey);
					} while (rs.next());
			}
			finally {
				if (null != rs)
					rs.close();
			}
		}
		catch (SQLException ex) {
			Log.write().warning(ex.toString());
		}
		return Collections.enumeration(ret);
	}

	public boolean deleteAll(PrimaryKeyRow masterKey) {
		try {
			DbManager mgr = new DbManager();
			PreparedStatement del = mgr.getConnection().prepareStatement("delete from invoicedetails where invoiceid=?;");
			del.setObject(1, masterKey.get("invoiceid"));

			return del.executeUpdate() >= 0;
		}
		catch (SQLException ex) {
			Log.write().warning(ex.toString());
			return false;
		}
	}
}
