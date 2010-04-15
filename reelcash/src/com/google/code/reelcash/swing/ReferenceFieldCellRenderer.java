package com.google.code.reelcash.swing;

import com.google.code.reelcash.data.DataRow;
import java.awt.Component;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author cusi
 */
public class ReferenceFieldCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 8387079661943076176L;
    private List<DataRow> data;
    private final int valueColIdx;
    private final int displayColIdx;

    public ReferenceFieldCellRenderer(int valueColIdx, int displayColIdx, List<DataRow> data) {
        this.data = data;
        this.valueColIdx = valueColIdx;
        this.displayColIdx = displayColIdx;
    }

    public void setData(List<DataRow> data) {
        this.data = data;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        String text = "";
        try {
            if (null != value)
                for (DataRow r : data) {
                    if (r.getValue(valueColIdx).equals(value)) {
                        Object disp = r.getValue(displayColIdx).toString();
                        text = (null == disp) ? "" : disp.toString();
                        break;
                    }
                }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
        finally {
            setText(text);
        }
    }
}

