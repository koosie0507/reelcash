package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.data.goods.GoodNode;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.sql.QueryMediator;
import com.google.code.reelcash.data.taxes.ExciseTypeNode;
import com.google.code.reelcash.data.taxes.GoodExciseNode;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;
import java.sql.SQLException;

/**
 * Insert some documentation for the class <b>GoodExcisesPanel</b> (what's its purpose,
 * why this implementation, that sort of thing).
 *
 * @author andrei.olar
 */
public class GoodExcisesPanel extends JRegistryPanel {

    private static final long serialVersionUID = 8856451399217693961L;
    private GoodExciseDisplayFactory instance;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return GoodExciseNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == instance)
            instance = new GoodExciseDisplayFactory();
        return instance;
    }

    private class GoodExciseDisplayFactory extends FieldDisplayFactory {

        {
            GoodExciseNode node = GoodExciseNode.getInstance();
            addFieldDisplayInfo(node.getIdField()).setVisible(false);

            QueryMediator mediator = new QueryMediator(getDataSource());
            Field field = node.getGoodIdField();
            FieldDisplay display = addFieldDisplayInfo(node.getGoodIdField());
            try {
                GoodNode foreignNode = GoodNode.getInstance();
                initializeReferencedData(foreignNode, foreignNode.getCodeField(), mediator, display, field);
            }
            catch (SQLException e) {
                showError(e);
            }
            field = node.getExciseTypeIdField();
            display = addFieldDisplayInfo(node.getExciseTypeIdField());
            try {
                ExciseTypeNode foreignNode = ExciseTypeNode.getInstance();
                initializeReferencedData(foreignNode, foreignNode.getCodeField(), mediator, display, field);
            }
            catch (SQLException e) {
                showError(e);
            }
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
