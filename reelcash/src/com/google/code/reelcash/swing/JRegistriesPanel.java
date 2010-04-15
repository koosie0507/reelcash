package com.google.code.reelcash.swing;

import com.google.code.reelcash.data.RegistryLayout;
import com.google.code.reelcash.model.DataLayoutTreeModel;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

/**
 * <p>
 * This panel provides a layout for editing registry information with the reelcash application. The user will
 * be allowed to choose the registry information to edit from a tree component located on the left-hand side
 * of the panel. After making the choice, the user will be given the oportunity to edit the chosen type of
 * registry on the right-hand side of the frame.
 * </p>
 * <p>
 * An box - located in the bottom-left corner of the panel - provides information about the selected registry type.
 * </p>
 * @author andrei.olar
 */
public class JRegistriesPanel extends JPanel {

    private static final long serialVersionUID = -1475977611395913485L;
    private static final Object SYNC_ROOT = new Object();
    private static JRegistriesPanel instance;
    private JScrollPane leftContainer;
    private JTree registriesTree;

    private JRegistriesPanel() {
        initializeComponents();
    }

    /**
     * Creates and returns the singleton instance of this class.
     * 
     * @return a singleton instance of this class.
     */
    public static JRegistriesPanel getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new JRegistriesPanel();
        }
        return instance;
    }

    private JScrollPane getLeftContainer() {
        if(null == leftContainer) {
            leftContainer = new JScrollPane(getRegistriesTree());
        }
        return leftContainer;
    }

    private JTree getRegistriesTree() {
        if (null == registriesTree) {
            DataLayoutTreeModel model = new DataLayoutTreeModel(RegistryLayout.getInstance());
            registriesTree = new JTree(model);
        }
        return registriesTree;
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        add(getLeftContainer(), BorderLayout.LINE_START);
    }
}
