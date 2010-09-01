package com.google.code.reelcash.model;

import com.google.code.reelcash.ReelcashException;
import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.sql.QueryMediator;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author andrei.olar
 */
public class DataRowTableModelDatabaseAdapter implements TableModelListener {

    private final QueryMediator mediator;
    private final DataRowTableModel monitored;
    private final DataLayoutNode node;

    /**
     * Creates a new database adapter for the specified table model. It uses a data source.
     *
     * @param dataSource the data source to use.
     * @param monitored the monitored table model.
     */
    public DataRowTableModelDatabaseAdapter(final DataSource dataSource, final DataRowTableModel monitored) {
        mediator = new QueryMediator(dataSource);
        this.monitored = monitored;
        this.monitored.addTableModelListener(this);
        this.node = this.monitored.getLayoutNode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final DataRowTableModelDatabaseAdapter other = (DataRowTableModelDatabaseAdapter) obj;
        if (this.monitored != other.monitored && (this.monitored == null || !this.monitored.equals(other.monitored)))
            return false;
        if (this.node != other.node && (this.node == null || !this.node.equals(other.node)))
            return false;
        return true;
    }

    @Override
    protected void finalize() throws Throwable {
        monitored.removeTableModelListener(this);
        super.finalize();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.monitored != null ? this.monitored.hashCode() : 0);
        hash = 97 * hash + (this.node != null ? this.node.hashCode() : 0);
        return hash;
    }

    private void onRowInserted(DataRow row) {
        try {
            mediator.createRow(node.getName(), row);
        }
        catch (SQLException exc) {
            throw new ReelcashException(Resources.getString("insert_failed"), exc);
        }
    }

    private void onRowUpdated(DataRow row) {
        try {
            mediator.updateRow(node, row);
        }
        catch (SQLException exc) {
            throw new ReelcashException(Resources.getString("update_failed"), exc);
        }
    }

    private void onRowDeleted(DataRow row) {
        try {
            mediator.delete(node, row);
        }
        catch (SQLException exc) {
            throw new ReelcashException(Resources.getString("delete_failed"), exc);
        }
    }

    /**
     * Retrieves all the data from the database into the table model, refreshing its cache.
     */
    public void readAll() {
        try {
            monitored.removeTableModelListener(this);
            monitored.fill(mediator.fetchAll(node));
            monitored.addTableModelListener(this);
        }
        catch (SQLException exc) {
            throw new ReelcashException(Resources.getString("select_failed"), exc);
        }
    }

    /**
     * Call this method with the appropriate arguments to perform an insert/update/delete in the database.
     * 
     * @param e arguments to the appropriate action to take upon the database.
     */
    public void tableChanged(TableModelEvent e) {
        DataRow changed = monitored.getRow(e.getFirstRow());
        switch (e.getType()) {
            case TableModelEvent.INSERT:
                onRowInserted(changed);
                break;
            case TableModelEvent.UPDATE:
                onRowUpdated(changed);
                break;
            case TableModelEvent.DELETE:
                onRowDeleted(changed);
                break;
        }
    }
}
