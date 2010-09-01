package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.GlobalResources;
import com.google.code.reelcash.data.contacts.ContactNode;
import com.google.code.reelcash.data.contacts.ContactWebAddressNode;
import com.google.code.reelcash.data.contacts.WebAddressNode;
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
public class ContactWebAddressesPanel extends JRegistryPanel {

    private static final long serialVersionUID = 939010645980782116L;
    private ContactWebAddressDisplayFactory factory;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return ContactWebAddressNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == factory)
            factory = new ContactWebAddressDisplayFactory();
        return factory;
    }

    private class ContactWebAddressDisplayFactory extends FieldDisplayFactory {

        {
            ContactWebAddressNode node = ContactWebAddressNode.getInstance();

            Field field = node.getIdField();
            FieldDisplay display = addFieldDisplayInfo(field);
            display.setVisible(false);

            QueryMediator mediator = new QueryMediator(getDataSource());
            field = node.getContactIdField();
            display = addFieldDisplayInfo(field);
            try {
                ContactNode contactNode = ContactNode.getInstance();
                initializeReferencedData(contactNode, contactNode.getNameField(), mediator, display, field);
            }
            catch (SQLException e) {
                JOptionPane.showMessageDialog(ContactWebAddressesPanel.this, e.getMessage(),
                        GlobalResources.getString("application_error_title"),
                        JOptionPane.ERROR_MESSAGE);

            }

            field = node.getWebAddressIdField();
            display = addFieldDisplayInfo(field);
            try {
                WebAddressNode webAddressNode = WebAddressNode.getInstance();
                initializeReferencedData(webAddressNode, webAddressNode.getAddressField(), mediator, display, field);
            }
            catch (SQLException e) {
                JOptionPane.showMessageDialog(ContactWebAddressesPanel.this, e.getMessage(),
                        GlobalResources.getString("application_error_title"),
                        JOptionPane.ERROR_MESSAGE);

            }
            field = node.getPriorityField();
            display = addFieldDisplayInfo(field);
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
