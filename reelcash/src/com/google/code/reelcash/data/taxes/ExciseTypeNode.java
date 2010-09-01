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
public class ExciseTypeNode extends FlatLayoutNode {

    private static final String TABLE_NAME = "excise_types";
    private static final Object SYNC_ROOT = new Object();
    private static ExciseTypeNode instance;
    public static final String ID_FIELD = "id";
    public static final String CODE_FIELD = "code";
    public static final String NAME_FIELD = "name";
    public static final String IS_PERCENT_FIELD = "is_percent";
    public static final String VALUE_FIELD = "value";

    private ExciseTypeNode() {
        super(TABLE_NAME);

        getFieldList().add(new IntegerField(ID_FIELD, KeyRole.PRIMARY, true));
        getFieldList().add(new StringField(CODE_FIELD, KeyRole.UNIQUE, true));
        getFieldList().add(new StringField(NAME_FIELD, KeyRole.NONE, false));
        getFieldList().add(new BooleanField(IS_PERCENT_FIELD, KeyRole.NONE, false));
        getFieldList().add(new BigDecimalField(VALUE_FIELD, KeyRole.NONE, true, 9, 2));
    }

    public static ExciseTypeNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance) {
                instance = new ExciseTypeNode();
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

    public BooleanField getIsPercentField() {
        return (BooleanField) getFieldList().get(3);
    }

    public BigDecimalField getValueField() {
        return (BigDecimalField) getFieldList().get(4);
    }
}
