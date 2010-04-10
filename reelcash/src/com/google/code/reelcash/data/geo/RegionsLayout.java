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
 * Creates a layout for the countries/regions data table.
 *
 * @author cusi
 */
public class RegionsLayout extends DataLayout {

    private final RootLayoutNode countries;
    private final LeafLayoutNode regions;

    {
        countries = new RootLayoutNode("countries");
        countries.getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        countries.getFieldList().add(new StringField("name", KeyRole.NONE, true));

        regions = new LeafLayoutNode(countries, "regions");
        regions.getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        regions.getFieldList().add(new ReferenceField(countries.getFieldList().get(0),
                "country_id"));
        regions.getFieldList().add(new StringField("name", KeyRole.NONE, true));
        regions.getFieldList().add(new StringField("code", KeyRole.NONE, true));
    }

    public RegionsLayout() {
        super();
        addRoot(countries);
    }

    public DataLayoutNode getCountries() {
        return countries;
    }

    public DataLayoutNode getRegions() {
        return regions;
    }

    public DataRowComboModel getCountriesComboModel() {
        return new DataRowComboModel(countries);
    }

    public IntegerField getRegionIdField() {
        return (IntegerField) regions.getFieldList().get(0);
    }

    public IntegerField getCountryIdField() {
        return (IntegerField) countries.getFieldList().get(0);
    }

    public StringField getCountryNameField() {
        return (StringField) countries.getFieldList().get(1);
    }

    public ReferenceField getRegionCountryField() {
        return (ReferenceField) regions.getFieldList().get(1);
    }

    public StringField getRegionNameField() {
        return (StringField) regions.getFieldList().get(2);
    }

    public StringField getRegionCodeField() {
        return (StringField) regions.getFieldList().get(3);
    }
}
