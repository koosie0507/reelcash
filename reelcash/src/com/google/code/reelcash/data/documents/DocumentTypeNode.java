package com.google.code.reelcash.data.documents;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.RootLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 * Represents the structure of a document type. 
 * 
 * @author cusi
 */
public class DocumentTypeNode extends RootLayoutNode {

    private static final String TABLE_NAME = "document_types";
    private static final Object SYNC_ROOT = new Object();
    private static DocumentTypeNode instance;

    private DocumentTypeNode() {
        super(TABLE_NAME);

        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new StringField("name", KeyRole.UNIQUE, true));
        getFieldList().add(new StringField("description", KeyRole.NONE, true));
    }

    public static DocumentTypeNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance) {
                instance = new DocumentTypeNode();
            }
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public StringField getNameField() {
        return (StringField) getFieldList().get(1);
    }

    public StringField getDescriptionField() {
        return (StringField) getFieldList().get(2);
    }
}
