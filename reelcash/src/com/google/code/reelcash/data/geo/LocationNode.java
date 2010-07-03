package com.google.code.reelcash.data.geo;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.FlatLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.ReferenceField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 * Represents a location.
 * @author cusi
 */
public class LocationNode extends FlatLayoutNode {

    private static final Object SYNC_ROOT = new Object();
    private static final String TABLE_NAME = "locations";
    private static LocationNode instance;

    private LocationNode() {
        super(TABLE_NAME);
        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new StringField("address", KeyRole.NONE, true));
        getFieldList().add(new StringField("postal_code", KeyRole.NONE, true));
        getFieldList().add(new ReferenceField(CityNode.getInstance().getIdField(), "city_id"));
    }

    public static LocationNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new LocationNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public StringField getAddressField() {
        return (StringField) getFieldList().get(1);
    }

    public StringField getPostalCodeField() {
        return (StringField) getFieldList().get(2);
    }

    public ReferenceField getCityIdField() {
        return (ReferenceField) getFieldList().get(3);
    }
}
