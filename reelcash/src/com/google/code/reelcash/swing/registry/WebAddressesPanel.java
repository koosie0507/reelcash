package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.data.contacts.WebAddressNode;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;

/**
 *
 * @author andrei.olar
 */
public class WebAddressesPanel extends JRegistryPanel {

    private static final long serialVersionUID = -5389306042219977581L;
    private WebAddressDisplayFactory factory;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return WebAddressNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == factory)
            factory = new WebAddressDisplayFactory();
        return factory;
    }

    private class WebAddressDisplayFactory extends FieldDisplayFactory {

        {
            WebAddressNode node = WebAddressNode.getInstance();

            Field field = node.getIdField();
            FieldDisplay display = FieldDisplay.newInstance(field);
            display.setVisible(false);
            getData().put(field, display);

            field = node.getAddressTypeField();
            display = FieldDisplay.newInstance(field);
            getData().put(field, display);

            field = node.getAddressField();
            display = FieldDisplay.newInstance(field);
            getData().put(field, display);

            field = node.getCustomAddressDenominationField();
            display = FieldDisplay.newInstance(field);
            getData().put(field, display);
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
