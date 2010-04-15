/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data.contacts;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.FlatLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 * This class provides layout for a web address which would be kept in the
 * registry containing web addresses.
 * 
 * @author andrei.olar
 */
public class WebAddressNode extends FlatLayoutNode {

    private static final Object SYNC_ROOT = new Object();
    private static final String TABLE_NAME = "web_addresses";
    private static WebAddressNode instance;

    private WebAddressNode() {
        super(TABLE_NAME);
        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new StringField("address", KeyRole.NONE, true));
        getFieldList().add(new StringField("address_type", KeyRole.NONE, true));
        getFieldList().add(new StringField("custom_address_denomination", KeyRole.NONE, false));
    }

    public static WebAddressNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new WebAddressNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public StringField getAddressField() {
        return (StringField) getFieldList().get(1);
    }

    public StringField getAddressTypeField() {
        return (StringField) getFieldList().get(2);
    }

    public StringField getCustomAddressDenominationField() {
        return (StringField) getFieldList().get(3);
    }
}
