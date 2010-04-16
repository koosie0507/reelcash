package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.data.banks.BankNode;
import com.google.code.reelcash.data.geo.LocationNode;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.sql.QueryMediator;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;
import java.sql.SQLException;

/**
 *
 * @author andrei.olar
 */
public class BanksPanel extends JRegistryPanel {

    private static final long serialVersionUID = 5523110628009039137L;
    private BankDisplayFactory factory;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return BankNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == factory)
            factory = new BankDisplayFactory();
        return factory;
    }

    private class BankDisplayFactory extends FieldDisplayFactory {

        {
            BankNode node = BankNode.getInstance();
            Field field = node.getIdField();
            FieldDisplay display = addFieldDisplayInfo(field);
            display.setVisible(false);

            field = node.getLocationIdField();
            display = addFieldDisplayInfo(field);
            QueryMediator mediator = new QueryMediator(getDataSource());
            try {
                LocationNode locationNode = LocationNode.getInstance();
                initializeReferencedData(locationNode, locationNode.getAddressField(), mediator, display, field);
            }
            catch (SQLException e) {
                showError(e);
            }

            field = node.getNameField();
            addFieldDisplayInfo(field);

            field = node.getParentIdField();
            display = addFieldDisplayInfo(field);
            try {
                initializeReferencedData(node, node.getNameField(), mediator, display, field);
            }
            catch (SQLException e) {
                showError(e);
            }
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
