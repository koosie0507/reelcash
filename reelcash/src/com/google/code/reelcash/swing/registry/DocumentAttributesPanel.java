package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.data.documents.DocumentAttributeNode;
import com.google.code.reelcash.data.documents.DocumentTypeNode;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.sql.QueryMediator;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;
import java.sql.SQLException;

/**
 * Panel for CRUD operations on document attributes.
 * 
 * @author andrei.olar
 */
public class DocumentAttributesPanel extends JRegistryPanel {

    private static final long serialVersionUID = 9061383091448345044L;
    private DocumentAttributeDisplayFactory factory;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return DocumentAttributeNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == factory)
            factory = new DocumentAttributeDisplayFactory();
        return factory;
    }

    private class DocumentAttributeDisplayFactory extends FieldDisplayFactory {

        {
            DocumentAttributeNode node = DocumentAttributeNode.getInstance();
            addFieldDisplayInfo(node.getIdField()).setVisible(false);

            Field field = node.getDocumentTypeIdField();
            FieldDisplay display = addFieldDisplayInfo(field);
            try {
                DocumentTypeNode typeNode = DocumentTypeNode.getInstance();
                initializeReferencedData(typeNode, typeNode.getNameField(), new QueryMediator(getDataSource()), display, field);
            }
            catch (SQLException e) {
                showError(e);
            }

            addFieldDisplayInfo(node.getNameField());
            addFieldDisplayInfo(node.getValueTypeField());
            addFieldDisplayInfo(node.getMaxLengthField());
            addFieldDisplayInfo(node.getDescriptionField());
            addFieldDisplayInfo(node.getCustomFormatField());
            addFieldDisplayInfo(node.getCheckExpressionField());
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
