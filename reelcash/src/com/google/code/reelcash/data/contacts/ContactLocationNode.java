/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class ContactLocationNode extends LeafLayoutNode {

    private static final Object SYNC_ROOT = new Object();
    private static final String TABLE_NAME = "contact_locations";
    private static ContactLocationNode instance;

    private ContactLocationNode() {
        super(ContactNode.getInstance(), TABLE_NAME);
        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new ReferenceField(ContactNode.getInstance().getIdField(), "contact_id"));
        getFieldList().add(new ReferenceField(LocationNode.getInstance().getIdField(), "location_id"));
        getFieldList().add(new StringField("subdivision", KeyRole.NONE, false));
        getFieldList().add(new IntegerField("priority", KeyRole.NONE, true));
    }

    public static ContactLocationNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new ContactLocationNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public ReferenceField getContactIdField() {
        return (ReferenceField) getFieldList().get(1);
    }

    public ReferenceField getLocationIdField() {
        return (ReferenceField) getFieldList().get(2);
    }

    public StringField getSubdivisionField() {
        return (StringField) getFieldList().get(3);
    }

    public IntegerField getPriorityField() {
        return (IntegerField) getFieldList().get(4);
    }
}
