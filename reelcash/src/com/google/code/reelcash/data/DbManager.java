/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Hashtable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cusi
 */
public class DbManager {

    private Connection connection;
    private static final String defaultJdbcUrl = "jdbc:sqlite:/home/cusi/reelcash.db";

    /**
     * Creates a new constructor with a default jdbc url.
     */
    public DbManager() throws SQLException {
        connection = DriverManager.getConnection(defaultJdbcUrl);
    }

    public DbManager(String jdbcUrl) throws SQLException {
        connection = DriverManager.getConnection(jdbcUrl);
    }

    public Connection getConnection() {
        return connection;
    }

    public void createDatabase() throws SQLException, IOException {
        Statement create = connection.createStatement();
        InputStream stream = getClass().getResourceAsStream("database.sql");
        InputStreamReader streamReader = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(streamReader);
        StringBuilder builder = new StringBuilder();
        String line = null;
        while (null != (line = reader.readLine())) {
            builder.append(line);
        }

        reader.close();
        streamReader.close();
        stream.close();

        create.addBatch(builder.toString());
        create.executeBatch();
    }

    public boolean insertContact(String name, String sicCode, String orc,
            String address, String region, String country, String iban,
            String bank, String socialCapital, String repName, String repIdType,
            String repIdentification, String repAddress) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO contacts(name, siccode, commerceregno, address, region, country, iban, bank, socialcapital, repname, repidtype, repidentification, repaddress) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        stmt.setString(1, name);
        stmt.setString(2, sicCode);
        stmt.setString(3, orc);
        stmt.setString(4, address);
        stmt.setString(5, region);
        stmt.setString(6, country);
        stmt.setString(7, iban);
        stmt.setString(8, bank);
        stmt.setString(9, socialCapital);
        stmt.setString(10, repName);
        stmt.setString(11, repIdType);
        stmt.setString(12, repIdentification);
        stmt.setString(13, repAddress);

        return stmt.execute();
    }

    public boolean updateContact(int contactId, String name, String sicCode, String orc,
            String address, String region, String country, String iban,
            String bank, String socialCapital, String repName, String repIdType,
            String repIdentification, String repAddress) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement("update contacts set name=?, siccode=?, commerceregno=?, address=?, region=?, country=?, iban=?, bank=?, socialcapital=?, repname=?, repidtype=?, repidentification=?, repaddress=? WHERE contactid=?");
        stmt.setString(1, name);
        stmt.setString(2, sicCode);
        stmt.setString(3, orc);
        stmt.setString(4, address);
        stmt.setString(5, region);
        stmt.setString(6, country);
        stmt.setString(7, iban);
        stmt.setString(8, bank);
        stmt.setString(9, socialCapital);
        stmt.setString(10, repName);
        stmt.setString(11, repIdType);
        stmt.setString(12, repIdentification);
        stmt.setString(13, repAddress);
        stmt.setInt(14, contactId);

        return stmt.execute();
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
        if (null == model) {
            return;
        }
        model.setRowCount(0);
        PreparedStatement stmt = connection.prepareStatement("select number, series, invoicedate, duedate, customer from invoices");

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            model.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getString(5)});
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
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } finally {
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
        if(rs.next()) ret = rs.getInt(1);
        else ret = -1;
        rs.close();
        return ret;
    }
}
