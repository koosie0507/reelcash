package com.google.code.reelcash.swing;

import com.google.code.reelcash.data.RegistryLayout;
import com.google.code.reelcash.data.banks.BankAccountNode;
import com.google.code.reelcash.data.banks.BankNode;
import com.google.code.reelcash.data.business.BusinessNode;
import com.google.code.reelcash.data.business.BusinessRepresentativeNode;
import com.google.code.reelcash.data.business.FiscalIdentificationNode;
import com.google.code.reelcash.data.business.LegalStatusNode;
import com.google.code.reelcash.data.contacts.ContactIdentityNode;
import com.google.code.reelcash.data.contacts.ContactLocationNode;
import com.google.code.reelcash.data.contacts.ContactNode;
import com.google.code.reelcash.data.contacts.ContactPhoneNode;
import com.google.code.reelcash.data.contacts.ContactWebAddressNode;
import com.google.code.reelcash.data.contacts.PhoneNode;
import com.google.code.reelcash.data.contacts.WebAddressNode;
import com.google.code.reelcash.data.geo.CityNode;
import com.google.code.reelcash.data.geo.CountryNode;
import com.google.code.reelcash.data.geo.CountyNode;
import com.google.code.reelcash.data.geo.LocationNode;
import com.google.code.reelcash.data.geo.RegionNode;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.permissions.BusinessPermissionNode;
import com.google.code.reelcash.data.permissions.PermissionNode;
import com.google.code.reelcash.model.DataLayoutTreeModel;
import com.google.code.reelcash.swing.registry.BankAccountsPanel;
import com.google.code.reelcash.swing.registry.BanksPanel;
import com.google.code.reelcash.swing.registry.BusinessPermissionsPanel;
import com.google.code.reelcash.swing.registry.BusinessRepresentativesPanel;
import com.google.code.reelcash.swing.registry.BusinessesPanel;
import com.google.code.reelcash.swing.registry.CitiesPanel;
import com.google.code.reelcash.swing.registry.ContactIdentitiesPanel;
import com.google.code.reelcash.swing.registry.ContactLocationsPanel;
import com.google.code.reelcash.swing.registry.ContactPhonesPanel;
import com.google.code.reelcash.swing.registry.ContactWebAddressesPanel;
import com.google.code.reelcash.swing.registry.ContactsPanel;
import com.google.code.reelcash.swing.registry.CountiesPanel;
import com.google.code.reelcash.swing.registry.CountriesPanel;
import com.google.code.reelcash.swing.registry.FiscalIdentificationPanel;
import com.google.code.reelcash.swing.registry.LegalStatusesPanel;
import com.google.code.reelcash.swing.registry.LocationsPanel;
import com.google.code.reelcash.swing.registry.PermissionsPanel;
import com.google.code.reelcash.swing.registry.PhonesPanel;
import com.google.code.reelcash.swing.registry.RegionsPanel;
import com.google.code.reelcash.swing.registry.WebAddressesPanel;
import java.awt.BorderLayout;
import java.util.Hashtable;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

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
    private final Hashtable<DataLayoutNode, JRegistryPanel> nodePanelMappings;
    private JScrollPane leftContainer;
    private JSplitPane mainContainer;
    private JTree registriesTree;

    private JRegistriesPanel() {
        nodePanelMappings = new Hashtable<DataLayoutNode, JRegistryPanel>(23);
        initializeComponents();
        initializeNodePanelMappings();
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
        if (null == leftContainer)
            leftContainer = new JScrollPane(getRegistriesTree());
        return leftContainer;
    }

    private JSplitPane getMainContainer() {
        if (null == mainContainer)
            mainContainer = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, getLeftContainer(), new JPanel());
        return mainContainer;
    }

    private JTree getRegistriesTree() {
        if (null == registriesTree) {
            DataLayoutTreeModel model = new DataLayoutTreeModel(RegistryLayout.getInstance());
            registriesTree = new JTree(model);
            registriesTree.addTreeSelectionListener(new RegistryTreeSelectionListener());
        }
        return registriesTree;
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());
        add(getMainContainer(), BorderLayout.CENTER);
    }

    private void initializeNodePanelMappings() {
        nodePanelMappings.put(CountryNode.getInstance(), new CountriesPanel());
        nodePanelMappings.put(RegionNode.getInstance(), new RegionsPanel());
        nodePanelMappings.put(CountyNode.getInstance(), new CountiesPanel());
        nodePanelMappings.put(CityNode.getInstance(), new CitiesPanel());
        nodePanelMappings.put(LocationNode.getInstance(), new LocationsPanel());
        nodePanelMappings.put(ContactNode.getInstance(), new ContactsPanel());
        nodePanelMappings.put(PhoneNode.getInstance(), new PhonesPanel());
        nodePanelMappings.put(WebAddressNode.getInstance(), new WebAddressesPanel());
        nodePanelMappings.put(ContactLocationNode.getInstance(), new ContactLocationsPanel());
        nodePanelMappings.put(ContactIdentityNode.getInstance(), new ContactIdentitiesPanel());
        nodePanelMappings.put(ContactPhoneNode.getInstance(), new ContactPhonesPanel());
        nodePanelMappings.put(ContactWebAddressNode.getInstance(), new ContactWebAddressesPanel());
        nodePanelMappings.put(BankNode.getInstance(), new BanksPanel());
        nodePanelMappings.put(BankAccountNode.getInstance(), new BankAccountsPanel());
        nodePanelMappings.put(LegalStatusNode.getInstance(), new LegalStatusesPanel());
        nodePanelMappings.put(BusinessNode.getInstance(), new BusinessesPanel());
        nodePanelMappings.put(BusinessRepresentativeNode.getInstance(), new BusinessRepresentativesPanel());
        nodePanelMappings.put(FiscalIdentificationNode.getInstance(), new FiscalIdentificationPanel());
        nodePanelMappings.put(PermissionNode.getInstance(), new PermissionsPanel());
        nodePanelMappings.put(BusinessPermissionNode.getInstance(), new BusinessPermissionsPanel());
    }

    private class RegistryTreeSelectionListener implements TreeSelectionListener {

        public void valueChanged(TreeSelectionEvent e) {
            TreePath[] selPaths = e.getPaths();
            TreePath selectedPath = null;
            int selAddCount = 0;
            for (int i = selPaths.length - 1; i > -1; i--) {
                if (e.isAddedPath(i)) {
                    selAddCount++;
                    selectedPath = selPaths[i];
                }
            }

            JPanel rightComponent = new JPanel();
            JSplitPane container = JRegistriesPanel.this.getMainContainer();
            if (null != selectedPath && selAddCount < 2) {
                Object lastComponent = selectedPath.getLastPathComponent();
                if (lastComponent instanceof DataLayoutNode && JRegistriesPanel.this.nodePanelMappings.containsKey((DataLayoutNode) lastComponent)) {
                    rightComponent = JRegistriesPanel.this.nodePanelMappings.get((DataLayoutNode) lastComponent);
                    ((JRegistryPanel) rightComponent).getDatabaseAdapter().readAll();
                }
            }
            if (container.getRightComponent() instanceof JRegistryPanel)
                ((JRegistryPanel) container.getRightComponent()).getTableModel().clearQuietly();
            container.setRightComponent(rightComponent);
        }
    }
}
