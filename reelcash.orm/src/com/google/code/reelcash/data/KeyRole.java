package com.google.code.reelcash.data;

/**
 * <p>Key role of a field in a data model.</p>
 * 
 * @author andrei.olar
 */
public enum KeyRole {

    /**
     * No key role is assigned to the field.
     */
    NONE,
    /**
     * The field is a key field (indexed or foreign, it doesn't really matter)
     */
    KEY,
    /**
     * The field is supposed to contain unique values.
     */
    UNIQUE,
    /**
     * The field is supposed to be part of the primary key of the relation
     * described by the model.
     */
    PRIMARY
}
