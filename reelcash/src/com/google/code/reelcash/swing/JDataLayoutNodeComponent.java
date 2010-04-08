/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.swing;

import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This component creates a panel which is created dynamically as specified by a data
 * layout node.
 *
 * @author andrei.olar
 */
public class JDataLayoutNodeComponent extends JComponent {

    private static final long serialVersionUID = 3984322573736715408L;
    private DataLayoutNode node;
    private JPanel contentPane;
    private GridBagLayout layout;

    public JDataLayoutNodeComponent() {
        contentPane = new JPanel(getLayout());
    }

    @Override
    public GridBagLayout getLayout() {
        if (null == layout)
            layout = new GridBagLayout();
        return layout;
    }

    private static GridBagConstraints getLabelConstraints(int gridy) {
        return new GridBagConstraints(0, gridy, 1, 1, 0.1, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 5), 0, 0);
    }

    private static GridBagConstraints getComponentConstraints(int gridy) {
        return new GridBagConstraints(1, gridy, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 5), 0, 0);
    }

    private void initializeComponent(DataLayoutNode node) {
        contentPane.removeAll();

        setName(node.getName());
        int gridy = 0;
        for (Field field : node) {
            UIDisplayInfo dispInfo = UIDisplayInfo.newInstance(field);
            JLabel label = dispInfo.createDescriptionLabel();
            JComponent component = dispInfo.getDisplayComponent();
            contentPane.add(label, getLabelConstraints(gridy));
            contentPane.add(component, getComponentConstraints(gridy));
            gridy++;
        }
        
        getLayout().invalidateLayout(contentPane);
    }

    /**
     * Returns the layout node responsable for metadata information.
     *
     * @return layout node.
     */
    public DataLayoutNode getNode() {
        return node;
    }

    /**
     * Sets the layout node responsable for metadata information provided to this component.
     * @param node the node 
     */
    public void setNode(DataLayoutNode node) {
        this.node = node;
        initializeComponent(node);
    }
}
