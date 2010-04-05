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

    public DataRow(final FieldSet fields) {
        this.fields = fields;
        data = Collections.emptyMap();
    }

    public Object getValue(String field) {
        return data.get(field);
    }

    public Iterator<String> iterator() {
        return data.keySet().iterator();
    }

    public void setValue(String field, Object value) {
        Field f = fields.get(field);
        data.put(field, f.getValidValue(value));
    }

    protected void validate(String columnName, Object value) {
        Field f = fields.get(columnName); // throws FieldNotFoundException
        f.validateValue(value);
    }
}
