package com.google.code.reelcash.data.layout.fields;

import java.util.Currency;
import com.google.code.reelcash.data.KeyRole;

/**
 * Implements the @see com.google.code.reelcash.models.fields.Field abstract class. Values
 * for this field should be Currency types.
 *
 * @author andrei.olar
 */
public class CurrencyField extends Field {

    private static final long serialVersionUID = -1367261005920942956L;

    /**
     * Creates a new field of Currency.
     * @param name the name of the new field.
     * @param role the role of the new field.
     * @param mandatory whether the field is mandatory or not.
     */
    public CurrencyField(String name, KeyRole role, boolean mandatory) {
        super(name, role, Currency.class, mandatory);
    }

    /**
     * Creates a new field of Currency.
     * @param name the name of the new field.
     * @param role the role of the new field.
     */
    public CurrencyField(String name, KeyRole role) {
        super(name, role, Currency.class, true);
    }

    /**
     * Creates a new field of Currency.
     * @param name the name of the new field.
     */
    public CurrencyField(String name) {
        super(name, KeyRole.NONE, Currency.class, true);
    }
}
