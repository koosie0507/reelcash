/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.GlobalResources;
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
public class ContactsPanel extends JRegistryPanel {

    private static final long serialVersionUID = -6705949886969090527L;
    private ContactDisplayFactory factory;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return ContactNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == factory)
            factory = new ContactDisplayFactory();
        return factory;
    }

    private class ContactDisplayFactory extends FieldDisplayFactory {

        {
            ContactNode node = ContactNode.getInstance();
            Field contactField = node.getIdField();
            FieldDisplay display = FieldDisplay.newInstance(contactField);
            display.setVisible(false);
            getData().put(contactField, display);

            contactField = node.getNameField();
            getData().put(contactField, FieldDisplay.newInstance(contactField));

            contactField = node.getSurnameField();
            getData().put(contactField, FieldDisplay.newInstance(contactField));

            contactField = node.getLocationIdField();
            display = FieldDisplay.newInstance(contactField);
            try {
                QueryMediator mediator = new QueryMediator(ContactsPanel.this.getDataSource());
                LocationNode locationNode = LocationNode.getInstance();
                initializeReferencedData(locationNode, locationNode.getAddressField(), mediator, display, contactField);
            }
            catch (SQLException e) {
                JOptionPane.showMessageDialog(ContactsPanel.this, e.getMessage(),
                        GlobalResources.getString("application_error_title"),
                        JOptionPane.ERROR_MESSAGE);

            }
             getData().put(contactField, display);
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
