package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.taxes.ExciseTypeNode;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;

/**
 * Insert some documentation for the class <b>ExciseTypesPanel</b> (what's its purpose,
 * why this implementation, that sort of thing).
 *
 * @author andrei.olar
 */
public class ExciseTypesPanel extends JRegistryPanel {

    private static final long serialVersionUID = 103555227625067998L;
    private ExciseTypeDisplayFactory instance;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return ExciseTypeNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == instance)
            instance = new ExciseTypeDisplayFactory();
        return instance;
    }

    private class ExciseTypeDisplayFactory extends FieldDisplayFactory {

        {
            ExciseTypeNode node = ExciseTypeNode.getInstance();
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
