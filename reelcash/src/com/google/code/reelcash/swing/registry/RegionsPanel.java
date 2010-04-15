package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.GlobalResources;
import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.geo.CountryNode;
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
 *
 * @author cusi
 */
public class RegionsPanel extends JRegistryPanel {

    private static final long serialVersionUID = -7314012231968122983L;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return RegionNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        return new RegionsDisplayFactory();
    }

    private class RegionsDisplayFactory extends FieldDisplayFactory {

        RegionsDisplayFactory() {
            super();

            Field field = RegionNode.getInstance().getIdField();
            FieldDisplay disp = FieldDisplay.newInstance(field);
            disp.setVisible(false);
            getData().put(field, disp);

            field = RegionNode.getInstance().getCountryIdField();
            disp = FieldDisplay.newInstance(field);
            getData().put(field, disp);
            // combo boxes are a bit more complicated
            QueryMediator mediator = new QueryMediator(getDataSource());
            try {
                List<DataRow> rows = mediator.fetchAll(CountryNode.getInstance());
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
                JOptionPane.showMessageDialog(RegionsPanel.this, e.getMessage(),
                        GlobalResources.getString("application_error_title"),
                        JOptionPane.ERROR_MESSAGE);
            }

            field = RegionNode.getInstance().getNameField();
            disp = FieldDisplay.newInstance(field);
            getData().put(field, disp);

            field = RegionNode.getInstance().getCodeField();
            disp = FieldDisplay.newInstance(field);
            getData().put(field, disp);
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
