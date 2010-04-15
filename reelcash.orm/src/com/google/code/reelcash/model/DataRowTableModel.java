/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.model;

import com.google.code.reelcash.ReelcashException;
import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.table.AbstractTableModel;

/**
 * <p>Implements the abstract table model</p>
 * @author andrei.olar
 */
public class DataRowTableModel extends AbstractTableModel {

    private static final long serialVersionUID = -6916050574654809891L;
    private final DataLayoutNode node;
    private final ArrayList<DataRow> dataRows;

    /**
     * Creates a new table model from the given data source and the given layout node.
     *
     * @param node the layout node which provides the layout.
     */
    public DataRowTableModel(DataLayoutNode node) {
        this.node = node;
        dataRows = new ArrayList<DataRow>();
    }

    public DataRow add() {
        int rowCount = dataRows.size();
        DataRow row = new DataRow(node.getFieldList());
        dataRows.add(row);
        fireTableRowsInserted(rowCount, rowCount);
        return row;
    }

    public void add(DataRow row) {
        int rowCount = dataRows.size();
        try {
            dataRows.add(row);
            fireTableRowsInserted(rowCount, rowCount);
        }
        catch (ReelcashException t) {
            dataRows.remove(row);
            throw t;
        }
    }

    /**
     * Clears the data from the model without notifying any listeners.
     */
    public void clearQuietly() {
        dataRows.clear();
    }

    /**
     * Clears the data from the table and notifies the model's listeners.
     */
    public void clear() {
        dataRows.clear();
        fireTableDataChanged();
    }

    public void delete(int rowIndex) {
        fireTableRowsDeleted(rowIndex, rowIndex);
        dataRows.remove(rowIndex);
    }

    /**
     * Returns the number of rows in the current model.
     *
     * @return the number of rows in the current model.
     */
    public int getRowCount() {

        return dataRows.size();
    }

    /**
     * Returns the number of columns in the current model.
     *
     * @return number of columns in the current model.
     */
    public int getColumnCount() {
        return node.getFieldList().size();
    }

    /**
     * Returns the layout node which provides the layout information for the table model. 
     * 
     * @return the layout node.
     */
    public DataLayoutNode getLayoutNode() {
        return node;
    }

    /**
     * Returns the value located at the given row and column.
     *
     * @param rowIndex the index of the row.
     * @param columnIndex the index of the column
     *
     * @return the value which should go into the cell specified by the row and column.
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        return dataRows.get(rowIndex).getValue(columnIndex);
    }

    /**
     * Fills the model with data from the collection. Also, fires the
     * table data changed event.
     *
     * @param rows the new data of the table.
     */
    void fill(Collection<? extends DataRow> rows) {

        int size = dataRows.size();
        if (size > 0) {
            dataRows.clear();
            fireTableRowsDeleted(0, size - 1);
        }
        size = rows.size();
        if (size > 0) {
            dataRows.addAll(rows);
            fireTableRowsInserted(0, size - 1);
        }
    }

    @Override
    public int findColumn(String columnName) {
        return node.getFieldList().indexOf(columnName);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return node.getFieldList().get(columnIndex).getType();
    }

    @Override
    public String getColumnName(int column) {
        return node.getFieldList().get(column).getName();
    }

    /**
     * Returns the row located at the specified 0-based index. 
     * 
     * @param row position of the row to get.
     * 
     * @return a data row.
     */
    public DataRow getRow(int row) {
        return dataRows.get(row);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return super.isCellEditable(rowIndex, columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        dataRows.get(rowIndex).setValue(columnIndex, aValue);
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    /**
     * Updates the values in the row at the given index with the values specified
     * in the row passed as parameter.
     *
     * @param rowIndex the index of the existing row. If -1 is passed, the update call
     * is ignored .
     *
     * @param row the row containing the desired values.
     */
    public void update(int rowIndex, DataRow row) {
        if (0 > rowIndex)
            return;

        if (rowIndex >= dataRows.size())
            throw new IndexOutOfBoundsException();

        DataRow existing = dataRows.get(rowIndex);
        if (row.size() != existing.size())
            return; // mismatch

        try {
            dataRows.add(rowIndex, row);
            fireTableRowsUpdated(rowIndex, rowIndex); // this may throw exception
            dataRows.remove(rowIndex + 1); // remove old row
        }
        catch (ReelcashException e) {
            dataRows.remove(rowIndex);
        }
    }
}
