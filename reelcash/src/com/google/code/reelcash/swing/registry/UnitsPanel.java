package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.data.goods.UnitNode;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;

/**
 * Insert some documentation for the class <b>UnitsPanel</b> (what's its purpose,
 * why this implementation, that sort of thing).
 *
 * @author andrei.olar
 */
public class UnitsPanel extends JRegistryPanel {

    private static final long serialVersionUID = -7675720475971386177L;
    private UnitDisplayFactory instance;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return UnitNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == instance)
            instance = new UnitDisplayFactory();
        return instance;
    }

    private class UnitDisplayFactory extends FieldDisplayFactory {

        {
            UnitNode node = UnitNode.getInstance();
            addFieldDisplayInfo(node.getIdField()).setVisible(false);
            addFieldDisplayInfo(node.getCodeField());
            addFieldDisplayInfo(node.getNameField());
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
