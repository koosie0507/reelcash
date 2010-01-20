/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.table;

import java.awt.Component;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author cusi
 */
public class FormattedDateRenderer extends JLabel implements TableCellRenderer {

    private String customFormat;
    private DateFormat dateFormatInstance;

    public FormattedDateRenderer() {
        dateFormatInstance = DateFormat.getDateInstance();
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (null == value) {
            setText("-");
            return this;
        }
        if (!value.getClass().getName().endsWith("Date")) {
        }
        Date dateValue;
        if (value instanceof java.sql.Date) {
            dateValue = new Date(((java.sql.Date) value).getTime());
        } else if (value instanceof Date) {
            dateValue = (Date) value;
        } else if (value instanceof Number) {
            dateValue = new Date(((Number) value).longValue());
        } else {
            setText(value.toString());
            return this;
        }
        setText(dateFormatInstance.format(dateValue));
        return this;
    }

    public String getCustomFormat() {
        if (null == customFormat) {
            customFormat = "";
        }
        return customFormat;
    }

    public void setCustomFormat(String customFormat) {
        this.customFormat = customFormat;
    }
}
