package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.GlobalResources;
import com.google.code.reelcash.data.contacts.ContactLocationNode;
import com.google.code.reelcash.data.contacts.ContactNode;
import com.google.code.reelcash.data.geo.LocationNode;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.sql.QueryMediator;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author andrei.olar
 */
public class ContactLocationsPanel extends JRegistryPanel {

    private static final long serialVersionUID = -6481556512993065656L;
    private ContactLocationDisplayFactory factory;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return ContactLocationNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == factory)
            factory = new ContactLocationDisplayFactory();
        return factory;
    }

    private class ContactLocationDisplayFactory extends FieldDisplayFactory {

        {
            ContactLocationNode node = ContactLocationNode.getInstance();

            Field field = node.getIdField();
            FieldDisplay display = FieldDisplay.newInstance(field);
            display.setVisible(false);
            getData().put(field, display);

            field = node.getContactIdField();
            display = FieldDisplay.newInstance(field);
            QueryMediator mediator = new QueryMediator(getDataSource());
            try {
                ContactNode contactNode = ContactNode.getInstance();
                initializeReferencedData(contactNode, contactNode.getNameField(), mediator, display, field);
            }
            catch (SQLException e) {
                JOptionPane.showMessageDialog(ContactLocationsPanel.this, e.getMessage(),
                        GlobalResources.getString("application_error_title"),
                        JOptionPane.ERROR_MESSAGE);

            }
            getData().put(field, display);

            field = node.getLocationIdField();
            display = FieldDisplay.newInstance(field);
            try {
                LocationNode locationNode = LocationNode.getInstance();
                initializeReferencedData(locationNode, locationNode.getAddressField(), mediator, display, field);
            }
            catch (SQLException e) {
                JOptionPane.showMessageDialog(ContactLocationsPanel.this, e.getMessage(),
                        GlobalResources.getString("application_error_title"),
                        JOptionPane.ERROR_MESSAGE);

            }
            getData().put(field, display);

            field = node.getPriorityField();
            display = FieldDisplay.newInstance(field);
            getData().put(field, display);

            field = node.getSubdivisionField();
            display = FieldDisplay.newInstance(field);
            getData().put(field, display);
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
