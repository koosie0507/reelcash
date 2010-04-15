package com.google.code.reelcash.data.contacts;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.geo.LocationNode;
import com.google.code.reelcash.data.layout.RootLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.ReferenceField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 *
 * @author andrei.olar
 */
public class ContactNode extends RootLayoutNode {

    private static final Object SYNC_ROOT = new Object();
    private static final String TABLE_NAME = "contacts";
    private static ContactNode instance;

    private ContactNode() {
        super(TABLE_NAME);
        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new StringField("name", KeyRole.NONE, true));
        getFieldList().add(new StringField("surname", KeyRole.NONE, true));
        getFieldList().add(new ReferenceField(LocationNode.getInstance().getIdField(), "location_id"));
    }

    public static ContactNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new ContactNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public StringField getAddressField() {
        return (StringField) getFieldList().get(1);
    }

    public StringField getPostalCodeField() {
        return (StringField) getFieldList().get(2);
    }

    public ReferenceField getLocationIdField() {
        return (ReferenceField) getFieldList().get(3);
    }
}
