/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data.layout.fields;

import com.google.code.reelcash.ReelcashException;

/**
 * The error is raised when a field can't be found for various reasons.
 *
 * @author cusi
 */
public class FieldNotFoundException extends ReelcashException {
    private static final long serialVersionUID = 3724017450262194683L;

    /**
     * Use this constructor for a generic "field not found" error.
     */
    public FieldNotFoundException() {
        super(Resources.getString("fieldnotfounderror1"));
    }

    /**
     * Use this constructor for a more detailed error message which also
     * mentions the field's name.
     *
     * @param fieldName the name of the field which can't be found
     */
    public FieldNotFoundException(String fieldName) {
        super(String.format(Resources.getString("fieldnotfounderror2"), fieldName));
    }
}
