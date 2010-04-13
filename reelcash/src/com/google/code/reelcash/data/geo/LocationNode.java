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

    private static final String TABLE_NAME = "locations";
    private static final Object SYNC_ROOT = new Object();
    private static LocationNode instance;

    private LocationNode() {
        super(TABLE_NAME);
        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new ReferenceField(getFieldList().get(0), "parent_id", false)); // cities
        getFieldList().add(new StringField("name", KeyRole.NONE, true));
        getFieldList().add(new StringField("code", KeyRole.NONE, true));
    }
}
