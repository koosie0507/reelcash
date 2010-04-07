/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data;

import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.layout.fields.FieldList;
import com.google.code.reelcash.data.layout.fields.FieldNotFoundException;
import java.util.Iterator;

/**
 * Represents a row of data.
 *
 * @author cusi
 */
public class DataRow implements Iterable<String> {

    private final FieldList fields;
    private final Object[] data;

    /**
     * Creates a new data row which uses the information contained in the specified
     * field set for validation purposes.
     *
     * @param fields field set containing field validation rules.
     */
    public DataRow(final FieldList fields) {
        this.fields = fields;
        data = new Object[fields.size()];
        for (int i = 0; i < data.length; i++) {
        }
    }

    /**
     * Returns true if the current row is equal to the given row.
     *
     * @param row the row to compare this row to.
     * @return true, if the current row is equal to the given row.
     */
    public boolean equals(DataRow row) {
        if (this == row)
            return true;
        if (null == row)
            return false;

        if (!this.fields.equals(row.fields))
            return false;

        for (int i = fields.size() - 1; i > -1; i--) {
            if (!data[i].equals(row.data[i]))
                return false;
        }
        return true;
    }

    /**
     * Returns true if the current row is equal to the given row.
     *
     * @param arg the row to compare this row to.
     * @return true, if the current row is equal to the given row. If
     * the argument is not a data row, the method returns false.
     */
    @Override
    public boolean equals(Object arg) {
        if (arg instanceof DataRow)
            return equals((DataRow) arg);
        return false;
    }

    /**
     * Gets the field set which contains the information used for validation.
     *
     * @return a field set.
     */
    public FieldList getFields() {
        return fields;
    }

    public Object getValue(int index) {
        return data[index];
    }

    /**
     * Gets the value stored in the field with the given name.
     *
     * @param field name of field.
     *
     * @return the value stored in the field.
     */
    public Object getValue(String field) {
        int idx = fields.indexOf(field);
        if (idx < 0)
            throw new FieldNotFoundException(field);
        return data[idx];
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (this.fields != null ? this.fields.hashCode() : 0);
        for (Field f : fields.getPrimary()) {
            int idx = fields.indexOf(f);
            hash = 41 * hash + (data[idx] != null ? data[idx].hashCode() : 0);
        }
        return hash;
    }

    /**
     * Returns an iterator over the names of the fields in the data row.
     *
     * @return an iterator over the names of the fields in the data row.
     */
    public Iterator<String> iterator() {
        return new NameIterator();
    }

    /**
     * Sets the value for the given field. A validation is also performed.
     *
     * @param field the name of the field
     * @param value the new value
     */
    public void setValue(String field, Object value) {
        int idx = fields.indexOf(field);
        if (0 > idx)
            throw new FieldNotFoundException(field);

        data[idx] = fields.get(idx).getValidValue(value);
    }

    public void setValue(int idx, Object value) {
        data[idx] = fields.get(idx).getValidValue(value);
    }

    /**
     * Validates a field's value. Override this to provide custom validation.
     * @param columnName field name.
     * @param value value of field
     */
    protected void validate(String columnName, Object value) {
        Field f = fields.get(columnName); // throws FieldNotFoundException
        f.validateValue(value);
    }

    private class NameIterator implements Iterator<String> {

        private Field current;
        private int idx = 0;

        public boolean hasNext() {
            return idx < DataRow.this.data.length;
        }

        public String next() {
            current = DataRow.this.fields.get(idx);
            idx++;
            return current.getName();
        }

        public void remove() {
            if (null != current)
                DataRow.this.fields.remove(current);
        }
    }
}
