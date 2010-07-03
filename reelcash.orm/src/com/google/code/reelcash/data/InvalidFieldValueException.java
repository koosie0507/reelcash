/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data;

import com.google.code.reelcash.data.layout.fields.Resources;

/**
 *
 * @author andrei.olar
 */
public class InvalidFieldValueException extends RuntimeException {

    private static final long serialVersionUID = 5376533599083139130L;

    /**
     * Raise the error because the provided value is invalid in the field
     * with the given field name.
     * @param value invalid value
     * @param fieldName name of field
     */
    public InvalidFieldValueException(Object value, String fieldName) {
        super(String.format(Resources.getString("invalidfieldvalue"),
                (null == value) ? "<NULL>" : value.toString(),
                (null == fieldName) ? "<UNKNOWN>" : value.toString()));
    }

    /**
     * Raise the error that a NULL value is invalid in the field with
     * the given field name.
     * @param fieldName name of the field which does not accept nulls.
     */
    public InvalidFieldValueException(String fieldName) {
        this(null, fieldName);
    }

    /**
     * Raise the error that a NULL value is invalid in an UNKNOWN field.
     */
    public InvalidFieldValueException() {
        this(null, null);
    }
}
