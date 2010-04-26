package com.google.code.reelcash.data.documents;

import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.layout.FlatLayoutNode;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.StringField;

/**
 * Insert some documentation for the class <b>SeriesRangeNode</b> (what's its purpose,
 * why this implementation, that sort of thing).
 *
 * @author andrei.olar
 */
public class SeriesRangeNode extends FlatLayoutNode {
    private static final String TABLE_NAME = "series_ranges";
    private static final Object SYNC_ROOT = new Object();
    private static SeriesRangeNode instance;

    private SeriesRangeNode() {
        super(TABLE_NAME);

        getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
        getFieldList().add(new StringField("prefix", KeyRole.NONE, true));
        getFieldList().add(new IntegerField("min_value", KeyRole.NONE, true));
        getFieldList().add(new IntegerField("max_value", KeyRole.NONE, true));
        getFieldList().add(new IntegerField("inc_step", KeyRole.NONE, true));
        getFieldList().add(new StringField("suffix", KeyRole.NONE, true));
    }

    public static SeriesRangeNode getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new SeriesRangeNode();
        }
        return instance;
    }

    public IntegerField getIdField() {
        return (IntegerField) getFieldList().get(0);
    }

    public StringField getPrefixField() {
        return (StringField) getFieldList().get(1);
    }

    public IntegerField getMinValueField() {
        return (IntegerField) getFieldList().get(2);
    }

    public IntegerField getMaxValueField() {
        return (IntegerField) getFieldList().get(3);
    }

    public IntegerField getIncStepField() {
        return (IntegerField) getFieldList().get(4);
    }

    public StringField getSuffixField() {
        return (StringField) getFieldList().get(5);
    }
}
