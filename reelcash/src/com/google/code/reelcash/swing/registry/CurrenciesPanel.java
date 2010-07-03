package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.data.banks.CurrencyNode;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;

/**
 * CRUD operations on available currencies.
 *
 * @author andrei.olar
 */
public class CurrenciesPanel extends JRegistryPanel {

    private static final long serialVersionUID = -4432471855517811794L;
    private CurrencyDisplayFactory factory;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return CurrencyNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == factory)
            factory = new CurrencyDisplayFactory();
        return factory;
    }

    private class CurrencyDisplayFactory extends FieldDisplayFactory {

        {
            CurrencyNode node = CurrencyNode.getInstance();
            addFieldDisplayInfo(node.getIdField()).setVisible(false);
            addFieldDisplayInfo(node.getCodeField());
            addFieldDisplayInfo(node.getNameField());
            addFieldDisplayInfo(node.getMustExchangeField());
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
