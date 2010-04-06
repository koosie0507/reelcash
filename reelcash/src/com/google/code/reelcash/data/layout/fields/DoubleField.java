package com.google.code.reelcash.data.layout.fields;

import com.google.code.reelcash.data.KeyRole;

/**
 * Implements the @see com.google.code.reelcash.models.fields.Field abstract class.
 *
 * @author andrei.olar
 */
public class DoubleField extends Field {

    private static final long serialVersionUID = -1367261005920942956L;

    /**
     * Creates a new DoubleField instance.
     * @param name the name of the new field.
     * @param role the role of the new field.
     * @param mandatory whether the field is mandatory or not.
     */
    public DoubleField(String name, KeyRole role, boolean mandatory) {
        super(name, role, Double.class, mandatory);
    }

    /**
     * Creates a new DoubleField instance.
     * @param name the name of the new field.
     * @param role the role of the new field.
     */
    public DoubleField(String name, KeyRole role) {
        super(name, role, Double.class, true);
    }

    /**
     * Creates a new DoubleField instance.
     * @param name the name of the new field.
     */
    public DoubleField(String name) {
        super(name, KeyRole.NONE, Double.class, true);
    }

    @Override
    public Object getDefaultValue() {
       if(isMandatory()) return 0.0;
       return null;
    }


}
