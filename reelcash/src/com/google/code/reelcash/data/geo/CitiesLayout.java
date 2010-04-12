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
public class CitiesLayout extends DataLayout {
    private final RootLayoutNode counties;
    private final LeafLayoutNode cities;

    {
        counties = new RootLayoutNode("counties");
        counties.getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        counties.getFieldList().add(new StringField("name", KeyRole.NONE, true));

        cities = new LeafLayoutNode(counties, "cities");
        cities.getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        cities.getFieldList().add(new ReferenceField(counties.getFieldList().get(0),
                "county_id"));
        cities.getFieldList().add(new StringField("name", KeyRole.NONE, true));
        cities.getFieldList().add(new StringField("code", KeyRole.NONE, true));
    }

    public CitiesLayout() {
        super();
        addRoot(counties);
    }

    public DataLayoutNode getCounties() {
        return counties;
    }

    public DataLayoutNode getCities() {
        return cities;
    }

    public DataRowComboModel getCountiesComboModel() {
        return new DataRowComboModel(counties);
    }

    public IntegerField getCountyIdField() {
        return (IntegerField) counties.getFieldList().get(0);
    }

    public StringField getCountyNameField() {
        return (StringField) counties.getFieldList().get(1);
    }

    public IntegerField getCityIdField() {
        return (IntegerField) cities.getFieldList().get(0);
    }

    public ReferenceField getCityCountyField() {
        return (ReferenceField) cities.getFieldList().get(1);
    }

    public StringField getCityNameField() {
        return (StringField) cities.getFieldList().get(2);
    }

    public StringField getCityCodeField() {
        return (StringField) cities.getFieldList().get(3);
    }
}
