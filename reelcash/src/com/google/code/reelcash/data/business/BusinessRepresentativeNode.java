package com.google.code.reelcash.data.business;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.contacts.ContactNode;
import com.google.code.reelcash.data.layout.LeafLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.ReferenceField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 *
 * @author andrei.olar
 */
public class BusinessRepresentativeNode extends LeafLayoutNode {

    private static final Object SYNC_ROOT = new Object();
    private static final String TABLE_NAME = "business_representatives";
    private static BusinessRepresentativeNode instance;

    private BusinessRepresentativeNode() {
        super(BusinessNode.getInstance(), TABLE_NAME);
        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new ReferenceField(BusinessNode.getInstance().getIdField(), "business_id"));
        getFieldList().add(new ReferenceField(ContactNode.getInstance().getIdField(), "contact_id"));
        getFieldList().add(new StringField("text", KeyRole.NONE, true));
        getFieldList().add(new StringField("description", KeyRole.NONE, false));
    }

    public static BusinessRepresentativeNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new BusinessRepresentativeNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public ReferenceField getBusinessIdField() {
        return (ReferenceField) getFieldList().get(1);
    }

    public ReferenceField getContactIdField() {
        return (ReferenceField) getFieldList().get(2);
    }

    public StringField getTextField() {
        return (StringField) getFieldList().get(3);
    }

    public StringField getDescriptionField() {
        return (StringField) getFieldList().get(4);
    }
}
