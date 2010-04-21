/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data.layout.fields;

import com.google.code.reelcash.data.InvalidFieldValueException;
import com.google.code.reelcash.data.KeyRole;

/**
 * This field is used for representing boolean values.
 * 
 * @author andrei.olar
 */
public class BooleanField extends Field {

    private static final long serialVersionUID = -2861437453443852272L;

    /**
     * Creates a new boolean field.
     * @param name the name of the new field.
     * @param role the role of the new field.
     * @param mandatory whether the field is mandatory or not.
     */
    public BooleanField(String name, KeyRole role, boolean mandatory) {
        super(name, role, Boolean.class, mandatory);
    }

    /**
     * Creates a new boolean field.
     * @param name the name of the new field.
     * @param role the role of the new field.
     */
    public BooleanField(String name, KeyRole role) {
        super(name, role, Boolean.class, true);
    }

    /**
     * Creates a new boolean field.
     * @param name the name of the new field.
     */
    public BooleanField(String name) {
        super(name, KeyRole.NONE, Boolean.class, true);
    }

    @Override
    public Object getDefaultValue() {
        if (isMandatory())
            return false;
        else
            return null;
    }

    @Override
    public Object getValidValue(Object actualValue) {
        if (!validateValue(actualValue))
            throw new InvalidFieldValueException(actualValue, getName());
        if (null == actualValue)
            return null;

        if (Boolean.class == actualValue.getClass())
            return (Boolean) actualValue;
        else //it's a Number
            return new Boolean(0 != ((Number) actualValue).intValue());
    }

    @Override
    public boolean validateValue(Object aValue) {
        // check whether the field is mandatory and the value is null
        if (null == aValue && (isMandatory() || KeyRole.PRIMARY.equals(getKeyRole())))
            return false;

        if (null == aValue)
            return true;

        if (Boolean.class != aValue.getClass()) {
            if (aValue instanceof Number)
                return true;
            return false;
        }
        return true;
    }
}
