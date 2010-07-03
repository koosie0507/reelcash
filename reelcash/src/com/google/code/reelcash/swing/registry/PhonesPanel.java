/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.swing.registry;

import com.google.code.reelcash.data.contacts.PhoneNode;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.swing.JRegistryPanel;

/**
 *
 * @author andrei.olar
 */
public class PhonesPanel extends JRegistryPanel {

    private static final long serialVersionUID = 9072674352502094708L;
    private PhoneDisplayFactory factory;

    @Override
    public DataLayoutNode getDataLayoutNode() {
        return PhoneNode.getInstance();
    }

    @Override
    public FieldDisplayFactory getDisplayInfoFactory() {
        if (null == factory)
            factory = new PhoneDisplayFactory();
        return factory;
    }

    private class PhoneDisplayFactory extends FieldDisplayFactory {

        {
            PhoneNode node = PhoneNode.getInstance();

            Field field = node.getIdField();
            FieldDisplay display = FieldDisplay.newInstance(field);
            display.setVisible(false);
            getData().put(field, display);

            field = node.getPhoneField();
            display = FieldDisplay.newInstance(field);
            getData().put(field, display);

            field = node.getCallChargeField();
            display = FieldDisplay.newInstance(field);
            getData().put(field, display);
        }

        @Override
        public FieldDisplay getUIDisplayInfo(Field field) {
            return getData().get(field);
        }
    }
}
