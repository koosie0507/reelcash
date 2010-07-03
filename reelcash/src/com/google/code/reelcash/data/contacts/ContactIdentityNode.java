package com.google.code.reelcash.data.contacts;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.geo.LocationNode;
import com.google.code.reelcash.data.layout.LeafLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.ReferenceField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 *
 * @author andrei.olar
 */
public class ContactIdentityNode extends LeafLayoutNode {

    private static final Object SYNC_ROOT = new Object();
    private static final String TABLE_NAME = "contact_identities";
    private static ContactIdentityNode instance;

    private ContactIdentityNode() {
        super(ContactNode.getInstance(), TABLE_NAME);
        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new ReferenceField(ContactNode.getInstance().getIdField(), "contact_id"));
        getFieldList().add(new StringField("identity_type", KeyRole.NONE, true));
        getFieldList().add(new StringField("identity_field1", KeyRole.NONE, true));
        getFieldList().add(new StringField("identity_field2", KeyRole.NONE, true));
        getFieldList().add(new StringField("identity_field3", KeyRole.NONE, false));
        getFieldList().add(new StringField("identity_field4", KeyRole.NONE, false));
    }

    public static ContactIdentityNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new ContactIdentityNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public ReferenceField getContactIdField() {
        return (ReferenceField) getFieldList().get(1);
    }

    public StringField getIdentityTypeField() {
        return (StringField) getFieldList().get(2);
    }

    public StringField getIdentityField1Field() {
        return (StringField) getFieldList().get(3);
    }

    public StringField getIdentityField2Field() {
        return (StringField) getFieldList().get(4);
    }

    public StringField getIdentityField3Field() {
        return (StringField) getFieldList().get(5);
    }

    public StringField getIdentityField4Field() {
        return (StringField) getFieldList().get(6);
    }
}
