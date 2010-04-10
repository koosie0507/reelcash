/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data.layout.fields;

import com.google.code.reelcash.data.KeyRole;

/**
 *
 * @author cusi
 */
public class ReferenceField extends Field {

    /**
     * Creates a new reference field.
     * @param name the name of the new field.
     * @param role the role of the new field.
     * @param mandatory whether the field is mandatory or not.
     */
    public ReferenceField(Field referred, String name, boolean mandatory) {
        super(name, KeyRole.KEY, referred.getType(), mandatory);
        setReferencedField(referred);
    }

    /**
     * Creates a new reference field.
     * @param name the name of the new field.
     * @param role the role of the new field.
     */
    public ReferenceField(Field referred, String name) {
        super(name, KeyRole.KEY, referred.getType(), true);
        setReferencedField(referred);
    }

    /**
     * Creates a new reference field.
     * @param name the name of the new field.
     */
    public ReferenceField(Field referred) {
        super(referred.getName(), KeyRole.KEY, referred.getType(), true);
        setReferencedField(referred);
    }

    @Override
    public Object getDefaultValue() {
        if(isMandatory())
            return getReferencedField().getDefaultValue();
        return null;
    }


}
