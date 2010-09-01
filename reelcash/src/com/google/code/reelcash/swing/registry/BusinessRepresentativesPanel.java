package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.data.business.BusinessNode;
import com.google.code.reelcash.data.business.BusinessRepresentativeNode;
import com.google.code.reelcash.data.contacts.ContactNode;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.sql.QueryMediator;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;
import java.sql.SQLException;

/**
 * Panel for editing business reps.
 * 
 * @author andrei.olar
 */
public class BusinessRepresentativesPanel extends JRegistryPanel {

    private static final long serialVersionUID = -2233726463210943854L;
    private BusinessRepresentativeDisplayFactory factory;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return BusinessRepresentativeNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == factory)
            factory = new BusinessRepresentativeDisplayFactory();
        return factory;
    }

    private class BusinessRepresentativeDisplayFactory extends FieldDisplayFactory {

        {
            BusinessRepresentativeNode node = BusinessRepresentativeNode.getInstance();
            QueryMediator mediator = new QueryMediator(getDataSource());

            Field field = node.getIdField();
            FieldDisplay display = addFieldDisplayInfo(field);
            display.setVisible(false);

            field = node.getBusinessIdField();
            display = addFieldDisplayInfo(field);
            try {
                BusinessNode businessNode = BusinessNode.getInstance();
                initializeReferencedData(businessNode, businessNode.getNameField(), mediator, display, field);
            }
            catch (SQLException e) {
                showError(e);
            }

            field = node.getContactIdField();
            display = addFieldDisplayInfo(field);
            try {
                ContactNode contactNode = ContactNode.getInstance();
                initializeReferencedData(contactNode, contactNode.getNameField(), mediator, display, field);
            }
            catch (SQLException e) {
                showError(e);
            }

            field = node.getTextField();
            addFieldDisplayInfo(field);

            field = node.getDescriptionField();
            addFieldDisplayInfo(field);
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
