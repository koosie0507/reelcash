/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data;

import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.layout.fields.FieldSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents a row of data.
 *
 * @author cusi
 */
public class DataRow implements Iterable<String> {

    private final FieldSet fields;
    private Map<String, Object> data;

    /**
     * Creates a new data row which uses the information contained in the specified
     * field set for validation purposes.
     *
     * @param fields field set containing field validation rules.
     */
    public DataRow(final FieldSet fields) {
        this.fields = fields;
        data = Collections.emptyMap();
        for (Field field : fields) {
            data.put(field.getName(), field.getDefaultValue());
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

        for (Field f : fields.getPrimary()) {
            if (!data.get(f.getName()).equals(row.data.get(f.getName())))
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
    public FieldSet getFields() {
        return fields;
    }

    /**
     * Gets the value stored in the field with the given name.
     *
     * @param field name of field.
     *
     * @return the value stored in the field.
     */
    public Object getValue(String field) {
        return data.get(field);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (this.fields != null ? this.fields.hashCode() : 0);
        for (Field f : fields.getPrimary()) {
            Object hval = this.data.get(f.getName());
            hash = 41 * hash + (hval != null ? hval.hashCode() : 0);
        }
        return hash;
    }

    /**
     * Returns an iterator over the names of the fields in the data row.
     *
     * @return an iterator over the names of the fields in the data row.
     */
    public Iterator<String> iterator() {
        return data.keySet().iterator();
    }

    /**
     * Sets the value for the given field. A validation is also performed.
     *
     * @param field the name of the field
     * @param value the new value
     */
    public void setValue(String field, Object value) {
        Field f = fields.get(field);
        data.put(field, f.getValidValue(value));
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
}
