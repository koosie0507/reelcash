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
public class LocationsLayout extends DataLayout{
    private final RootLayoutNode cities;
    private final LeafLayoutNode locations;

    {
        cities = new RootLayoutNode("cities");
        cities.getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        cities.getFieldList().add(new StringField("name", KeyRole.NONE, true));

        locations = new LeafLayoutNode(cities, "locations");
        locations.getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        locations.getFieldList().add(new StringField("address", KeyRole.NONE, true));
        locations.getFieldList().add(new StringField("postal_code", KeyRole.NONE, true));
        locations.getFieldList().add(new ReferenceField(cities.getFieldList().get(0),
                "city_id"));
    }

    public LocationsLayout() {
        super();
        addRoot(cities);
    }

    public DataLayoutNode getCities() {
        return cities;
    }

    public DataLayoutNode getLocations() {
        return locations;
    }

    public DataRowComboModel getCitiesComboModel() {
        return new DataRowComboModel(cities);
    }

    public IntegerField getCityIdField() {
        return (IntegerField) cities.getFieldList().get(0);
    }

    public StringField getCityNameField() {
        return (StringField) cities.getFieldList().get(1);
    }

    public IntegerField getLocationIdField() {
        return (IntegerField) locations.getFieldList().get(0);
    }

    public StringField getLocationAddressField() {
        return (StringField) locations.getFieldList().get(1);
    }

    public StringField getLocationPostalCodeField() {
        return (StringField) locations.getFieldList().get(2);
    }

    public ReferenceField getLocationCityField() {
        return (ReferenceField) locations.getFieldList().get(3);
    }
}
