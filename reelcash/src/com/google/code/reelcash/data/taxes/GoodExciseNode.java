package com.google.code.reelcash.data.taxes;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.goods.GoodNode;
import com.google.code.reelcash.data.layout.FlatLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.ReferenceField;

/**
 * Insert some documentation for the class <b>GoodExciseNode</b> (what's its purpose,
 * why this implementation, that sort of thing).
 *
 * @author andrei.olar
 */
public class GoodExciseNode extends FlatLayoutNode {

    private static final String TABLE_NAME = "good_excises";
    private static final Object SYNC_ROOT = new Object();
    private static GoodExciseNode instance;

    private GoodExciseNode() {
        super(TABLE_NAME);
        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new ReferenceField(GoodNode.getInstance().getIdField(), "good_id", true));
        getFieldList().add(new ReferenceField(ExciseTypeNode.getInstance().getIdField(), "excise_type_id", true));
    }

    public static GoodExciseNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new GoodExciseNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public ReferenceField getGoodIdField() {
        return (ReferenceField) getFieldList().get(1);
    }

    public ReferenceField getExciseTypeIdField() {
        return (ReferenceField) getFieldList().get(2);
    }
}
