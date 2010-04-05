package com.google.code.reelcash.data.layout.fields;

import com.google.code.reelcash.data.KeyRole;
import java.sql.Date;

/**
 * Implements the @see com.google.code.reelcash.models.fields.Field abstract class. Values
 * for this field should be SQL Date types.
 *
 * @author andrei.olar
 */
public class DateField extends Field {

    private static final long serialVersionUID = -1367261005920942956L;

    /**
     * Creates a new field of Date.
     * @param name the name of the new field.
     * @param role the role of the new field.
     * @param mandatory whether the field is mandatory or not.
     */
    public DateField(String name, KeyRole role, boolean mandatory) {
        super(name, role, Date.class, mandatory);
    }

    /**
     * Creates a new field of Date.
     * @param name the name of the new field.
     * @param role the role of the new field.
     */
    public DateField(String name, KeyRole role) {
        super(name, role, Date.class, true);
    }

    /**
     * Creates a new field of Date.
     * @param name the name of the new field.
     */
    public DateField(String name) {
        super(name, KeyRole.NONE, Date.class, true);
    }

    @Override
    public Object getValidValue(Object actualValue) {
        if (null == actualValue || !(actualValue instanceof java.sql.Date) || !(actualValue instanceof java.util.Date))
            return actualValue; //TODO: throw exception for non-null case

        if (actualValue instanceof java.util.Date)
            return new java.sql.Date(((java.util.Date) actualValue).getTime());

        return actualValue; // we return the actual value for all java.sql.Date instances which are passing through
    }
}
