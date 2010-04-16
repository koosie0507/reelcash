/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.swing;

import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.layout.fields.FieldNotFoundException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class provides an abstract factory for getting the proper {@link com.google.code.reelcash.swing.FieldDisplay}
 * for a given {@link com.google.code.reelcash.data.layout.fields.Field}.
 *
 * @author andrei.olar
 */
public abstract class FieldDisplayFactory {

    private static final Object syncRoot = new Object();
    private HashMap<Field, FieldDisplay> data;

    protected HashMap<Field, FieldDisplay> getData() {
        synchronized (syncRoot) {
            if (null == data)
                data = new HashMap();
        }
        return data;
    }

    protected FieldDisplay addFieldDisplayInfo(Field field) {
        FieldDisplay display = FieldDisplay.newInstance(field);
        getData().put(field, display);
        return display;
    }

    public abstract FieldDisplay getUIDisplayInfo(Field field);

    /**
     * Returns the display information for the field with the specified name.
     * @param fieldName the field's name.
     * @return the display information for the field with the specified name.
     */
    public FieldDisplay getUIDisplayInfo(String fieldName) {
        Field key = null;
        boolean found = false;
        Iterator<Field> f = getData().keySet().iterator();
        while (f.hasNext() && !found) {
            key = f.next();
            if (key.getName().equals(fieldName))
                found = true;
        }
        if (found)
            return getUIDisplayInfo(key);
        throw new FieldNotFoundException(fieldName);
    }
}
