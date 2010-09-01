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
public class VatTypeNode extends FlatLayoutNode {

    private static final String TABLE_NAME = "vat_types";
    private static final Object SYNC_ROOT = new Object();
    private static VatTypeNode instance;

    private VatTypeNode() {
        super(TABLE_NAME);

        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new StringField("code", KeyRole.UNIQUE, true));
        getFieldList().add(new StringField("name", KeyRole.NONE, false));
        getFieldList().add(new BigDecimalField("percent", KeyRole.NONE, true, 3, 2));
        getFieldList().add(new BooleanField("is_default", KeyRole.NONE, true));
    }

    public static VatTypeNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance) {
                instance = new VatTypeNode();
            }
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

    public BigDecimalField getPercentField() {
        return (BigDecimalField) getFieldList().get(3);
    }

    public BooleanField getIsDefaultField() {
        return (BooleanField) getFieldList().get(4);
    }
}
