/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.data.geo.CountryNode;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;

/**
 *
 * @author cusi
 */
public class CountriesPanel extends JRegistryPanel {
    private static final long serialVersionUID = -2976002956602368882L;

    private CountriesDisplayFactory countriesDisplay;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return CountryNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if(null == countriesDisplay)
            countriesDisplay = new CountriesDisplayFactory();
        return countriesDisplay;
    }

    private class CountriesDisplayFactory extends FieldDisplayFactory {

        CountriesDisplayFactory() {
            super();

            CountryNode node = (CountryNode) CountriesPanel.this.getDataLayoutNode();
            FieldDisplay disp = FieldDisplay.newInstance(node.getIdField());
            disp.setVisible(false);
            getData().put(node.getIdField(), disp);
            getData().put(node.getNameField(), FieldDisplay.newInstance(node.getNameField()));
            getData().put(node.getIsoNameField(), FieldDisplay.newInstance(node.getIsoNameField()));
            getData().put(node.getIsoCode2Field(), FieldDisplay.newInstance(node.getIsoCode2Field()));
            getData().put(node.getIsoCode3Field(), FieldDisplay.newInstance(node.getIsoCode3Field()));
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }

    }
}
