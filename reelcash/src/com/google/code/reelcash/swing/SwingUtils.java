package com.google.code.reelcash.swing;

import com.google.code.reelcash.table.FormattedNumberRenderer;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 * Contains utility methods for various purposes.
 *
 * @author andrei.olar
 */
public class SwingUtils {

    /**
     * Toggles the visibility of a column in a JTable.
     *
     * @param table the table
     * @param columnIdentifier the identifier (name) of the column
     * @param visible whether the column should be visible or not.
     * @param visibleCaption the caption of the visible column.
     * @param width if the <b>visible</b> parameter is true, this
     * parameter holds the width of the visible column.
     */
    public static void setTableColumnVisible(JTable table, String columnIdentifier, boolean visible, String visibleCaption, int width) {
        if (null == table || null == columnIdentifier || null == visibleCaption)
            throw new IllegalArgumentException();

        TableColumn column = table.getColumn(columnIdentifier);
        if (null == column)
            return;

        if (visible) {
            column.setResizable(true);
            column.setMinWidth(width);
            column.setMaxWidth(width);
            column.setPreferredWidth(width);

            column.setHeaderValue(columnIdentifier);
        }
        else {
            column.setMinWidth(0);
            column.setMaxWidth(1);
            column.setPreferredWidth(0);
            column.setHeaderValue("");
            column.setResizable(false);
        }
    }

    /**
     * Specifies that the given column will contain decimal values.
     *
     * @param table table
     * @param columnIdentifier column in table
     * @param integerDigits integral part of the number will contain at most this many digits
     * @param decimalPlaces fractional part of number will contain at most this many digits
     */
    public static void setTableColumnDecimalFormat(JTable table, String columnIdentifier, int integerDigits, int decimalPlaces) {
        if (null == table || null == columnIdentifier || 1 > integerDigits || 0 > decimalPlaces)
            throw new IllegalArgumentException();

        try {
            TableColumn column = table.getColumn(columnIdentifier);
            column.setCellRenderer(new FormattedNumberRenderer(integerDigits, decimalPlaces, false, false));
        }
        catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * Specifies that the given column will contain currency values.
     *
     * @param table table
     * @param columnIdentifier column in table
     * @param integerDigits integral part of the number will contain at most this many digits
     * @param decimalPlaces fractional part of number will contain at most this many digits
     */
    public static void setTableColumnCurrencyFormat(JTable table, String columnIdentifier, int integerDigits, int decimalPlaces) {
        if (null == table || null == columnIdentifier || 1 > integerDigits || 0 > decimalPlaces)
            throw new IllegalArgumentException();

        TableColumn column = table.getColumn(columnIdentifier);
        if (null == column)
            return;

        column.setCellRenderer(new FormattedNumberRenderer(integerDigits, decimalPlaces, true, false));
    }

    /**
     * Specifies that the given column will contain percent values.
     *
     * @param table table
     * @param columnIdentifier column in table
     * @param integerDigits integral part of the number will contain at most this many digits
     * @param decimalPlaces fractional part of number will contain at most this many digits
     */
    public static void setTableColumnPercentFormat(JTable table, String columnIdentifier, int integerDigits, int decimalPlaces) {
        if (null == table || null == columnIdentifier || 1 > integerDigits || 2 < integerDigits || 0 > decimalPlaces)
            throw new IllegalArgumentException();

        TableColumn column = table.getColumn(columnIdentifier);
        if (null == column)
            return;

        column.setCellRenderer(new FormattedNumberRenderer(integerDigits, decimalPlaces, false, true));
    }
}
