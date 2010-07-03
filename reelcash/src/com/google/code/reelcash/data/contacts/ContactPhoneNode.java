package com.google.code.reelcash.data.contacts;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.LeafLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.ReferenceField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 * Represents the layout of a phonebook entry corresponding to a contact.
 * 
 * @author andrei.olar
 */
public class ContactPhoneNode extends LeafLayoutNode {

    private static final Object SYNC_ROOT = new Object();
    private static final String TABLE_NAME = "contact_phones";
    private static ContactPhoneNode instance;

    private ContactPhoneNode() {
        super(ContactNode.getInstance(), TABLE_NAME);
        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new ReferenceField(ContactNode.getInstance().getIdField(), "contact_id"));
        getFieldList().add(new ReferenceField(PhoneNode.getInstance().getIdField(), "phone_id"));
        getFieldList().add(new IntegerField("priority", KeyRole.NONE, true));
        getFieldList().add(new StringField("category", KeyRole.NONE, false));
        getFieldList().add(new StringField("remarks", KeyRole.NONE, false));
    }

    public static ContactPhoneNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new ContactPhoneNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public ReferenceField getContactIdField() {
        return (ReferenceField) getFieldList().get(1);
    }

    public ReferenceField getPhoneIdField() {
        return (ReferenceField) getFieldList().get(2);
    }

    public IntegerField getPriorityField() {
        return (IntegerField) getFieldList().get(3);
    }

    public StringField getCategoryField() {
        return (StringField) getFieldList().get(4);
    }

    public StringField getRemarksField() {
        return (StringField) getFieldList().get(5);
    }
}
