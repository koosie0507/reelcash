/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data;

import com.google.code.reelcash.ReelcashException;
import com.google.code.reelcash.util.SysUtils;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Implements the {@link javax.sql.DataSource} interface. Provides the necessary
 * information for connecting to the ReelCash database.
 *
 * @author cusi
 */
public class ReelcashDataSource implements javax.sql.DataSource {

    private static final String SQLITE_JDBC_DRIVER = "org.sqlite.jdbc";
    private static final String defaultDbName = "reelcash.db";
    private static final String defaultJdbcUrl;
    private static final String defaultDbFilePath;
    private Connection connection;
    private PrintWriter writer;
    private int loginTimeout;

    static {
        defaultDbFilePath = SysUtils.getAppHome().concat(defaultDbName);
        defaultJdbcUrl = "jdbc:sqlite:".concat(defaultDbFilePath);
        try {
            Class.forName(SQLITE_JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            throw new ReelcashException(ex);
        }
    }

    {
        loginTimeout = 0;
    }

    /**
     * Gets a new connection to the ReelCash database.
     *
     * @return a new connection to the ReelCash database.
     *
     * @throws SQLException if an error occurs.
     */
    public Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(defaultJdbcUrl);
        return connection;
    }

    /**
     * Opens a new connection to the ReelCash database.
     *
     * @param username name of user to use - ignored.
     * @param password password - ignored.
     *
     * @return an open connection to the ReelCash database.
     *
     * @throws SQLException if no connection to the DB can be made.
     */
    public Connection getConnection(String username, String password) throws SQLException {
        connection = DriverManager.getConnection(defaultJdbcUrl, username, password);
        return connection;
    }

    /**
     * Returns a preferred log writer. Defaults to {@link System.err}.
     * @return a {@link java.io.PrintWriter} instance.
     * @throws SQLException is never thrown.
     */
    public PrintWriter getLogWriter() throws SQLException {
        if (null == writer) {
            writer = new PrintWriter(System.err);
        }
        return writer;
    }

    /**
     * Sets the log writer to the given instance.
     * @param out the instace.
     * @throws SQLException never thrown.
     */
    public void setLogWriter(PrintWriter out) throws SQLException {
        writer = out;
    }

    /**
     * Sets the login timeout. This is ignored.
     * @param seconds login timeout in seconds.
     * @throws SQLException never thrown.
     */
    public void setLoginTimeout(int seconds) throws SQLException {
        loginTimeout = seconds;
    }

    /**
     * Gets the login timeout in seconds. Ignore this value, as SQLite
     * doesn't support it.
     * @return the login timeout in seconds. Should be ignored.
     * @throws SQLException never thrown.
     */
    public int getLoginTimeout() throws SQLException {
        return loginTimeout;
    }

    /**
     * Don't use this.
     * @param <T> not used.
     * @param iface not used.
     * @return nothing. Always throws an unsupported operation exception.
     * @throws SQLException never
     */
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * This class is not a wrapper.
     * @param iface interface - not used.
     * @return false.
     * @throws SQLException never.
     */
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
