package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.data.documents.DocumentStateNode;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;

/**
 *
 * @author andrei.olar
 */
public class DocumentStatesPanel extends JRegistryPanel {

    private static final long serialVersionUID = 3715646320980480492L;
    private DocumentStateDisplayFactory factory;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return DocumentStateNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == factory)
            factory = new DocumentStateDisplayFactory();
        return factory;
    }

    private class DocumentStateDisplayFactory extends FieldDisplayFactory {

        {
            DocumentStateNode node = DocumentStateNode.getInstance();
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
