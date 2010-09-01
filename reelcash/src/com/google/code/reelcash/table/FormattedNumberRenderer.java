package com.google.code.reelcash.table;

import java.awt.Color;
import java.awt.Component;
import java.text.NumberFormat;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Instances of this class format what gets displayed in the cells of a JTable.
 *
 * @author andrei.olar
 */
public class FormattedNumberRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = -674411452143647868L;
    private int maxIntegerDigits;
    private int maxFractionDigits;
    private NumberFormat decimalFormatInstance;

    public FormattedNumberRenderer(int maxIntegerDigits, int maxFractionDigits, boolean currency, boolean percent) {
        this.maxIntegerDigits = maxIntegerDigits;
        this.maxFractionDigits = maxFractionDigits;
        decimalFormatInstance = (currency)
                ? NumberFormat.getCurrencyInstance()
                : (percent)
                ? NumberFormat.getPercentInstance()
                : NumberFormat.getNumberInstance();

    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (null == value) {
            value = "-";
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }

        decimalFormatInstance.setMaximumFractionDigits(maxFractionDigits);
        decimalFormatInstance.setMaximumIntegerDigits(maxIntegerDigits);
        String valueStr = decimalFormatInstance.format(value);
        super.setHorizontalAlignment(SwingConstants.RIGHT);
        return super.getTableCellRendererComponent(table, valueStr, isSelected, hasFocus,
                row, column);
    }
}
