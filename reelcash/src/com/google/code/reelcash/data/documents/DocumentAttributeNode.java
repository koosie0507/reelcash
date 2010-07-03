package com.google.code.reelcash.data.documents;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.LeafLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.ReferenceField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 * A document attribute is a custom field allowed by a certain document type. For instance contracts will
 * allow custom attributes such as recurring terms or effectiveness, while receipts don't need any special
 * attributes other than the payed amount. Mind you, these are only examples. Receipts and payments are
 * special cases which are treated in their own right. 
 * 
 * @author andrei.olar
 */
public class DocumentAttributeNode extends LeafLayoutNode {

    private static final String TABLE_NAME = "document_attributes";
    private static final Object SYNC_ROOT = new Object();
    private static DocumentAttributeNode instance;

    private DocumentAttributeNode() {
        super(DocumentTypeNode.getInstance(), TABLE_NAME);
        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new ReferenceField(DocumentTypeNode.getInstance().getIdField(), "document_type_id", true));
        getFieldList().add(new StringField("name", KeyRole.NONE, true));
        getFieldList().add(new StringField("value_type", KeyRole.NONE, true));
        getFieldList().add(new IntegerField("max_length", KeyRole.NONE, false) {

            private static final long serialVersionUID = 2154853950404117274L;

            @Override
            public Object getDefaultValue() {
                return new Integer(10);
            }
        });
        getFieldList().add(new StringField("description", KeyRole.NONE, false));
        getFieldList().add(new StringField("custom_format", KeyRole.NONE, false));
        getFieldList().add(new StringField("check_expression", KeyRole.NONE, false));
    }

    public static DocumentAttributeNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new DocumentAttributeNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public ReferenceField getDocumentTypeIdField() {
        return (ReferenceField) getFieldList().get(1);
    }

    public StringField getNameField() {
        return (StringField) getFieldList().get(2);
    }

    public StringField getValueTypeField() {
        return (StringField) getFieldList().get(3);
    }

    public IntegerField getMaxLengthField() {
        return (IntegerField) getFieldList().get(4);
    }

    public StringField getDescriptionField() {
        return (StringField) getFieldList().get(5);
    }

    public StringField getCustomFormatField() {
        return (StringField) getFieldList().get(6);
    }

    public StringField getCheckExpressionField() {
        return (StringField) getFieldList().get(7);
    }
}
