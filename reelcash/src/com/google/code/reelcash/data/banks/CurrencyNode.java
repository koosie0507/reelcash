package com.google.code.reelcash.data.banks;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.FlatLayoutNode;
import com.google.code.reelcash.data.layout.fields.BooleanField;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 * Represents the layout of a currency specification.
 * 
 * @author andrei.olar
 */
public class CurrencyNode extends FlatLayoutNode {

    private static final String TABLE_NAME = "currencies";
    private static final Object SYNC_ROOT = new Object();
    private static CurrencyNode instance;

    private CurrencyNode() {
        super(TABLE_NAME);
        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new StringField("code", KeyRole.UNIQUE, true));
        getFieldList().add(new BooleanField("must_exchange", KeyRole.NONE, true));
        getFieldList().add(new StringField("name", KeyRole.NONE, false));
    }

    public static CurrencyNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new CurrencyNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public StringField getCodeField() {
        return (StringField) getFieldList().get(1);
    }

    public BooleanField getMustExchangeField() {
        return (BooleanField) getFieldList().get(2);
    }

    public StringField getNameField() {
        return (StringField) getFieldList().get(3);
    }
}
