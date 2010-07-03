package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.GlobalResources;
import com.google.code.reelcash.data.contacts.ContactNode;
import com.google.code.reelcash.data.contacts.ContactPhoneNode;
import com.google.code.reelcash.data.contacts.PhoneNode;
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
public class ContactPhonesPanel extends JRegistryPanel {

    private static final long serialVersionUID = 939010645980782116L;
    private ContactPhoneDisplayFactory factory;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return ContactPhoneNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == factory)
            factory = new ContactPhoneDisplayFactory();
        return factory;
    }

    private class ContactPhoneDisplayFactory extends FieldDisplayFactory {

        {
            ContactPhoneNode node = ContactPhoneNode.getInstance();

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
                JOptionPane.showMessageDialog(ContactPhonesPanel.this, e.getMessage(),
                        GlobalResources.getString("application_error_title"),
                        JOptionPane.ERROR_MESSAGE);

            }

            field = node.getPhoneIdField();
            display = addFieldDisplayInfo(field);
            try {
                PhoneNode phoneNode = PhoneNode.getInstance();
                initializeReferencedData(phoneNode, phoneNode.getPhoneField(), mediator, display, field);
            }
            catch (SQLException e) {
                JOptionPane.showMessageDialog(ContactPhonesPanel.this, e.getMessage(),
                        GlobalResources.getString("application_error_title"),
                        JOptionPane.ERROR_MESSAGE);

            }

            field = node.getCategoryField();
            display = addFieldDisplayInfo(field);
            field = node.getPriorityField();
            display = addFieldDisplayInfo(field);
            field = node.getRemarksField();
            display = addFieldDisplayInfo(field);
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
