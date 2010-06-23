package com.google.code.reelcash.swing.invoices;

import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.swing.MultiLineLabelUI;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * Insert some documentation for the class <b>InvoiceListCellRenderer</b> (what's its purpose,
 * why this implementation, that sort of thing).
 *
 * @author andrei.olar
 */
public class DataRowListCellRenderer extends DefaultListCellRenderer {
    
    private static final long serialVersionUID = -8029827150063040701L;
    private String formatString;
    private int[] rowIndices;

    /**
     * Creates a new renderer which will
     *
     * @param formatString string which will be used for formatting the contents of the label.
     * @param rowIndices within the data row which will be rendered.
     *
     * @exception IllegalArgumentException if either parameter is null or empty.
     */
    public DataRowListCellRenderer(String formatString, int... rowIndices) {
        this.formatString = formatString;
        this.rowIndices = rowIndices;
        setUI(new MultiLineLabelUI());
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
            Object[] values = new Object[rowIndices.length];
            int valIdx = 0;
            for(int idx: rowIndices) {
                values[valIdx] = row.getValue(idx);
                valIdx ++;
            }
            setText(String.format(formatString, values));
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
