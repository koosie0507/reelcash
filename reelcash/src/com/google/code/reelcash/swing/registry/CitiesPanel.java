package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.GlobalResources;
import com.google.code.reelcash.data.geo.CityNode;
import com.google.code.reelcash.data.geo.CountyNode;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.sql.QueryMediator;
import com.google.code.reelcash.model.DataRowComboModel;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;
import com.google.code.reelcash.swing.ReferenceFieldCellRenderer;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author cusi
 */
public class CitiesPanel extends JRegistryPanel {

    private static final long serialVersionUID = -4610560191884056847L;
    private FieldDisplayFactory displayInfoFactory;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return CityNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == displayInfoFactory)
            displayInfoFactory = new CitiesDisplayFactory();
        return displayInfoFactory;
    }

    private class CitiesDisplayFactory extends FieldDisplayFactory {

        private QueryMediator queryMediator;

        private QueryMediator getQueryMediator() {
            if (null == queryMediator)
                queryMediator = new QueryMediator(getDataSource());
            return queryMediator;
        }

        CitiesDisplayFactory() {
            super();
            CityNode cityNode = CityNode.getInstance();
            
            Field field = cityNode.getIdField();
            FieldDisplay disp = FieldDisplay.newInstance(field);
            disp.setVisible(false);
            getData().put(field, disp);

            field = cityNode.getCountyIdField();
            disp = FieldDisplay.newInstance(field);
            getData().put(field, disp);
            try {
                CountyNode countyNode = CountyNode.getInstance();
                CitiesPanel.this.initializeReferencedData(countyNode, countyNode.getNameField(), getQueryMediator(), disp, field);
            }
            catch (SQLException e) {
                JOptionPane.showMessageDialog(CitiesPanel.this, e.getMessage(),
                        GlobalResources.getString("application_error_title"),
                        JOptionPane.ERROR_MESSAGE);
            }

            field = cityNode.getNameField();
            disp = FieldDisplay.newInstance(field);
            getData().put(field, disp);

            field = cityNode.getCodeField();
            disp = FieldDisplay.newInstance(field);
            getData().put(field, disp);
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
