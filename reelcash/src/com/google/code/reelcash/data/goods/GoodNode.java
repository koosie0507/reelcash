package com.google.code.reelcash.data.goods;
import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.FlatLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.ReferenceField;
import com.google.code.reelcash.data.layout.fields.StringField;
import com.google.code.reelcash.data.taxes.VatTypeNode;

/**
 * Insert some documentation for the class <b>GoodNode</b> (what's its purpose,
 * why this implementation, that sort of thing).
 *
 * @author andrei.olar
 */
public class GoodNode extends FlatLayoutNode {
    private static final String TABLE_NAME = "goods";
    private static final Object SYNC_ROOT = new Object();
    private static GoodNode instance;

    private GoodNode() {
        super(TABLE_NAME);

        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new StringField("code", KeyRole.NONE, true));
        getFieldList().add(new StringField("name", KeyRole.NONE, true));
        getFieldList().add(new StringField("description", KeyRole.NONE, false));
        getFieldList().add(new ReferenceField(VatTypeNode.getInstance().getIdField(), "vat_type_id", true));
    }

    public static GoodNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new GoodNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField)getFieldList().get(0);
    }

    public StringField getCodeField() {
        return (StringField)getFieldList().get(1);
    }

    public StringField getNameField() {
        return (StringField)getFieldList().get(2);
    }

    public StringField getDescriptionField() {
        return (StringField)getFieldList().get(3);
    }

    public ReferenceField getVatTypeIdField() {
        return (ReferenceField)getFieldList().get(4);
    }
}
