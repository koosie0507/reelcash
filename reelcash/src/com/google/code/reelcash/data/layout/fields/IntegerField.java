/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data.layout.fields;

import com.google.code.reelcash.data.KeyRole;

/**
 * This type of field uses integers.
 * 
 * @author andrei.olar
 */
public class IntegerField extends Field {

    private static final long serialVersionUID = -1367261005920942956L;

    /**
     * Creates a new integer field.
     * @param name the name of the new field.
     * @param role the role of the new field.
     * @param mandatory whether the field is mandatory or not.
     */
    public IntegerField(String name, KeyRole role, boolean mandatory) {
        super(name, role, Integer.class, mandatory);
    }

    /**
     * Creates a new integer field.
     * @param name the name of the new field.
     * @param role the role of the new field.
     */
    public IntegerField(String name, KeyRole role) {
        super(name, role, Integer.class, true);
    }

    /**
     * Creates a new integer field.
     * @param name the name of the new field.
     */
    public IntegerField(String name) {
        super(name, KeyRole.NONE, Integer.class, true);
    }
}
