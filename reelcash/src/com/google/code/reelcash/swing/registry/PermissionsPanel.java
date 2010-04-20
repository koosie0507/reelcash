package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.permissions.PermissionNode;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;

/**
 * Panel for editing permissions.
 * 
 * @author andrei.olar
 */
public class PermissionsPanel extends JRegistryPanel {

    private static final long serialVersionUID = 8114557711170380207L;
    private PermissionDisplayFactory factory;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return PermissionNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == factory)
            factory = new PermissionDisplayFactory();
        return factory;
    }

    private class PermissionDisplayFactory extends FieldDisplayFactory {

        {
            PermissionNode node = PermissionNode.getInstance();

            addFieldDisplayInfo(node.getIdField()).setVisible(false);
            addFieldDisplayInfo(node.getNameField());
            addFieldDisplayInfo(node.getDescriptionField());
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
