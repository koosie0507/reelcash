package com.google.code.reelcash.data.contacts;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.FlatLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 * The structure of one phone number registration in the phone book.
 * 
 * @author andrei.olar
 */
public class PhoneNode extends FlatLayoutNode {

    private static final Object SYNC_ROOT = new Object();
    private static final String TABLE_NAME = "phones";
    private static PhoneNode instance;

    private PhoneNode() {
        super(TABLE_NAME);
        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new StringField("phone", KeyRole.NONE, true));
        getFieldList().add(new StringField("call_charge", KeyRole.NONE, false));
    }

    public static PhoneNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new PhoneNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public StringField getPhoneField() {
        return (StringField) getFieldList().get(1);
    }

    public StringField getCallChargeField() {
        return (StringField) getFieldList().get(2);
    }
}
