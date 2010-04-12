/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.GlobalResources;
import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.DataRowComboModel;
import com.google.code.reelcash.data.geo.CitiesLayout;
import com.google.code.reelcash.data.geo.CitiesLayout;
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
public class CitiesPanel extends JRegistryPanel {
    private CitiesLayout layout;
    private FieldDisplayFactory displayInfoFactory;

    private CitiesLayout getCitiesLayout() {
        if (null == layout) {
            layout = new CitiesLayout();
        }
        return layout;
    }

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return getCitiesLayout().getCities();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if(null == displayInfoFactory)
            displayInfoFactory = new CitiesDisplayFactory();
        return displayInfoFactory;
    }

    private class CitiesDisplayFactory extends FieldDisplayFactory {
                CitiesDisplayFactory() {
            super();

            Field field = CitiesPanel.this.getCitiesLayout().getCountyIdField();
            FieldDisplay disp = FieldDisplay.newInstance(field);
            disp.setVisible(false);
            getData().put(field, disp);

            field = CitiesPanel.this.getCitiesLayout().getCityCountyField();
            disp = FieldDisplay.newInstance(field);
            getData().put(field, disp);
            // combo boxes are a bit more complicated
            QueryMediator mediator = new QueryMediator(getDataSource());
            DataLayoutNode counties = CitiesPanel.this.getCitiesLayout().getCounties();
            try {
                List<DataRow> rows = mediator.fetchAll(counties);
                DataRowComboModel model = new DataRowComboModel(counties);
                ((JComboBox) disp.getDisplayComponent()).setModel(model);
                model.setDisplayMemberIndex(1);
                ((JComboBox) disp.getDisplayComponent()).setRenderer(
                        new ComboListCellRenderer(model.getDisplayMemberIndex()));
                model.fill(rows);

                getDataTable().getColumn(field.getName()).setCellRenderer(
                        new ReferenceFieldCellRenderer(0, 1, rows));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(CitiesPanel.this, e.getMessage(),
                        GlobalResources.getString("application_error_title"),
                        JOptionPane.ERROR_MESSAGE);
            }

            field = CitiesPanel.this.getCitiesLayout().getCityNameField();
            disp = FieldDisplay.newInstance(field);
            getData().put(field, disp);

            field = CitiesPanel.this.getCitiesLayout().getCityCodeField();
            disp = FieldDisplay.newInstance(field);
            getData().put(field, disp);
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }

    }
}
