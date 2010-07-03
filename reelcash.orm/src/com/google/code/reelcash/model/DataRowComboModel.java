package com.google.code.reelcash.model;

import com.google.code.reelcash.Log;
import com.google.code.reelcash.data.DataRow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Represents a combo box model which works with data rows.
 *
 * @author cusi
 */
public class DataRowComboModel implements ComboBoxModel {

    private final ArrayList<DataRow> data;
    private final ArrayList<ListDataListener> listeners;
    private int valueMemberIndex;
    private int displayMemberIndex;
    private DataRow selectedRow;
    private DataRowComparator comparator;

    public DataRowComboModel() {
        data = new ArrayList();
        listeners = new ArrayList();
        valueMemberIndex = 0;
        displayMemberIndex = 1;
        selectedRow = null;
        comparator = new DataRowComparator();
    }

    protected void fireContentsChanged() {
        ListDataEvent evt = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED,
                0, data.size() - 1);
        for (ListDataListener listener : listeners) {
            listener.contentsChanged(evt);
        }
    }

    protected void fireIntervalAdded(int startIndex, int endIndex) {
        ListDataEvent evt = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED,
                startIndex, endIndex);
        for (ListDataListener listener : listeners) {
            listener.intervalAdded(evt);
        }
    }

    protected void fireIntervalRemoved(int startIndex, int endIndex) {
        ListDataEvent evt = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED,
                startIndex, endIndex);
        for (ListDataListener listener : listeners) {
            listener.intervalRemoved(evt);
        }
    }

    public void add(DataRow row) {
        int idx = data.size();
        data.add(row);
        fireIntervalAdded(idx, idx);
    }

    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    public void clear() {
        if (data.isEmpty())
            return;
        fireIntervalRemoved(0, data.size() - 1);
        data.clear();
    }

    public void fill(Collection<? extends DataRow> rows) {
        data.clear();
        data.addAll(rows);
        fireContentsChanged();
    }

    public void fill(DataRow[] fetchSimple) {
        data.clear();
        for (DataRow row : fetchSimple)
            data.add(row);
        fireContentsChanged();
    }

    public int indexOfValue(Object aValue) {
        int idx = data.size() - 1;
        while (idx > -1) {
            DataRow row = data.get(idx);

            if (null == row.getValue(valueMemberIndex)) {
                if (null == aValue)
                    return idx;
            }
            else if (row.getValue(valueMemberIndex).equals(aValue))
                return idx;
            idx--;
        }
        return idx;
    }

    public int indexOf(DataRow row) {
        return data.indexOf(row);
    }

    public Object getElementAt(int index) {
        return data.get(index);
    }

    public Object getElementValueAt(int index) {
        return data.get(index).getValue(valueMemberIndex);
    }

    public Object getSelectedItem() {
        return selectedRow;
    }

    public Object getSelectedValue() {
        if (null == selectedRow)
            return null;
        return selectedRow.getValue(valueMemberIndex);
    }

    public int getSize() {
        return data.size();
    }

    public void remove(Object obj) {
        int idx = indexOfValue(obj);
        if (0 > idx)
            return;
        fireIntervalRemoved(idx, idx);
        data.remove(idx);
    }

    public void remove(DataRow row) {
        if(!data.remove(row)) {
            Log.write().warning("blana mare");
        }
    }

    public void remove(int idx) {
        data.remove(idx);
    }

    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

    public void setSelectedItem(Object anItem) {
        if (anItem instanceof DataRow) {
            selectedRow = (DataRow) anItem;
            return;
        }
        setSelectedValue(anItem);
    }

    public void setSelectedValue(Object anItem) {
        int idx = indexOfValue(anItem);
        if (0 > idx) {
            selectedRow = null;
            return;
        }
        selectedRow = data.get(idx);
    }

    public void sortBy(int fieldIndex) {
        comparator.setFieldIndex(fieldIndex);
        Collections.sort(data, comparator);
    }

    public int getDisplayMemberIndex() {
        return displayMemberIndex;
    }

    public void setDisplayMemberIndex(int displayMemberIndex) {
        this.displayMemberIndex = displayMemberIndex;
    }

    public int getValueMemberIndex() {
        return valueMemberIndex;
    }

    public void setValueMemberIndex(int valueMemberIndex) {
        this.valueMemberIndex = valueMemberIndex;
    }

    public DataRow getSelectedRow() {
        return selectedRow;
    }

    private class DataRowComparator implements Comparator<DataRow> {

        private int fieldIndex = 0;

        public int compare(DataRow o1, DataRow o2) {
            if (o1.size() < o2.size())
                return -1;
            else if (o1.size() > o2.size())
                return 1;
            else if (o1.size() < 1)
                return 0;
            else {
                if (fieldIndex > -1 && fieldIndex < o1.size()) {
                    Object value = o1.getValue(fieldIndex);
                    if (null == value)
                        return -1;

                    if (value instanceof Comparable)
                        return ((Comparable) value).compareTo(o2.getValue(fieldIndex));
                }
                return 0;
            }
        }

        void setFieldIndex(int fieldIndex) {
            this.fieldIndex = fieldIndex;
        }
    }
}
