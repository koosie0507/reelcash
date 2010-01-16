/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data;

import java.sql.*;
import java.util.Hashtable;

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
        Hashtable<String, Object> ret =null;
        if(rs.next()) {
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
        return ret;
    }
}
