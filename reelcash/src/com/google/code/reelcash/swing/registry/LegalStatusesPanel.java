package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.data.business.LegalStatusNode;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;

/**
 * Provides functionality for CRUD on legal statuses.
 * 
 * @author andrei.olar
 */
public class LegalStatusesPanel extends JRegistryPanel {

    private static final long serialVersionUID = 2575577505353941404L;
    private LegalStatusDisplayFactory factory;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return LegalStatusNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == factory)
            factory = new LegalStatusDisplayFactory();
        return factory;
    }

    private class LegalStatusDisplayFactory extends FieldDisplayFactory {

        {
            LegalStatusNode node = LegalStatusNode.getInstance();

            addFieldDisplayInfo(node.getIdField()).setVisible(false);
            addFieldDisplayInfo(node.getCodeField());
            addFieldDisplayInfo(node.getNameField());
            addFieldDisplayInfo(node.getDescriptionField());
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
