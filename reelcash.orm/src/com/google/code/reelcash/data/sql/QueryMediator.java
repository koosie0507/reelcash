/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data.sql;

import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.BigDecimalField;
import com.google.code.reelcash.data.layout.fields.BooleanField;
import com.google.code.reelcash.data.layout.fields.DateField;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.layout.fields.FieldList;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.StringField;
import com.google.code.reelcash.util.SysUtils;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.sql.DataSource;

/**
 * Converts results to objects or data rows. Provides accessibility methods for quering the
 * SQLite back-end.
 * 
 * @author andrei.olar
 */
public class QueryMediator {

    private final DataSource dataSource;

    /**
     * Creates a query mediator using the supplied data source for connection information.
     *
     * @param dataSource the data source containing the connection information for this
     * query mediator.
     * 
     */
    public QueryMediator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Creates a table in an SQLite database based on the supplied information.
     *
     * @param layoutNode the node to perform the operation for.
     * @return true if the table has been created.
     *
     * @throws java.sql.SQLException if errors occur.
     */
    public boolean createTable(DataLayoutNode layoutNode) throws java.sql.SQLException {
        StringBuilder sql = new StringBuilder("CREATE TABLE `");
        sql.append(layoutNode.getName());
        sql.append("` ( ");
        sql.append(SysUtils.getNewLine());
        Iterator<Field> f = layoutNode.getFieldList().iterator();
        if (!f.hasNext())
            sql.append(");");
        else {
            Field field = f.next();
            SqlFieldRenderer renderer = SqlFieldRenderer.newInstance(field);
            sql.append(renderer.renderName());
            sql.append(" ");
            sql.append(renderer.renderType());
            sql.append(" ");
            sql.append(renderer.renderNull());

            while (f.hasNext()) {
                field = f.next();
                sql.append(", ");
                sql.append(SysUtils.getNewLine());

                renderer = SqlFieldRenderer.newInstance(field);
                sql.append(renderer.renderName());
                sql.append(" ");
                sql.append(renderer.renderType());
                sql.append(" ");
                sql.append(renderer.renderNull());
            }

            if (!layoutNode.getFieldList().getPrimary().isEmpty()) {
                sql.append(", ");
                sql.append(SysUtils.getNewLine());
                Iterator<Field> p = layoutNode.getFieldList().getPrimary().iterator();
                sql.append("CONSTRAINT pk_");
                sql.append(layoutNode.getName());
                sql.append(" PRIMARY KEY (`");
                sql.append(p.next().getName());
                sql.append("`");
                while (p.hasNext()) {
                    sql.append(", `");
                    sql.append(p.next().getName());
                    sql.append("`");
                }
                sql.append(")");
                sql.append(SysUtils.getNewLine());
                sql.append(");");
            }
        }

        return dataSource.getConnection().createStatement().execute(sql.toString());
    }

    /**
     * Creates a new table row in the SQLite database based on the provided data.
     *
     * @param tableName the ifieldcurrentField of the table in which we may insert the new data row.
     * @param input the values which should be inserted.
     *
     * @return true upon successful completion of the statement(s).
     * @throws SQLException
     */
    public boolean createRow(String tableName, DataRow input) throws SQLException {
        return createRow(tableName, input, true);
    }

    /**
     * Creates a new table row in the SQLite database based on the provided data.
     *
     * @param tableName the ifieldcurrentField of the table in which we may insert the new data row.
     * @param input the values which should be inserted.
     * @param noPk if this is true, the primary key will be left out of the insert statement.
     * 
     * @return true upon successful completion of the statement(s).
     * @throws SQLException 
     */
    public boolean createRow(String tableName, DataRow input, boolean noPk) throws SQLException {
        StringBuilder sql = new StringBuilder("INSERT INTO `");
        sql.append(tableName);
        FieldList fields = new FieldList();
        fields.addAll(input.getFields());
        if (noPk)
            fields.removeAll(fields.getPrimary());

        Iterator<Field> ifieldcurrentField = fields.iterator();
        if (!ifieldcurrentField.hasNext())
            return false;

        sql.append("` (`");
        sql.append(ifieldcurrentField.next().getName());
        sql.append("`");
        while (ifieldcurrentField.hasNext()) {
            sql.append(", `");
            sql.append(ifieldcurrentField.next().getName());
            sql.append("`");
        }

        sql.append(") VALUES (?");
        for (int i = fields.size(); i > 1; i--) {
            sql.append(",?");
        }
        sql.append(");");
        PreparedStatement insert = dataSource.getConnection().prepareStatement(sql.toString());
        int idx = 1;

        for (Field field : fields) {
            insert.setObject(idx, input.getValue(field.getName()));
            idx++;
        }

        return insert.execute();
    }

    /**
     * Deletes a table row from the database.
     *
     * @param layoutNode node describing the table
     * @param deleted the row which should be deleted.
     * @return true, if the row was deleted.
     *
     * @throws SQLException on database error.
     */
    public boolean delete(DataLayoutNode layoutNode, DataRow deleted) throws SQLException {
        List<Field> primary = layoutNode.getFieldList().getPrimary();
        Iterator<Field> p = primary.iterator();
        if (!p.hasNext())
            throw new SQLException("no_pk_error");

        StringBuilder sql = new StringBuilder("DELETE FROM `");
        sql.append(layoutNode.getName());
        sql.append("` WHERE (");
        sql.append(SqlFieldRenderer.newInstance(p.next()).renderName());
        sql.append(" = ?)");
        while (p.hasNext()) {
            sql.append("` AND (");
            sql.append(SqlFieldRenderer.newInstance(p.next()).renderName());
            sql.append(" = ?)");
        }

        PreparedStatement delete = dataSource.getConnection().prepareStatement(sql.toString());
        int paramIdx = 1;
        for (p = primary.iterator(); p.hasNext();) {
            int idx = layoutNode.getFieldList().indexOf(p.next());
            delete.setObject(paramIdx, deleted.getValue(idx));
            paramIdx++;
        }

        return delete.execute();
    }

    /**
     * Updates the database table specified by the layout node.
     *
     * @param layoutNode the node specifying the ifieldcurrentField of the database table
     * @param modified the row which was modified.
     * @return true, if no errors occur.
     *
     * @throws SQLException if database errors occur or if the layout node does not define a primary key or the
     * primary key is comprised of all fields.
     */
    public boolean updateRow(DataLayoutNode layoutNode, DataRow modified) throws SQLException {
        List<Field> primaryKey = layoutNode.getFieldList().getPrimary();
        List<Field> normal = new ArrayList<Field>(layoutNode.getFieldList());
        normal.removeAll(primaryKey);
        Iterator<Field> p = primaryKey.iterator();
        Iterator<Field> n = normal.iterator();
        if (!p.hasNext())
            throw new SQLException(Resources.getString("no_pk_error"));

        if (!n.hasNext())
            throw new SQLException(Resources.getString("all_pk_error"));

        StringBuilder sql = new StringBuilder("UPDATE `");
        sql.append(layoutNode.getName());
        sql.append("` SET ");
        sql.append(SysUtils.getNewLine());
        sql.append(SqlFieldRenderer.newInstance(n.next()).renderName());
        sql.append(" = ?");
        while (n.hasNext()) {
            sql.append(", ");
            sql.append(SqlFieldRenderer.newInstance(n.next()).renderName());
            sql.append(" = ?");
        }
        sql.append(" WHERE (");
        sql.append(SqlFieldRenderer.newInstance(p.next()).renderName());
        sql.append(" = ?)");
        while (p.hasNext()) {
            sql.append(" AND (");
            sql.append(SqlFieldRenderer.newInstance(p.next()).renderName());
            sql.append(" = ?)");
        }

        PreparedStatement update = dataSource.getConnection().prepareStatement(sql.toString());
        int paramIdx = 1;
        for (Iterator<Field> i = normal.iterator(); i.hasNext();) {
            int idx = layoutNode.getFieldList().indexOf(i.next());
            update.setObject(paramIdx, modified.getValue(idx));
            paramIdx++;
        }
        for (Iterator<Field> i = primaryKey.iterator(); i.hasNext();) {
            int idx = layoutNode.getFieldList().indexOf(i.next());
            update.setObject(paramIdx, modified.getValue(idx));
            paramIdx++;
        }
        return update.execute();
    }

    /**
     * Executes the given sql statement as if it were an update statement.
     * @param sql an sql statement
     * @return the number of affected records in the DB.
     * @throws SQLException if something goes wrong
     */
    public int execute(String sql) throws SQLException {
        Statement exec = dataSource.getConnection().createStatement();
        return exec.executeUpdate(sql);
    }

    /**
     * Executes the given sql statement as if it were an update statement.
     * @param sql an sql statement
     * @param params sql statement params
     *
     * @return the number of affected records in the DB.
     * @throws SQLException if something goes wrong
     */
    public int execute(String sql, Object... params) throws SQLException {
        PreparedStatement exec = dataSource.getConnection().prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            exec.setObject(i + 1, params[i]);
        }
        return exec.executeUpdate();
    }

    public Object executeScalar(String sql) throws SQLException {
        Statement select = dataSource.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = null;
        try {
            rs = select.executeQuery(sql);
            if (!rs.next())
                return null;
            return rs.getObject(1);
        }
        finally {
            tryCloseResultSet(rs);
        }
    }

    public Object executeScalar(String sql, Object... params) throws SQLException {
        PreparedStatement select = dataSource.getConnection().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = null;
        try {
            for (int i = params.length; i > 0; i--) {
                select.setObject(i, params[i - 1]);
            }
            rs = select.executeQuery();
            if (!rs.next())
                return null;
            return rs.getObject(1);
        }
        finally {
            tryCloseResultSet(rs);
        }
    }

    /**
     * Fetches data from the database with a simple (non-parameterized) SQL statement.
     * @param sql the SQL statement used for fetching data
     * @return an array of data rows which will point to a field structure deduced from
     * the query result meta-data.
     * @throws SQLException if certain errors occur
     */
    public DataRow[] fetchSimple(final String sql) throws SQLException {
        Statement select = dataSource.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = null;
        ArrayList<DataRow> rows = new ArrayList<DataRow>();
        try {
            rs = select.executeQuery(sql);
            ResultSetMetaData meta = rs.getMetaData();
            FieldList fields = new FieldList();
            initializeFieldList(meta, fields);
            int colCount = meta.getColumnCount();

            while (rs.next()) {
                DataRow row = new DataRow(fields);
                for (int i = 1; i <= colCount; i++) {
                    int idx = fields.indexOf(meta.getColumnName(i));
                    if (idx < 0)
                        continue;
                    row.setValue(idx, rs.getObject(i));
                }
                rows.add(row);
            }
        }
        finally {
            tryCloseResultSet(rs);
        }
        DataRow[] array = new DataRow[rows.size()];
        rows.toArray(array);
        rows = null;
        return array;
    }

    /**
     * Fetches data using a parameterized query.
     *
     * @param sql query
     * @param params the parameters of the query.
     *
     * @return returns an array of data rows.
     *
     * @throws SQLException when errors occur at the SQL level.
     */
    public DataRow[] fetch(String sql, Object... params) throws SQLException {
        PreparedStatement select = dataSource.getConnection().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = null;
        ArrayList<DataRow> rows = new ArrayList<DataRow>();
        try {
            for (int i = params.length; i > 0; i--) {
                select.setObject(i, params[i - 1]);
            }
            rs = select.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            FieldList fields = new FieldList();
            initializeFieldList(meta, fields);
            int colCount = meta.getColumnCount();

            while (rs.next()) {
                DataRow row = new DataRow(fields);
                for (int i = 1; i <= colCount; i++) {
                    int idx = fields.indexOf(meta.getColumnName(i));
                    if (idx < 0)
                        continue;
                    row.setValue(idx, rs.getObject(i));
                }
                rows.add(row);
            }
        }
        finally {
            tryCloseResultSet(rs);
        }

        DataRow[] array = new DataRow[rows.size()];
        rows.toArray(array);
        rows = null;
        return array;
    }

    /**
     * Returns a list of data rows from the database by using the information provided
     * in the layout node.
     *
     * @param layoutNode a data layout node containing the list of fields and table ifieldcurrentField.
     *
     * @return a list of data rows.
     * @throws SQLException when the layout node doesn't define fields or when a database
     * error occurs.
     */
    public List<DataRow> fetchAll(DataLayoutNode layoutNode) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT ");
        Iterator<Field> f = layoutNode.iterator();
        if (!f.hasNext())
            throw new SQLException(Resources.getString("expected_select_fields_errornull"));

        SqlFieldRenderer renderer = SqlFieldRenderer.newInstance(f.next());
        sql.append(renderer.renderName());
        while (f.hasNext()) {
            renderer = SqlFieldRenderer.newInstance(f.next());
            sql.append(", ");
            sql.append(renderer.renderName());
        }
        sql.append(" FROM `");
        sql.append(layoutNode.getName());
        sql.append("`;");

        Statement select = dataSource.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = null;
        List<DataRow> rows = new ArrayList<DataRow>();
        try {
            rs = select.executeQuery(sql.toString());
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                DataRow row = new DataRow(layoutNode.getFieldList());
                for (int i = 0; i < columnCount; i++) {
                    row.setValue(i, rs.getObject(i + 1));
                }
                rows.add(row);
            }
        }
        finally {
            tryCloseResultSet(rs);
        }
        return rows;
    }

    private static void tryCloseResultSet(ResultSet rs) throws SQLException {
        if (null == rs)
            return;
        rs.close();
    }

    private static void initializeFieldList(final ResultSetMetaData meta, final FieldList fields) throws SQLException {
        int colCount = meta.getColumnCount();
        for (int i = 1; i <= colCount; i++) {
            Field added = null;
            switch (meta.getColumnType(i)) {
                case Types.BIT:
                    added = new BooleanField(meta.getColumnName(i), KeyRole.NONE, false);
                    break;
                case Types.BIGINT:
                case Types.INTEGER:
                case Types.TINYINT:
                    added = new IntegerField(meta.getColumnName(i), KeyRole.NONE, false);
                    break;
                case Types.DECIMAL:
                case Types.DOUBLE:
                case Types.FLOAT:
                    added = new BigDecimalField(meta.getColumnName(i), KeyRole.NONE, false);
                    break;
                case Types.DATE:
                case Types.TIME:
                case Types.TIMESTAMP:
                    added = new DateField(meta.getColumnName(i), KeyRole.NONE, false);
                    break;
                case Types.CHAR:
                case Types.VARCHAR:
                case Types.NVARCHAR:
                case Types.LONGNVARCHAR:
                case Types.LONGVARCHAR:
                case Types.NCHAR:
                case Types.OTHER:
                    added = new StringField(meta.getColumnName(i), KeyRole.NONE, false);
            }
            if (null == added || fields.contains(added))
                continue;
            fields.add(added);
        }
    }
}
