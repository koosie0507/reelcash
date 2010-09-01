package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.taxes.TaxTypeNode;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;

/**
 * Insert some documentation for the class <b>TaxTypesPanel</b> (what's its purpose,
 * why this implementation, that sort of thing).
 *
 * @author andrei.olar
 */
public class TaxTypesPanel extends JRegistryPanel {

    private static final long serialVersionUID = -861205174348282571L;
    private TaxTypeDisplayFactory instance;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return TaxTypeNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == instance)
            instance = new TaxTypeDisplayFactory();
        return instance;
    }

    private class TaxTypeDisplayFactory extends FieldDisplayFactory {

        {
            TaxTypeNode node = TaxTypeNode.getInstance();

            addFieldDisplayInfo(node.getIdField()).setVisible(false);
            addFieldDisplayInfo(node.getCodeField());
            addFieldDisplayInfo(node.getNameField());
            addFieldDisplayInfo(node.getIsPercentField());
            addFieldDisplayInfo(node.getValueField());
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
