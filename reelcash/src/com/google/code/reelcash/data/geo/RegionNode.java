package com.google.code.reelcash.data.geo;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.FlatLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.ReferenceField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 *
 * @author andrei.olar
 */
public class RegionNode extends FlatLayoutNode {

    private static final Object SYNC_ROOT = new Object();
    private static final String TABLE_NAME = "regions";
    private static RegionNode instance;

    private RegionNode() {
        super(TABLE_NAME);
        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new ReferenceField(CountryNode.getInstance().getIdField(), "country_id"));
        getFieldList().add(new StringField("name", KeyRole.NONE, true));
        getFieldList().add(new StringField("code", KeyRole.NONE, true));
    }

    public static RegionNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new RegionNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public ReferenceField getCountryIdField() {
        return (ReferenceField) getFieldList().get(1);
    }

    public StringField getNameField() {
        return (StringField) getFieldList().get(2);
    }

    public StringField getCodeField() {
        return (StringField) getFieldList().get(3);
    }
}
