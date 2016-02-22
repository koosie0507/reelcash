package ro.samlex.reelcash.ui.components;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

public class OverwritingTableCellEditor extends AbstractCellEditor implements TableCellEditor {
    private final JComponent component = new JTextField();

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
        ((JTextField)component).setText("");
        return component;
    }

    @Override
    public Object getCellEditorValue() {
        return ((JTextField)component).getText();
    }
}