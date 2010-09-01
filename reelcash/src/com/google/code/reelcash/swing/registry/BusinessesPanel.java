package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.data.banks.BankAccountNode;
import com.google.code.reelcash.data.business.BusinessNode;
import com.google.code.reelcash.data.business.LegalStatusNode;
import com.google.code.reelcash.data.geo.LocationNode;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.sql.QueryMediator;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;
import java.sql.SQLException;

/**
 * Panel for editing businesses in the database registry.
 * @author andrei.olar
 */
public class BusinessesPanel extends JRegistryPanel {

    private static final long serialVersionUID = 6926911622450127248L;
    private BusinessDisplayFactory factory;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return BusinessNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == factory)
            factory = new BusinessDisplayFactory();
        return factory;
    }

    private class BusinessDisplayFactory extends FieldDisplayFactory {

        {
            BusinessNode node = BusinessNode.getInstance();
            Field field = node.getIdField();
            FieldDisplay display = addFieldDisplayInfo(field);
            display.setVisible(false);

            field = node.getNameField();
            addFieldDisplayInfo(field);

            QueryMediator mediator = new QueryMediator(getDataSource());

            field = node.getBankAccountIdField();
            display = addFieldDisplayInfo(field);
            try {
                BankAccountNode accountNode = BankAccountNode.getInstance();
                initializeReferencedData(accountNode, accountNode.getAccountField(), mediator, display, field);
            }
            catch (SQLException e) {
                showError(e);
            }

            field = node.getLegalStatusIdField();
            display = addFieldDisplayInfo(field);
            try {
                LegalStatusNode legalStatusNode = LegalStatusNode.getInstance();
                initializeReferencedData(legalStatusNode, legalStatusNode.getCodeField(), mediator, display, field);
            }
            catch (SQLException e) {
                showError(e);
            }

            field = node.getLocationIdField();
            display = addFieldDisplayInfo(field);
            try {
                LocationNode locationNode = LocationNode.getInstance();
                initializeReferencedData(locationNode, locationNode.getAddressField(), mediator, display, field);
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
