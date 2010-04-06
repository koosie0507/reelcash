/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data.sql;

import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.util.SysUtils;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
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
        Iterator<Field> f = layoutNode.getFieldSet().iterator();
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

            if (!layoutNode.getFieldSet().getPrimary().isEmpty()) {
                sql.append(", ");
                sql.append(SysUtils.getNewLine());
                Iterator<Field> p = layoutNode.getFieldSet().getPrimary().iterator();
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
}
