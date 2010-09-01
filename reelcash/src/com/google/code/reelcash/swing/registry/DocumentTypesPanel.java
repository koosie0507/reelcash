package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.data.documents.DocumentTypeNode;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;

/**
 * Panel for CRUD on document types.
 * 
 * @author andrei.olar
 */
public class DocumentTypesPanel extends JRegistryPanel {

    private static final long serialVersionUID = 3715646320980480492L;
    private DocumentTypeDisplayFactory factory;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return DocumentTypeNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == factory)
            factory = new DocumentTypeDisplayFactory();
        return factory;
    }

    private class DocumentTypeDisplayFactory extends FieldDisplayFactory {

        {
            DocumentTypeNode node = DocumentTypeNode.getInstance();
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
