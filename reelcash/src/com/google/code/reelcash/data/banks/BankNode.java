package com.google.code.reelcash.data.banks;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.RootLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.ReferenceField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 * Represents the structure of a bank in the database.
 * @author cusi
 */
public class BankNode extends RootLayoutNode {

    private static final String TABLE_NAME = "banks";
    private static final Object SYNC_ROOT = new Object();
    private static BankNode instance;

    private BankNode() {
        super(TABLE_NAME);
        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new StringField("name", KeyRole.UNIQUE, true));
        
        getFieldList().add(new ReferenceField(getFieldList().get(0), "parent_id", false));
        getFieldList().add(new ReferenceField(getFieldList().get(0), "parent_id", false));
    }
}
