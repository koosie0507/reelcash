package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.data.documents.SeriesRangeNode;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;

/**
 * Insert some documentation for the class <b>SeriesRangesPanel</b> (what's its purpose,
 * why this implementation, that sort of thing).
 *
 * @author andrei.olar
 */
public class SeriesRangesPanel extends JRegistryPanel {

    private static final long serialVersionUID = 2392612791201480757L;
    private SeriesRangeDisplayFactory instance;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return SeriesRangeNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == instance)
            instance = new SeriesRangeDisplayFactory();
        return instance;
    }

    private class SeriesRangeDisplayFactory extends FieldDisplayFactory {

        {
            SeriesRangeNode node = SeriesRangeNode.getInstance();
            addFieldDisplayInfo(node.getIdField()).setVisible(false);
            addFieldDisplayInfo(node.getPrefixField());
            addFieldDisplayInfo(node.getMinValueField());
            addFieldDisplayInfo(node.getCounterField());
            addFieldDisplayInfo(node.getIncStepField());
            addFieldDisplayInfo(node.getMaxValueField());
            addFieldDisplayInfo(node.getSuffixField());
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
