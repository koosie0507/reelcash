/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.swing;

import com.google.code.reelcash.data.DataRow;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author cusi
 */
public class ComboListCellRenderer extends DefaultListCellRenderer {
    private static final long serialVersionUID = 7015320515115727627L;

    private int displayMemberIndex;

    public ComboListCellRenderer(int displayMemberIndex) {
        this.displayMemberIndex = displayMemberIndex;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {

        setComponentOrientation(list.getComponentOrientation());

        Color bg = null;
        Color fg = null;

        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index)
            isSelected = true;

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        if (value instanceof DataRow) {
            DataRow row = (DataRow) value;
            setText(row.getValue(displayMemberIndex).toString());
        }
        else if (value instanceof Icon) {
            setIcon((Icon) value);
            setText("");
        }
        else {
            setIcon(null);
            setText((value == null) ? "" : value.toString());
        }

        setEnabled(list.isEnabled());
        setFont(list.getFont());

        Border border = null;
        if (cellHasFocus) {
            if (isSelected) {
                border = UIManager.getBorder("List.focusSelectedCellHighlightBorder");
                if (null == border)
                    border = UIManager.getBorder("List.focusCellHighlightBorder");
            }
        }
        else
            border = new EmptyBorder(1, 1, 1, 1);
        
        return this;
    }
}
