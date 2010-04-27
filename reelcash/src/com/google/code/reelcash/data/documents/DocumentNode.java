package com.google.code.reelcash.data.documents;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.business.BusinessNode;
import com.google.code.reelcash.data.layout.FlatLayoutNode;
import com.google.code.reelcash.data.layout.fields.DateField;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.ReferenceField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 * Insert some documentation for the class <b>DocumentNode</b> (what's its purpose,
 * why this implementation, that sort of thing).
 *
 * @author andrei.olar
 */
public class DocumentNode extends FlatLayoutNode {

    private static final String TABLE_NAME = "documents";
    private static final Object SYNC_ROOT = new Object();
    private static DocumentNode instance;

    private DocumentNode() {
        super(TABLE_NAME);

        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new StringField("number", KeyRole.UNIQUE, true));
        getFieldList().add(new ReferenceField(DocumentTypeNode.getInstance().getIdField(), "type_id", true));
        getFieldList().add(new ReferenceField(BusinessNode.getInstance().getIdField(), "issuer_id", true));
        getFieldList().add(new ReferenceField(BusinessNode.getInstance().getIdField(), "recipient_id", true));
        getFieldList().add(new ReferenceField(DocumentStateNode.getInstance().getIdField(), "state_id", true));
        getFieldList().add(new DateField("create_date", KeyRole.NONE, true));
        getFieldList().add(new DateField("date_issued", KeyRole.NONE, true));
        getFieldList().add(new DateField("date_due", KeyRole.NONE, false));
    }

    public static DocumentNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new DocumentNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public StringField getNumberField() {
        return (StringField) getFieldList().get(1);
    }

    public ReferenceField getTypeIdField() {
        return (ReferenceField) getFieldList().get(2);
    }

    public ReferenceField getIssuerIdField() {
        return (ReferenceField) getFieldList().get(3);
    }

    public ReferenceField getRecipientIdField() {
        return (ReferenceField) getFieldList().get(4);
    }

    public ReferenceField getStateIdField() {
        return (ReferenceField) getFieldList().get(5);
    }

    public DateField getCreateDateField() {
        return (DateField) getFieldList().get(6);
    }

    public DateField getDateIssuedField() {
        return (DateField) getFieldList().get(7);
    }

    public DateField getDateDueField() {
        return (DateField) getFieldList().get(8);
    }
}
