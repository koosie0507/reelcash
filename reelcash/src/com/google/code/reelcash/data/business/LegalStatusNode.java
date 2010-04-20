package com.google.code.reelcash.data.business;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.FlatLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 * Node for describing legal statuses.
 * @author andrei.olar
 */
public class LegalStatusNode extends FlatLayoutNode {

    private static final String TABLE_NAME = "legal_statuses";
    private static final Object SYNC_ROOT = new Object();
    private static LegalStatusNode instance;

    private LegalStatusNode() {
        super(TABLE_NAME);
        IntegerField idField = new IntegerField("id", KeyRole.PRIMARY, true);
        getFieldList().add(idField);
        getFieldList().add(new StringField("code", KeyRole.UNIQUE, true, 3));
        getFieldList().add(new StringField("name", KeyRole.UNIQUE, true));
        getFieldList().add(new StringField("description", KeyRole.NONE, false));
    }

    public static LegalStatusNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new LegalStatusNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public StringField getCodeField() {
        return (StringField) getFieldList().get(1);
    }

    public StringField getNameField() {
        return (StringField) getFieldList().get(2);
    }

    public StringField getDescriptionField() {
        return (StringField) getFieldList().get(3);
    }
}
