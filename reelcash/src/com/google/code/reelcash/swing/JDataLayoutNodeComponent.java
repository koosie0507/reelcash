/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.swing;

import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.DataRowComboModel;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

/**
 * This component creates a panel which is created dynamically as specified by a data
 * layout displayInfo.
 *
 * @author andrei.olar
 */
public class JDataLayoutNodeComponent extends JPanel implements Scrollable, ActionListener {

    private static final long serialVersionUID = 3984322573736715408L;
    private JPanel commandPanel;
    private DataLayoutNode node;
    private GridBagLayout layout;
    private ArrayList<FieldDisplay> dispInfo;
    private ArrayList<ActionListener> dataActionListeners;
    public static final String SAVE_ACTION = "save";
    public static final String CANCEL_ACTION = "cancel";

    public JDataLayoutNodeComponent() {
        dispInfo = new ArrayList();
        dataActionListeners = new ArrayList();
        setAutoscrolls(true);
        setLayout(getLayout());
    }

    @Override
    public GridBagLayout getLayout() {
        if (null == layout) {
            layout = new GridBagLayout();
        }
        return layout;
    }

    private static GridBagConstraints getLabelConstraints(int gridy) {
        return new GridBagConstraints(0, gridy, 1, 1, 0.1, 0.0, GridBagConstraints.LINE_START,
                GridBagConstraints.BOTH, new Insets(10, 25, 5, 5), 0, 0);
    }

    private static GridBagConstraints getComponentConstraints(int gridy) {
        return new GridBagConstraints(1, gridy, 1, 1, 1.0, 0.0, GridBagConstraints.LINE_START,
                GridBagConstraints.BOTH, new Insets(10, 5, 5, 25), 0, 0);
    }

    private FieldDisplay findDisplay(Field f) {
        for (FieldDisplay disp : dispInfo) {
            if (disp.getKey().equals(f.getName())) {
                return disp;
            }
        }
        return null;
    }

    private JPanel getCommandPanel() {
        if (null == commandPanel) {
            FlowLayout flow = new FlowLayout(SwingConstants.HORIZONTAL, 15, 15);
            flow.setAlignment(SwingConstants.RIGHT);
            flow.setAlignOnBaseline(true);
            commandPanel = new JPanel(flow);

            Dimension buttonSize = new Dimension(85, 23);

            JButton saveBtn = new JButton(Resources.getString("save_text"));
            saveBtn.setActionCommand(SAVE_ACTION);
            saveBtn.setMnemonic(Resources.getString("save_mnemonic").charAt(0));
            saveBtn.setMinimumSize(buttonSize);
            saveBtn.setMaximumSize(buttonSize);
            saveBtn.setPreferredSize(buttonSize);

            JButton cancelBtn = new JButton(Resources.getString("cancel_text"));
            cancelBtn.setActionCommand(CANCEL_ACTION);
            cancelBtn.setMnemonic(Resources.getString("cancel_mnemonic").charAt(0));
            cancelBtn.setMinimumSize(buttonSize);
            cancelBtn.setMaximumSize(buttonSize);
            cancelBtn.setPreferredSize(buttonSize);

            saveBtn.addActionListener(this);
            cancelBtn.addActionListener(this);

            commandPanel.add(saveBtn);
            commandPanel.add(cancelBtn);
        }
        return commandPanel;
    }

    private void initializeComponent(FieldDisplayFactory displayInfo) {
        removeAll();

        dispInfo.clear();

        setName(node.getName());
        add(getCommandPanel(), new GridBagConstraints(0, 0, 2, 1, 1.0, 0, GridBagConstraints.PAGE_START, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        int gridy = 1;
        for (Field field : node) {
            FieldDisplay d = displayInfo.getUIDisplayInfo(field);
            if (d.isVisible()) {
                JLabel label = d.createDescriptionLabel();
                JComponent component = d.getDisplayComponent();
                add(label, getLabelConstraints(gridy));
                add(component, getComponentConstraints(gridy));
                dispInfo.add(d);
                gridy++;
            }
        }

        getLayout().invalidateLayout(this);
        setPreferredSize(new Dimension(getPreferredSize().width, getLayout().preferredLayoutSize(this).height));
    }

    public void actionPerformed(ActionEvent e) {
        for (ActionListener listener : dataActionListeners) {
            listener.actionPerformed(e);
        }
    }

    public void addDataActionListener(ActionListener l) {
        dataActionListeners.add(l);
    }

    /**
     * Returns a data row from the edited values.
     * @return a data row containing the edited values.
     */
    public DataRow getDataRow() {
        DataRow result = node.createRow();
        int idx = 0;
        for (Iterator<Field> f = node.iterator(); f.hasNext();) {
            FieldDisplay disp = findDisplay(f.next());
            if (null != disp) {
                result.setValue(idx, disp.getValue());
            }
            idx++;
        }
        return result;
    }

    /**
     * Returns the layout displayInfo responsable for metadata information.
     *
     * @return layout displayInfo.
     */
    public DataLayoutNode getNode() {
        return node;
    }

    /**
     * Sets the layout displayInfo responsable for metadata information provided to this component.
     * @param displayInfo the displayInfo
     */
    public void setNode(DataLayoutNode node, FieldDisplayFactory dispInfo) {
        this.node = node;
        initializeComponent(dispInfo);
    }

    public void clearData() {
        for (FieldDisplay info : dispInfo) {
            info.clearData();
        }
    }

    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 10;
    }

    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        if (orientation == SwingConstants.HORIZONTAL) {
            return visibleRect.width - 10;
        } else {
            return visibleRect.height - 10;
        }
    }

    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    public void removeDataACtionListener(ActionListener l) {
        dataActionListeners.remove(l);
    }

    /**
     * Fills the editors with data from the given data row.
     *
     * @param row the data row containing relevant data.
     */
    public void setData(DataRow row) {
        int idx = 0;
        for (FieldDisplay disp : dispInfo) {
            disp.setValue(row.getValue(idx));
            idx++;
        }
    }
}
