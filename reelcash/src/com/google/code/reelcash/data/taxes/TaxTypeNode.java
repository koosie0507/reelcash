package com.google.code.reelcash.data.taxes;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.FlatLayoutNode;
import com.google.code.reelcash.data.layout.fields.BigDecimalField;
import com.google.code.reelcash.data.layout.fields.BooleanField;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 *
 * @author andrei.olar
 */
public class TaxTypeNode extends FlatLayoutNode {

    private static final String TABLE_NAME = "tax_types";
    private static final Object SYNC_ROOT = new Object();
    private static TaxTypeNode instance;

    private TaxTypeNode() {
        super(TABLE_NAME);

        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new StringField("code", KeyRole.UNIQUE, true));
        getFieldList().add(new StringField("name", KeyRole.NONE, false));
        getFieldList().add(new BooleanField("is_percent", KeyRole.NONE, false));
        getFieldList().add(new BigDecimalField("value", KeyRole.NONE, true, 9, 2));
    }

    public static TaxTypeNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new TaxTypeNode();
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

    public BooleanField getIsPercentField() {
        return (BooleanField) getFieldList().get(3);
    }

    public BigDecimalField getValueField() {
        return (BigDecimalField) getFieldList().get(4);
    }
}
