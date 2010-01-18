/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data;

import com.google.code.reelcash.Log;
import com.google.code.reelcash.util.SysUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cusi
 */
public class DbManager {

	private static final String defaultDbName = "reelcash.db";
	private static final String defaultJdbcUrl;
	private static final String defaultDbFilePath;

	static {
		defaultDbFilePath = SysUtils.getAppHome().concat(defaultDbName);
		defaultJdbcUrl = "jdbc:sqlite:".concat(defaultDbFilePath);
	}
	private Connection connection;

	/**
	 * Creates a new constructor with a default jdbc url.
	 *
	 * @throws SQLException if we can't get a connection to the database or if the database is not initialized.
	 */
	public DbManager() throws SQLException {
		connection = DriverManager.getConnection(defaultJdbcUrl);
	}

	@Override
	protected void finalize() {
		try {
			if (null != connection)
				connection.close();
		}
		catch (SQLException ex) {
			Log.write().log(Level.SEVERE, "Can't close connection to database!", ex);
		}
	}

	private static boolean checkDb() {
		File file = new File(defaultDbFilePath);
		if (!file.exists())
			return false;

		try {
			Connection checkConnection = DriverManager.getConnection(defaultJdbcUrl);
			ResultSet rs = null;
			try {
				rs = checkConnection.createStatement().executeQuery("select count(distinct name) from sqlite_master where type=\"table\";");
				if (!rs.next())
					return false;
				return rs.getInt(1) > 0;
			}
			finally {
				if (null != rs)
					rs.close();
				checkConnection.close();
			}
		}
		catch (SQLException ex) {
			Log.write().log(Level.WARNING, "Can't read database structure!", ex);
			return false;
		}
	}

	public static boolean checkCreateDb() throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
		}
		catch (ClassNotFoundException ex) {
			Log.write().log(Level.SEVERE, "SQLite JDBC driver is not installer correctly!", ex);
			return false;
		}

		if (checkDb())
			return true;

		String sql = null;
		try {
			InputStream stream = DbManager.class.getResourceAsStream("database.sql");
			InputStreamReader streamReader = new InputStreamReader(stream);
			BufferedReader reader = new BufferedReader(streamReader);
			StringBuilder builder = new StringBuilder();
			String line = null;
			while (null != (line = reader.readLine())) {
				builder.append(line);
				builder.append(SysUtils.getNewLine());
			}

			reader.close();
			streamReader.close();
			stream.close();

			sql = builder.toString();
		}
		catch (IOException ex) {
			Log.write().log(Level.SEVERE, "Can't read database script!", ex);
			return false;
		}

		Connection createConnection = DriverManager.getConnection(defaultJdbcUrl);
		Statement createDb = createConnection.createStatement();
		StringTokenizer tokenizer = new StringTokenizer(sql, ";");
		while (tokenizer.hasMoreTokens()) {
			String sqlPart = tokenizer.nextToken().trim();
			if (0 < sqlPart.length())
				createDb.addBatch(sqlPart);
		}
		createConnection.setAutoCommit(false);
		boolean ret = createDb.executeBatch().length > 0;
		createConnection.setAutoCommit(true);
		createConnection.close();
		return ret;
	}

	public Connection getConnection() {
		return connection;
	}

	void populateTableModel(DefaultTableModel model, String query) throws SQLException {
		ResultSet contacts = null;
		try {
			contacts = connection.createStatement().executeQuery(query);
			ResultSetMetaData meta = contacts.getMetaData();
			int arraySize = Math.min(meta.getColumnCount(), model.getColumnCount());
			while (contacts.next()) {
				Object[] rowData = new Object[arraySize];
				for (int col = 0; col < rowData.length; col++) {
					rowData[col] = contacts.getObject(col + 1);
				}
				model.addRow(rowData);
			}
		}
		finally {
			if (null != contacts)
				contacts.close();
		}
	}

	void populateTableModel(DefaultTableModel model, String query, FilterMode mode, String[] expressions, Object[] params) throws SQLException {
		ResultSet contacts = null;
		try {
			model.setRowCount(0);
			String filteredQuery = query.concat(" WHERE ").concat(createCompoundCondition(mode, expressions));
			PreparedStatement stmt = connection.prepareStatement(filteredQuery);
			for (int i = 0; i < params.length; i++)
				stmt.setObject(i + 1, params[i]);

			contacts = stmt.executeQuery();
			ResultSetMetaData meta = contacts.getMetaData();
			int arraySize = Math.min(meta.getColumnCount(), model.getColumnCount());
			while (contacts.next()) {
				Object[] rowData = new Object[arraySize];
				for (int col = 0; col < rowData.length; col++) {
					rowData[col] = contacts.getObject(col + 1);
				}
				model.addRow(rowData);
			}
		}
		finally {
			if (null != contacts)
				contacts.close();
		}
	}

	public Hashtable<String, Object> getContact(int contactId) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("select name, siccode, commerceregno, address, region, country, iban, bank, socialcapital, repname, repidtype, repidentification, repaddress from contacts where contactid=?");
		stmt.setInt(1, contactId);

		ResultSet rs = stmt.executeQuery();
		Hashtable<String, Object> ret = null;
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
		rs.close();
		return ret;
	}

	public void populateInvoiceModel(DefaultTableModel model) throws SQLException {
		if (null == model)
			return;
		model.setRowCount(0);
		PreparedStatement stmt = connection.prepareStatement("select invoiceid, number, series, invoicedate, duedate, customer from invoices");

		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			model.addRow(new Object[]{rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDate(4), rs.getDate(5), rs.getString(6)});
		}
		rs.close();
	}

	public boolean insertInvoice(int number, String series, java.sql.Date invoiceDate,
			java.sql.Date duedate, String customer, String billingaddress,
			String region, String country, String siccode, String commerceregno,
			String iban, String bank) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("insert into invoices(number, series, invoicedate, duedate, customer, billingaddress, region, country, siccode, commerceregno, iban, bank) values(?,?,?,?,?,?,?,?,?,?,?,?)");

		stmt.setInt(1, number);
		stmt.setString(2, series);
		stmt.setDate(3, invoiceDate);
		stmt.setDate(4, duedate);
		stmt.setString(5, customer);
		stmt.setString(6, billingaddress);
		stmt.setString(7, region);
		stmt.setString(8, country);
		stmt.setString(9, siccode);
		stmt.setString(10, commerceregno);
		stmt.setString(11, iban);
		stmt.setString(12, bank);

		return stmt.executeUpdate() > -1;
	}

	public boolean insertInvoiceDetail(int invoiceId, int position, String name,
			String unit, int quantity, double price) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("insert into invoicedetails(invoiceid,position,name,unit,quantity,price,vatvalue,value) values(?,?,?,?,?,?,?,?)");

		stmt.setInt(1, invoiceId);
		stmt.setInt(2, position);
		stmt.setString(3, name);
		stmt.setString(4, unit);
		stmt.setInt(5, quantity);
		stmt.setDouble(6, price);
		stmt.setDouble(7, 0.19 * price * quantity);
		stmt.setDouble(8, 1.19 * price * quantity);

		return stmt.executeUpdate() > -1;
	}

	public int getLastInsertedId() throws SQLException {
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid();");
		try {
			if (rs.next())
				return rs.getInt(1);
			return 0;
		}
		finally {
			rs.close();
		}
	}

	public boolean updateInvoice(int number, String series, java.sql.Date invoiceDate,
			java.sql.Date duedate, String customer, String billingaddress,
			String region, String country, String siccode, String commerceregno,
			String iban, String bank) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("insert into invoices(number, series, invoicedate, duedate, customer, billingaddress, region, country, siccode, commerceregno, iban, bank) values(?,?,?,?,?,?,?,?,?,?,?,?)");

		stmt.setInt(1, number);
		stmt.setString(2, series);
		stmt.setDate(3, invoiceDate);
		stmt.setDate(4, duedate);
		stmt.setString(5, customer);
		stmt.setString(6, billingaddress);
		stmt.setString(7, region);
		stmt.setString(8, country);
		stmt.setString(9, siccode);
		stmt.setString(10, commerceregno);
		stmt.setString(11, iban);
		stmt.setString(12, bank);

		return stmt.execute();
	}

	public int getInvoiceId(int invoiceNumber) throws SQLException {
		PreparedStatement s = connection.prepareStatement("select invoiceid from invoices where number=?");
		s.setInt(1, invoiceNumber);
		ResultSet rs = s.executeQuery();
		int ret;
		if (rs.next())
			ret = rs.getInt(1);
		else
			ret = -1;
		rs.close();
		return ret;
	}

	String createCompoundCondition(FilterMode mode, String... expressions) {
		if (null == expressions || expressions.length < 1)
			return "";
		StringBuilder exp = new StringBuilder(" (");
		exp.append(expressions[0]);
		exp.append(")");
		int idx = 1;
		while (idx < expressions.length) {
			exp.append(" AND (");
			exp.append(expressions[idx]);
			exp.append(")");
			idx++;
		}
		return exp.toString();
	}
}
