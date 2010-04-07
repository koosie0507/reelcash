/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data.sql;

import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.layout.fields.FieldList;
import com.google.code.reelcash.util.SysUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
     * @param tableName the name of the table in which we may insert the new data row.
     * @param input the values which should be inserted.
     *
     * @return true upon successful completion of the statement(s).
     * @throws SQLException 
     */
    public boolean createRow(String tableName, DataRow input) throws SQLException {
        StringBuilder sql = new StringBuilder("INSERT INTO `");
        sql.append(tableName);
        Iterator<String> name = input.iterator();
        if (!name.hasNext())
            return false;

        sql.append("` (`");
        sql.append(name.next());
        sql.append("`");
        while (name.hasNext()) {
            sql.append(", `");
            sql.append(name.next());
            sql.append("`");
        }

        sql.append(") VALUES (?");
        for (int i = input.getFields().size(); i > 1; i--) {
            sql.append(",?");
        }
        sql.append(");");
        PreparedStatement insert = dataSource.getConnection().prepareStatement(sql.toString());
        int idx = 1;

        for (Field field : input.getFields()) {
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
     * @param layoutNode the node specifying the name of the database table
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
     * Returns a list of data rows from the database by using the information provided
     * in the layout node.
     *
     * @param layoutNode a data layout node containing the list of fields and table name.
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
            TryCloseResultSet(rs);
        }
        return rows;
    }

    private static void TryCloseResultSet(ResultSet rs) throws SQLException {
        if (null == rs)
            return;
        rs.close();
    }
}
