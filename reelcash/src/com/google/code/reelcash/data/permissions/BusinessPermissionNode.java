package com.google.code.reelcash.data.permissions;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.business.BusinessNode;
import com.google.code.reelcash.data.layout.LeafLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.ReferenceField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 *
 * @author andrei.olar
 */
public class BusinessPermissionNode extends LeafLayoutNode {

    private static final Object SYNC_ROOT = new Object();
    private static final String TABLE_NAME = "business_permissions";
    private static BusinessPermissionNode instance;

    private BusinessPermissionNode() {
        super(PermissionNode.getInstance(), TABLE_NAME);
        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new ReferenceField(BusinessNode.getInstance().getIdField(), "business_id"));
        getFieldList().add(new ReferenceField(PermissionNode.getInstance().getIdField(), "permission_id"));
    }

    public static BusinessPermissionNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new BusinessPermissionNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public ReferenceField getBusinessIdField() {
        return (ReferenceField) getFieldList().get(1);
    }

    public ReferenceField getPermissionIdField() {
        return (ReferenceField) getFieldList().get(2);
    }
}
