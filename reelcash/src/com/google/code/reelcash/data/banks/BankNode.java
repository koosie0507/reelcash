package com.google.code.reelcash.data.banks;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.geo.LocationNode;
import com.google.code.reelcash.data.layout.RootLayoutNode;
import com.google.code.reelcash.data.layout.fields.BooleanField;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.ReferenceField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 * Represents the structure of a bank in the database.
 * @author cusi
 */
public class BankNode extends RootLayoutNode {

    private static final String TABLE_NAME = "banks";
    private static final Object SYNC_ROOT = new Object();
    private static BankNode instance;

    private BankNode() {
        super(TABLE_NAME);
        IntegerField idField = new IntegerField("id", KeyRole.PRIMARY, true);
        getFieldList().add(idField);
        getFieldList().add(new StringField("name", KeyRole.UNIQUE, true));
        getFieldList().add(new ReferenceField(LocationNode.getInstance().getIdField(), "location_id", true));
        getFieldList().add(new ReferenceField(idField, "parent_id", false));
        getFieldList().add(new BooleanField("allow_currency_exchange", KeyRole.NONE, true));
    }

    public static BankNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new BankNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public StringField getNameField() {
        return (StringField) getFieldList().get(1);
    }

    public ReferenceField getLocationIdField() {
        return (ReferenceField) getFieldList().get(2);
    }

    public ReferenceField getParentIdField() {
        return (ReferenceField) getFieldList().get(3);
    }

    public BooleanField getMustExchangeField() {
        return (BooleanField) getFieldList().get(4);
    }
}
