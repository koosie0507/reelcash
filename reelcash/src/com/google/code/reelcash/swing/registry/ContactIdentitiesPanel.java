package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.GlobalResources;
import com.google.code.reelcash.data.contacts.ContactIdentityNode;
import com.google.code.reelcash.data.contacts.ContactNode;
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
public class ContactIdentitiesPanel extends JRegistryPanel {

    private static final long serialVersionUID = -3815263754910382441L;
    private ContactIdentityDisplayFactory factory;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return ContactIdentityNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == factory)
            factory = new ContactIdentityDisplayFactory();
        return factory;
    }

    private class ContactIdentityDisplayFactory extends FieldDisplayFactory {

        {
            ContactIdentityNode node = ContactIdentityNode.getInstance();

            Field field = node.getIdField();
            FieldDisplay display = FieldDisplay.newInstance(field);
            display.setVisible(false);
            getData().put(field, display);

            field = node.getContactIdField();
            display = FieldDisplay.newInstance(field);
            try {
                QueryMediator mediator = new QueryMediator(getDataSource());
                ContactNode contactNode = ContactNode.getInstance();
                initializeReferencedData(contactNode, contactNode.getNameField(), mediator, display, field);
            }
            catch (SQLException e) {
                JOptionPane.showMessageDialog(ContactIdentitiesPanel.this, e.getMessage(),
                        GlobalResources.getString("application_error_title"),
                        JOptionPane.ERROR_MESSAGE);

            }
            getData().put(field, display);

            field = node.getIdentityTypeField();
            display = FieldDisplay.newInstance(field);
            getData().put(field, display);

            field = node.getIdentityField1Field();
            display = FieldDisplay.newInstance(field);
            getData().put(field, display);

            field = node.getIdentityField2Field();
            display = FieldDisplay.newInstance(field);
            getData().put(field, display);

            field = node.getIdentityField3Field();
            display = FieldDisplay.newInstance(field);
            getData().put(field, display);

            field = node.getIdentityField4Field();
            display = FieldDisplay.newInstance(field);
            getData().put(field, display);
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
