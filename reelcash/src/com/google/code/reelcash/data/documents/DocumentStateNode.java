/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data.documents;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.RootLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 *
 * @author cusi
 */
public class DocumentStateNode extends RootLayoutNode {

    private static final String TABLE_NAME = "document_states";
    private static final Object SYNC_ROOT = new Object();
    private static DocumentStateNode instance;

    private DocumentStateNode() {
        super(TABLE_NAME);

        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new StringField("name", KeyRole.UNIQUE, true));
        getFieldList().add(new StringField("description", KeyRole.NONE, true));
    }

    public static DocumentStateNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance) {
                instance = new DocumentStateNode();
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

