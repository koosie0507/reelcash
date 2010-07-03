package com.google.code.reelcash.data.banks;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.LeafLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.ReferenceField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 *
 * @author andrei.olar
 */
public class BankAccountNode extends LeafLayoutNode {

    private static final String TABLE_NAME = "bank_accounts";
    private static final Object SYNC_ROOT = new Object();
    private static BankAccountNode instance;

    private BankAccountNode() {
        super(BankNode.getInstance(), TABLE_NAME);
        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new ReferenceField(BankNode.getInstance().getIdField(), "bank_id", true));
        getFieldList().add(new StringField("account", KeyRole.NONE, true));
    }

    public static BankAccountNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new BankAccountNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public ReferenceField getBankIdField() {
        return (ReferenceField) getFieldList().get(1);
    }

    public StringField getAccountField() {
        return (StringField) getFieldList().get(2);
    }
}
