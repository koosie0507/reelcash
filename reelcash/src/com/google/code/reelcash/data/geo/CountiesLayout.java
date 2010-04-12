/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data.geo;

import com.google.code.reelcash.data.DataRowComboModel;
import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.DataLayout;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.LeafLayoutNode;
import com.google.code.reelcash.data.layout.RootLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.ReferenceField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 *
 * @author cusi
 */
public class CountiesLayout extends DataLayout {

    private final RootLayoutNode regions;
    private final LeafLayoutNode counties;

    {
        regions = new RootLayoutNode("regions");
        regions.getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        regions.getFieldList().add(new StringField("name", KeyRole.NONE, true));

        counties = new LeafLayoutNode(regions, "counties");
        counties.getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        counties.getFieldList().add(new ReferenceField(regions.getFieldList().get(0),
                "region_id"));
        counties.getFieldList().add(new StringField("name", KeyRole.NONE, true));
        counties.getFieldList().add(new StringField("code", KeyRole.NONE, true));
    }

    public CountiesLayout() {
        super();
        addRoot(regions);
    }

    public DataLayoutNode getRegions() {
        return regions;
    }

    public DataLayoutNode getCounties() {
        return counties;
    }

    public DataRowComboModel getRegionsComboModel() {
        return new DataRowComboModel(regions);
    }

    public IntegerField getCountyIdField() {
        return (IntegerField) counties.getFieldList().get(0);
    }

    public IntegerField getRegionIdField() {
        return (IntegerField) regions.getFieldList().get(0);
    }

    public StringField getRegionNameField() {
        return (StringField) regions.getFieldList().get(1);
    }

    public ReferenceField getCountyRegionField() {
        return (ReferenceField) counties.getFieldList().get(1);
    }

    public StringField getCountyNameField() {
        return (StringField) counties.getFieldList().get(2);
    }

    public StringField getCountyCodeField() {
        return (StringField) counties.getFieldList().get(3);
    }
}
