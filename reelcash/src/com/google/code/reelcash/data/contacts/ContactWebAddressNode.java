package com.google.code.reelcash.data.contacts;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.LeafLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.ReferenceField;

/**
 * Represents the layout of a web address of a given contact.
 * 
 * @author andrei.olar
 */
public class ContactWebAddressNode extends LeafLayoutNode {

    private static final Object SYNC_ROOT = new Object();
    private static final String TABLE_NAME = "contact_web_addresses";
    private static ContactWebAddressNode instance;

    private ContactWebAddressNode() {
        super(ContactNode.getInstance(), TABLE_NAME);
        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new ReferenceField(ContactNode.getInstance().getIdField(), "contact_id"));
        getFieldList().add(new ReferenceField(WebAddressNode.getInstance().getIdField(), "web_address_id"));
        getFieldList().add(new IntegerField("priority", KeyRole.NONE, true));
    }

    public static ContactWebAddressNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new ContactWebAddressNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public ReferenceField getContactIdField() {
        return (ReferenceField) getFieldList().get(1);
    }

    public ReferenceField getWebAddressIdField() {
        return (ReferenceField) getFieldList().get(2);
    }

    public IntegerField getPriorityField() {
        return (IntegerField) getFieldList().get(3);
    }
}
