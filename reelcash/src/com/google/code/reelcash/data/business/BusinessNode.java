/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data.business;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.banks.BankAccountNode;
import com.google.code.reelcash.data.geo.LocationNode;
import com.google.code.reelcash.data.layout.RootLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.ReferenceField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 * Node which describes the structure of a record keeping information about a business.
 *
 * @author andrei.olar
 */
public class BusinessNode extends RootLayoutNode {

    private static final Object SYNC_ROOT = new Object();
    private static final String TABLE_NAME = "businesses";
    private static BusinessNode instance;

    private BusinessNode() {
        super(TABLE_NAME);
        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new StringField("name", KeyRole.NONE, true));
        getFieldList().add(new ReferenceField(LocationNode.getInstance().getIdField(), "location_id"));
        getFieldList().add(new ReferenceField(BankAccountNode.getInstance().getIdField(), "bank_account_id"));
        getFieldList().add(new ReferenceField(LegalStatusNode.getInstance().getIdField(), "legal_status_id"));
    }

    public static BusinessNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new BusinessNode();
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

    public ReferenceField getBankAccountIdField() {
        return (ReferenceField) getFieldList().get(3);
    }

    public ReferenceField getLegalStatusIdField() {
        return (ReferenceField) getFieldList().get(4);
    }
}
