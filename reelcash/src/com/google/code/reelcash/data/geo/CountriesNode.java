/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data.geo;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.FlatLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 * Represents a flat countries super.
 *
 * @author cusi
 */
public class CountriesNode extends FlatLayoutNode {

    /**
     * Creates a new flat layout node for the "countries" data table.
     */
    public CountriesNode() {
        super("countries");
        super.getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        super.getFieldList().add(new StringField("name", KeyRole.NONE, true));
        super.getFieldList().add(new StringField("iso_name", KeyRole.NONE, true));
        super.getFieldList().add(new StringField("iso_code2", KeyRole.NONE, true));
        super.getFieldList().add(new StringField("iso_code3", KeyRole.NONE, true));
    }

    public Field getIdField() {
        return getFieldList().get(0);
    }

    public Field getNameField() {
        return getFieldList().get(1);
    }

    public Field getIsoNameField() {
        return getFieldList().get(2);
    }

    public Field getIsoCode2Field() {
        return getFieldList().get(3);
    }

    public Field getIsoCode3Field() {
        return getFieldList().get(4);
    }
}
