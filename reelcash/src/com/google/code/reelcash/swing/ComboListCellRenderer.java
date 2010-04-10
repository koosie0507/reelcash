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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import sun.swing.DefaultLookup;

/**
 *
 * @author cusi
 */
public class ComboListCellRenderer extends DefaultListCellRenderer {

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
                && dropLocation.getIndex() == index) {

            bg = DefaultLookup.getColor(this, ui, "List.dropCellBackground");
            fg = DefaultLookup.getColor(this, ui, "List.dropCellForeground");

            isSelected = true;
        }

	if (isSelected) {
            setBackground(bg == null ? list.getSelectionBackground() : bg);
	    setForeground(fg == null ? list.getSelectionForeground() : fg);
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
	    setIcon((Icon)value);
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
                border = DefaultLookup.getBorder(this, ui, "List.focusSelectedCellHighlightBorder");
            }
            if (border == null) {
                border = DefaultLookup.getBorder(this, ui, "List.focusCellHighlightBorder");
            }
        } else {
            //border = getNoFocusBorder();
        }
	setBorder(border);

	return this;
    }
}
