/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.GlobalResources;
import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.DataRowComboModel;
import com.google.code.reelcash.data.geo.CountiesLayout;
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
 * Provides a way to edit counties.
 *
 * @author cusi
 */
public class CountiesPanel extends JRegistryPanel {

    private CountiesLayout layout;
    private FieldDisplayFactory displayInfoFactory;

    private CountiesLayout getCountiesLayout() {
        if (null == layout) {
            layout = new CountiesLayout();
        }
        return layout;
    }

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return getCountiesLayout().getCounties();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if(null == displayInfoFactory)
            displayInfoFactory = new CountiesDisplayFactory();
        return displayInfoFactory;
    }

    private class CountiesDisplayFactory extends FieldDisplayFactory {
                CountiesDisplayFactory() {
            super();

            Field field = CountiesPanel.this.getCountiesLayout().getRegionIdField();
            FieldDisplay disp = FieldDisplay.newInstance(field);
            disp.setVisible(false);
            getData().put(field, disp);

            field = CountiesPanel.this.getCountiesLayout().getCountyRegionField();
            disp = FieldDisplay.newInstance(field);
            getData().put(field, disp);
            // combo boxes are a bit more complicated
            QueryMediator mediator = new QueryMediator(getDataSource());
            DataLayoutNode regions = CountiesPanel.this.getCountiesLayout().getRegions();
            try {
                List<DataRow> rows = mediator.fetchAll(regions);
                DataRowComboModel model = new DataRowComboModel(regions);
                ((JComboBox) disp.getDisplayComponent()).setModel(model);
                model.setDisplayMemberIndex(1);
                ((JComboBox) disp.getDisplayComponent()).setRenderer(
                        new ComboListCellRenderer(model.getDisplayMemberIndex()));
                model.fill(rows);

                getDataTable().getColumn(field.getName()).setCellRenderer(
                        new ReferenceFieldCellRenderer(0, 1, rows));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(CountiesPanel.this, e.getMessage(),
                        GlobalResources.getString("application_error_title"),
                        JOptionPane.ERROR_MESSAGE);
            }

            field = CountiesPanel.this.getCountiesLayout().getCountyNameField();
            disp = FieldDisplay.newInstance(field);
            getData().put(field, disp);

            field = CountiesPanel.this.getCountiesLayout().getCountyCodeField();
            disp = FieldDisplay.newInstance(field);
            getData().put(field, disp);
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }

    }
}
