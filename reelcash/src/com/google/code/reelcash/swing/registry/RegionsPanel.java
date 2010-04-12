/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.GlobalResources;
import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.DataRowComboModel;
import com.google.code.reelcash.data.geo.RegionsLayout;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.sql.QueryMediator;
import com.google.code.reelcash.swing.ComboListCellRenderer;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;
import com.google.code.reelcash.swing.ReferenceFieldCellRenderer;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author cusi
 */
public class RegionsPanel extends JRegistryPanel {

    private RegionsLayout layout;

    private RegionsLayout getRegionsLayout() {
        if (null == layout) {
            layout = new RegionsLayout();
        }
        return layout;
    }

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return getRegionsLayout().getRegions();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        return new RegionsDisplayFactory();
    }

    private class RegionsDisplayFactory extends FieldDisplayFactory {

        RegionsDisplayFactory() {
            super();

            Field field = RegionsPanel.this.getRegionsLayout().getRegionIdField();
            FieldDisplay disp = FieldDisplay.newInstance(field);
            disp.setVisible(false);
            getData().put(field, disp);

            field = RegionsPanel.this.getRegionsLayout().getRegionCountryField();
            disp = FieldDisplay.newInstance(field);
            getData().put(field, disp);
            // combo boxes are a bit more complicated
            QueryMediator mediator = new QueryMediator(getDataSource());
            DataLayoutNode countries = RegionsPanel.this.getRegionsLayout().getCountries();
            try {
                List<DataRow> rows = mediator.fetchAll(countries);
                DataRowComboModel model = new DataRowComboModel(countries);
                ((JComboBox) disp.getDisplayComponent()).setModel(model);
                model.setDisplayMemberIndex(1);
                ((JComboBox) disp.getDisplayComponent()).setRenderer(
                        new ComboListCellRenderer(model.getDisplayMemberIndex()));
                model.fill(rows);
                
                getDataTable().getColumn(field.getName()).setCellRenderer(
                        new ReferenceFieldCellRenderer(0, 1, rows));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(RegionsPanel.this, e.getMessage(),
                        GlobalResources.getString("application_error_title"),
                        JOptionPane.ERROR_MESSAGE);
            }

            field = RegionsPanel.this.getRegionsLayout().getRegionNameField();
            disp = FieldDisplay.newInstance(field);
            getData().put(field, disp);

            field = RegionsPanel.this.getRegionsLayout().getRegionCodeField();
            disp = FieldDisplay.newInstance(field);
            getData().put(field, disp);
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
