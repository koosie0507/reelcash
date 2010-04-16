package com.google.code.reelcash.swing;

import com.google.code.reelcash.GlobalResources;
import com.google.code.reelcash.Log;
import com.google.code.reelcash.ReelcashException;
import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.ReelcashDataSource;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.sql.QueryMediator;
import com.google.code.reelcash.model.DataRowComboModel;
import com.google.code.reelcash.model.DataRowTableModel;
import com.google.code.reelcash.model.DataRowTableModelDatabaseAdapter;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

/**
 * The registry panel component displays data from a registry. It features a toolbar, a content area where it
 * displays the data from the database and a component for creating new entities in the database.
 * 
 * @author andrei.olar
 */
public abstract class JRegistryPanel extends JPanel implements ListSelectionListener {

    private static final long serialVersionUID = -1522626960763797947L;
    private static final String ITEM_CARD_NAME = "item";
    private static final String TABLE_CARD_NAME = "table";
    private String caption;
    private CardLayout contentLayout;
    private JPanel contentPane;
    private DataRowTableModelDatabaseAdapter databaseAdapter;
    private DataSource dataSource;
    private JTable dataTable;
    private RegistryPanelMode mode;
    private JDataLayoutNodeComponent itemComponent;
    private DataRowTableModel tableModel;
    private TitledBorder titleBorder;
    private JToggleButton toggleInsert;
    private JToggleButton toggleUpdate;
    private JToolBar toolbar;
    private int selectedIndex;
    private ItemComponentDataActionAdapter adapter;

    public JRegistryPanel() {
        this(null);
    }

    public JRegistryPanel(DataSource dataSource) {
        this.dataSource = dataSource;
        mode = RegistryPanelMode.DEFAULT;
        selectedIndex = -1;
        adapter = new ItemComponentDataActionAdapter();
        initializeComponent();
    }

    public String getCaption() {
        if (null == caption)
            caption = "";
        return caption;
    }

    private CardLayout getContentLayout() {
        if (null == contentLayout)
            contentLayout = new CardLayout();
        return contentLayout;
    }

    private JPanel getContentPane() {
        if (null == contentPane) {
            contentPane = new JPanel(getContentLayout());

            JScrollPane tableScrollPane = new JScrollPane(getDataTable());
            contentPane.add(tableScrollPane, TABLE_CARD_NAME);

            JScrollPane itemScrollPane = new JScrollPane(getItemComponent());
            itemScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            contentPane.add(itemScrollPane, ITEM_CARD_NAME);

            getContentLayout().show(contentPane, TABLE_CARD_NAME);
        }
        return contentPane;
    }

    /**
     * Returns the data source. If a data source hasn't been set prior to the first call to
     * this method the default Reelcash data source is used.
     *
     * @return a data source.
     */
    public DataSource getDataSource() {
        if (null == dataSource)
            dataSource = ReelcashDataSource.getInstance();
        return dataSource;
    }

    public JTable getDataTable() {
        if (null == dataTable) {
            dataTable = new JTable(getTableModel());
            initializeTableColumns();
            dataTable.getTableHeader().setVisible(true);
            dataTable.getSelectionModel().addListSelectionListener(this);
        }
        return dataTable;
    }

    public abstract DataLayoutNode getDataLayoutNode();

    public DataRowTableModelDatabaseAdapter getDatabaseAdapter() {
        if (null == databaseAdapter)
            databaseAdapter = new DataRowTableModelDatabaseAdapter(getDataSource(), getTableModel());
        return databaseAdapter;
    }

    public abstract FieldDisplayFactory getDisplayInfoFactory();

    public JDataLayoutNodeComponent getItemComponent() {
        if (null == itemComponent) {
            itemComponent = new JDataLayoutNodeComponent();
            itemComponent.addDataActionListener(adapter);
            itemComponent.setMaximumSize(getMaximumSize());
            itemComponent.setMinimumSize(getMinimumSize());
            itemComponent.setPreferredSize(getPreferredSize());
            itemComponent.setNode(getDataLayoutNode(), getDisplayInfoFactory());
        }
        return itemComponent;
    }

    public RegistryPanelMode getMode() {
        return mode;
    }

    public DataRowTableModel getTableModel() {
        if (null == tableModel)
            tableModel = new DataRowTableModel(getDataLayoutNode());
        return tableModel;
    }

    public TitledBorder getTitleBorder() {
        if (null == titleBorder) {
            EtchedBorder etched = new EtchedBorder();
            titleBorder = new TitledBorder(etched, getCaption());
        }
        return titleBorder;
    }

    private JToggleButton getToggleInsert() {
        if (null == toggleInsert) {
            toggleInsert = new JToggleButton(new ActivateInsertModeAction());
            toggleInsert.setText(Resources.getString("new_text"));
            toggleInsert.setMnemonic(Resources.getString("new_mnemonic").charAt(0));
        }
        return toggleInsert;
    }

    private JToggleButton getToggleUpdate() {
        if (null == toggleUpdate) {
            toggleUpdate = new JToggleButton(new ActivateUpdateModeAction());
            toggleUpdate.setText(Resources.getString("edit_text"));
            toggleUpdate.setMnemonic(Resources.getString("edit_mnemonic").charAt(0));
        }
        return toggleUpdate;
    }

    public JToolBar getToolbar() {
        if (null == toolbar) {
            toolbar = new JToolBar("item actions", JToolBar.HORIZONTAL);
            toolbar.add(getToggleInsert());
            toolbar.add(getToggleUpdate());
            JButton deleteItem = new JButton(new DeleteItemAction());
            deleteItem.setText(Resources.getString("delete_text"));
            deleteItem.setMnemonic(Resources.getString("delete_mnemonic").charAt(0));
            toolbar.add(deleteItem);
        }
        return toolbar;
    }

    private void initializeComponent() {
        removeAll(); // removes all components
        BorderLayout layout = new BorderLayout(0, 15);

        setLayout(layout);
        add(getToolbar(), BorderLayout.NORTH);
        add(getContentPane(), BorderLayout.CENTER);

        setBorder(getTitleBorder());
        layout.invalidateLayout(this);
    }

    private void initializeTableColumns() {
        for (Field field : getDataLayoutNode()) {
            FieldDisplay dispInfo = getDisplayInfoFactory().getUIDisplayInfo(field);
            TableColumn column = getDataTable().getColumn(field.getName());
            if (null == dispInfo)
                continue;
            if (dispInfo.isVisible()) {
                column.setResizable(true);
                column.setMinWidth((int) dispInfo.getMinimumSize().getWidth());
                column.setMaxWidth((int) dispInfo.getMaximumSize().getWidth());
                column.setPreferredWidth((int) dispInfo.getPreferredSize().getWidth());

                column.setHeaderValue(dispInfo.getCaption());
            }
            else {
                column.setMinWidth(0);
                column.setMaxWidth(1);
                column.setPreferredWidth(0);
                column.setHeaderValue("");
                column.setResizable(false);
            }
        }
    }

    protected void initializeReferencedData(DataLayoutNode node, Field displayField, QueryMediator mediator,
            FieldDisplay disp, Field field) throws SQLException {
        List<DataRow> rows = mediator.fetchAll(node);
        int displayMember = node.getFieldList().indexOf(displayField);
        ReferenceFieldCellRenderer tableCellRenderer = new ReferenceFieldCellRenderer(0, displayMember, rows);
        DataRowComboModel comboModel = new DataRowComboModel();
        comboModel.fill(rows);

        JComboBox countriesCombo = (JComboBox) disp.getDisplayComponent();
        countriesCombo.setModel(comboModel);
        countriesCombo.addAncestorListener(new RefreshComponentDataListener(node, this.getDataSource(), comboModel, tableCellRenderer));
        countriesCombo.setRenderer(new ComboListCellRenderer(displayMember));
        // provide initial data
        getDataTable().getColumn(field.getName()).setCellRenderer(tableCellRenderer);
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() instanceof ListSelectionModel) {
            ListSelectionModel selmodel = (ListSelectionModel) e.getSource();
            switch (mode) {
                case INSERT:
                case UPDATE:
                    selmodel.setSelectionInterval(selectedIndex, selectedIndex);
                    break;
                default:
                    selectedIndex = selmodel.getMinSelectionIndex();
                    break;
            }
        }

    }

    /**
     * Sets the caption which will appear as the title of the panel.
     *
     * @param caption the text which is supposed to be the caption of the panel
     */
    public void setCaption(String caption) {
        this.caption = caption;
        getTitleBorder().setTitle(caption);
    }

    private class ActivateInsertModeAction extends AbstractAction {

        private static final long serialVersionUID = 8690350987358764996L;

        public void actionPerformed(ActionEvent e) {
            JToggleButton button;
            try {
                button = (JToggleButton) e.getSource();
            }
            catch (ClassCastException exc) {
                Log.write().fine(exc.getMessage());
                button = null;
            }

            if (null == button)
                return;
            JRegistryPanel.this.adapter.setSender(button);
            if (button.isSelected()) {
                String warning;
                switch (JRegistryPanel.this.mode) {
                    case INSERT:
                        warning = Resources.getString("insert_already_in_progress");
                        break;
                    case UPDATE:
                        warning = Resources.getString("update_in_progress");
                        break;
                    default:
                        warning = null;
                        break;
                }
                if (null != warning) {
                    JOptionPane.showMessageDialog(JRegistryPanel.this, warning, "Reelcash warning", JOptionPane.WARNING_MESSAGE);
                    button.setSelected(false);
                    return;
                }
                JRegistryPanel.this.getItemComponent().clearData();
                JRegistryPanel.this.getContentLayout().show(JRegistryPanel.this.getContentPane(),
                        JRegistryPanel.ITEM_CARD_NAME);
                JRegistryPanel.this.mode = RegistryPanelMode.INSERT;
            }
            else {
                JRegistryPanel.this.getContentLayout().show(JRegistryPanel.this.getContentPane(),
                        JRegistryPanel.TABLE_CARD_NAME);
                // this is just another way to cancel creation of rows
                JRegistryPanel.this.mode = RegistryPanelMode.DEFAULT;
            }
        }
    }

    private class ActivateUpdateModeAction extends AbstractAction {

        private static final long serialVersionUID = 8690350987358764996L;

        public void actionPerformed(ActionEvent e) {
            JToggleButton button;
            try {
                button = (JToggleButton) e.getSource();
            }
            catch (ClassCastException exc) {
                Log.write().fine(exc.getMessage());
                button = null;
            }

            if (null == button)
                return;
            if (0 > selectedIndex) {
                JOptionPane.showMessageDialog(JRegistryPanel.this, Resources.getString("select_row"),
                        GlobalResources.getString("basic_info_title"), JOptionPane.INFORMATION_MESSAGE);
                button.setSelected(false);
                return;
            }

            JRegistryPanel.this.adapter.setSender(button);
            if (button.isSelected()) {
                String warning;
                switch (JRegistryPanel.this.mode) {
                    case INSERT:
                        warning = Resources.getString("insert_in_progress");
                        break;
                    case UPDATE:
                        warning = Resources.getString("update_already_in_progress");
                        break;
                    default:
                        warning = null;
                        break;
                }
                if (null != warning) {
                    JOptionPane.showMessageDialog(JRegistryPanel.this, warning, "Reelcash warning", JOptionPane.WARNING_MESSAGE);
                    button.setSelected(false);
                    return;
                }
                DataRow modified = JRegistryPanel.this.getTableModel().getRow(selectedIndex);
                JRegistryPanel.this.getItemComponent().setData(modified);
                JRegistryPanel.this.getContentLayout().show(JRegistryPanel.this.getContentPane(),
                        JRegistryPanel.ITEM_CARD_NAME);
                JRegistryPanel.this.mode = RegistryPanelMode.UPDATE;
            }
            else {
                JRegistryPanel.this.getContentLayout().show(JRegistryPanel.this.getContentPane(),
                        JRegistryPanel.TABLE_CARD_NAME);
                // this is just another way to cancel creation of rows
                JRegistryPanel.this.mode = RegistryPanelMode.DEFAULT;
            }
        }
    }

    private class DeleteItemAction extends AbstractAction {

        private static final long serialVersionUID = 810551388261185071L;

        public void actionPerformed(ActionEvent e) {
            if (0 > selectedIndex) {
                JOptionPane.showMessageDialog(JRegistryPanel.this, Resources.getString("select_row"),
                        GlobalResources.getString("basic_info_title"), JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            JRegistryPanel.this.getTableModel().delete(selectedIndex);
        }
    }

    private class ItemComponentDataActionAdapter implements ActionListener {

        private JToggleButton sender;

        private void savePerformed() {
            try {
                switch (JRegistryPanel.this.mode) {
                    case INSERT:
                        DataRow created = JRegistryPanel.this.getItemComponent().getDataRow();
                        JRegistryPanel.this.getTableModel().add(created);
                        break;
                    case UPDATE:
                        if (0 > selectedIndex) {
                            JOptionPane.showMessageDialog(JRegistryPanel.this, Resources.getString("select_row"),
                                    GlobalResources.getString("basic_info_title"),
                                    JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }

                        DataRow modified = JRegistryPanel.this.getItemComponent().getDataRow();
                        JRegistryPanel.this.getTableModel().update(selectedIndex, modified);
                        break;
                }
            }
            catch (ReelcashException exc) {
                JOptionPane.showMessageDialog(JRegistryPanel.this, exc.getStackTrace(),
                        GlobalResources.getString("application_error_title"),
                        JOptionPane.ERROR_MESSAGE);
            }
            catch (Throwable t) {
                JOptionPane.showMessageDialog(JRegistryPanel.this, t.getStackTrace(),
                        GlobalResources.getString("fatal_error_title"),
                        JOptionPane.ERROR_MESSAGE);
            }
            finally {
                JRegistryPanel.this.getContentLayout().show(JRegistryPanel.this.getContentPane(),
                        JRegistryPanel.TABLE_CARD_NAME);
                JRegistryPanel.this.mode = RegistryPanelMode.DEFAULT;

                if (null != sender)
                    sender.setSelected(false);
            }
        }

        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();
            if (null == actionCommand)
                return;
            if (JDataLayoutNodeComponent.SAVE_ACTION.equals(actionCommand)) {
                savePerformed();
                return;
            }
            if (JDataLayoutNodeComponent.CANCEL_ACTION.equals(actionCommand)) {
                // this is the default way to cancel saving
                JRegistryPanel.this.getContentLayout().show(JRegistryPanel.this.getContentPane(),
                        JRegistryPanel.TABLE_CARD_NAME);
                JRegistryPanel.this.mode = RegistryPanelMode.DEFAULT;
                if (null != sender)
                    sender.setSelected(false);
            }
        }

        void setSender(JToggleButton sender) {
            this.sender = sender;
        }
    }
}

