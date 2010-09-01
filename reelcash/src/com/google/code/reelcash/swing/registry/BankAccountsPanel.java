/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.data.banks.BankAccountNode;
import com.google.code.reelcash.data.banks.BankNode;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.sql.QueryMediator;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;
import java.sql.SQLException;

/**
 *
 * @author andrei.olar
 */
public class BankAccountsPanel extends JRegistryPanel {

    private static final long serialVersionUID = -2389824129592522731L;
    private BankAccountDisplayFactory factory;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return BankAccountNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == factory)
            factory = new BankAccountDisplayFactory();
        return factory;
    }

    private class BankAccountDisplayFactory extends FieldDisplayFactory {

        {
            BankAccountNode node = BankAccountNode.getInstance();
            Field field = node.getIdField();
            FieldDisplay display = addFieldDisplayInfo(field);
            display.setVisible(false);

            field = node.getBankIdField();
            display = addFieldDisplayInfo(field);
            try {
                BankNode bankNode = BankNode.getInstance();
                initializeReferencedData(bankNode, bankNode.getNameField(), new QueryMediator(getDataSource()), display, field);
            }
            catch (SQLException e) {
                showError(e);
            }

            field = node.getAccountField();
            addFieldDisplayInfo(field);
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
