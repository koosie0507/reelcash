package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.GlobalResources;
import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.geo.CountyNode;
import com.google.code.reelcash.data.geo.RegionNode;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.sql.QueryMediator;
import com.google.code.reelcash.model.DataRowComboModel;
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

    private static final long serialVersionUID = -2794114529164362321L;
    private FieldDisplayFactory displayInfoFactory;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return CountyNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == displayInfoFactory)
            displayInfoFactory = new CountiesDisplayFactory();
        return displayInfoFactory;
    }

    private class CountiesDisplayFactory extends FieldDisplayFactory {

        CountiesDisplayFactory() {
            super();

            Field field = CountyNode.getInstance().getIdField();
            FieldDisplay disp = FieldDisplay.newInstance(field);
            disp.setVisible(false);
            getData().put(field, disp);

            field = CountyNode.getInstance().getRegionIdField();
            disp = FieldDisplay.newInstance(field);
            getData().put(field, disp);
            // combo boxes are a bit more complicated
            QueryMediator mediator = new QueryMediator(getDataSource());
            try {
                List<DataRow> rows = mediator.fetchAll(RegionNode.getInstance());
                DataRowComboModel model = new DataRowComboModel();
                ((JComboBox) disp.getDisplayComponent()).setModel(model);
                model.setDisplayMemberIndex(1);
                ((JComboBox) disp.getDisplayComponent()).setRenderer(
                        new ComboListCellRenderer(model.getDisplayMemberIndex()));
                model.fill(rows);

                getDataTable().getColumn(field.getName()).setCellRenderer(
                        new ReferenceFieldCellRenderer(0, 1, rows));
            }
            catch (SQLException e) {
                JOptionPane.showMessageDialog(CountiesPanel.this, e.getMessage(),
                        GlobalResources.getString("application_error_title"),
                        JOptionPane.ERROR_MESSAGE);
            }

            field = CountyNode.getInstance().getNameField();
            disp = FieldDisplay.newInstance(field);
            getData().put(field, disp);

            field = CountyNode.getInstance().getCodeField();
            disp = FieldDisplay.newInstance(field);
            getData().put(field, disp);
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
