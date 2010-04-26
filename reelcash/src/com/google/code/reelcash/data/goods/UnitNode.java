package com.google.code.reelcash.data.goods;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.FlatLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 * The structure of a measurement unit.
 * 
 * @author andrei.olar
 */
public class UnitNode extends FlatLayoutNode {

    private static final String TABLE_NAME = "units";
    private static final Object SYNC_ROOT = new Object();
    private static UnitNode instance;

    private UnitNode() {
        super(TABLE_NAME);

        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new StringField("code", KeyRole.UNIQUE, true));
        getFieldList().add(new StringField("name", KeyRole.NONE, false));
    }

    public static UnitNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new UnitNode();
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
}
