package com.google.code.reelcash.data.permissions;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.RootLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 *
 * @author andrei.olar
 */
public class PermissionNode extends RootLayoutNode {

    private static final Object SYNC_ROOT = new Object();
    private static final String TABLE_NAME = "permissions";
    private static PermissionNode instance;

    /**
     * Creates a new flat layout node for the "countries" data table.
     */
    private PermissionNode() {
        super(TABLE_NAME);
        super.getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        super.getFieldList().add(new StringField("name", KeyRole.UNIQUE, true));
        super.getFieldList().add(new StringField("description", KeyRole.NONE, false));
    }

    public static PermissionNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new PermissionNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public StringField getNameField() {
        return (StringField) getFieldList().get(1);
    }

    public StringField getDescriptionField() {
        return (StringField) getFieldList().get(2);
    }
}
