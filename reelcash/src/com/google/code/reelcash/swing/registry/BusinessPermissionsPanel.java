package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.data.business.BusinessNode;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.permissions.BusinessPermissionNode;
import com.google.code.reelcash.data.permissions.PermissionNode;
import com.google.code.reelcash.data.sql.QueryMediator;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;
import java.sql.SQLException;

/**
 *
 * @author andrei.olar
 */
public class BusinessPermissionsPanel extends JRegistryPanel {

    private static final long serialVersionUID = 7114117684189611622L;
    private BusinessPermissionDisplayFactory factory;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return BusinessPermissionNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == factory)
            factory = new BusinessPermissionDisplayFactory();
        return factory;
    }

    private class BusinessPermissionDisplayFactory extends FieldDisplayFactory {

        {
            BusinessPermissionNode node = BusinessPermissionNode.getInstance();
            QueryMediator mediator = new QueryMediator(getDataSource());

            addFieldDisplayInfo(node.getIdField()).setVisible(false);

            Field field = node.getBusinessIdField();
            FieldDisplay display = addFieldDisplayInfo(field);
            try {
                BusinessNode businessNode = BusinessNode.getInstance();
                initializeReferencedData(businessNode, businessNode.getNameField(), mediator, display, field);
            }
            catch (SQLException e) {
                showError(e);
            }

            field = node.getPermissionIdField();
            display = addFieldDisplayInfo(field);
            try {
                PermissionNode permNode = PermissionNode.getInstance();
                initializeReferencedData(permNode, permNode.getNameField(), mediator, display, field);
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
