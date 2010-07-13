package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.taxes.VatTypeNode;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;

/**
 * Insert some documentation for the class <b>VatTypesPanel</b> (what's its purpose,
 * why this implementation, that sort of thing).
 *
 * @author andrei.olar
 */
public class VatTypesPanel extends JRegistryPanel {

    private static final long serialVersionUID = 432146539468919467L;
    private VatTypeDisplayFactory instance;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return VatTypeNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == instance)
            instance = new VatTypeDisplayFactory();
        return instance;
    }

    private class VatTypeDisplayFactory extends FieldDisplayFactory {

        {
            VatTypeNode node = VatTypeNode.getInstance();
            addFieldDisplayInfo(node.getIdField()).setVisible(false);
            addFieldDisplayInfo(node.getCodeField());
            addFieldDisplayInfo(node.getNameField());
            addFieldDisplayInfo(node.getPercentField());
            addFieldDisplayInfo(node.getIsDefaultField());
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
