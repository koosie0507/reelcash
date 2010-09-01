package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.data.business.BusinessNode;
import com.google.code.reelcash.data.business.FiscalIdentificationNode;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.sql.QueryMediator;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;
import java.sql.SQLException;

/**
 * Fiscal identification panel.
 * @author andrei.olar
 */
public class FiscalIdentificationPanel extends JRegistryPanel {

    private static final long serialVersionUID = -8801517523404329640L;
    private FiscalIdentificationDisplayFactory factory;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return FiscalIdentificationNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == factory)
            factory = new FiscalIdentificationDisplayFactory();
        return factory;
    }

    private class FiscalIdentificationDisplayFactory extends FieldDisplayFactory {

        {
            FiscalIdentificationNode node = FiscalIdentificationNode.getInstance();

            Field field = node.getIdField();
            FieldDisplay display = addFieldDisplayInfo(field);
            display.setVisible(false);

            field = node.getBusinessIdField();
            display = addFieldDisplayInfo(field);
            try {
                BusinessNode businessNode = BusinessNode.getInstance();
                initializeReferencedData(businessNode, businessNode.getNameField(), new QueryMediator(getDataSource()),
                        display, field);
            }
            catch (SQLException e) {
                showError(e);
            }

            addFieldDisplayInfo(node.getNameField());
            addFieldDisplayInfo(node.getValueField());
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
