package ro.samlex.reelcash.viewmodels;

import java.util.ArrayList;
import java.util.List;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.observablecollections.ObservableListListener;
import ro.samlex.reelcash.PropertyChangeObservable;

public class SelectorViewModel<T> extends PropertyChangeObservable {

    private final ObservableList<T> items = ObservableCollections.observableList(new ArrayList<T>());
    private T selectedItem;

    public SelectorViewModel() {
        items.addObservableListListener(new ItemsChangeToSelectionAdapter());
    }

    public ObservableList<T> getItems() {
        return items;
    }

    public T getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(T selectedItem) {
        if (selectedItem != null && !items.contains(selectedItem)) {
            return;
        }
        T oldValue = this.selectedItem;
        this.selectedItem = selectedItem;
        firePropertyChanged("selectedItem", oldValue, this.selectedItem);
    }

    private final class ItemsChangeToSelectionAdapter implements ObservableListListener {

        ItemsChangeToSelectionAdapter() {
        }

        @Override
        public void listElementsAdded(ObservableList list, int index, int count) {
            setSelectedItem((T) list.get(index));
        }

        @Override
        public void listElementsRemoved(ObservableList list, int index, List removed) {
            if (list.isEmpty() || removed.contains(selectedItem)) {
                setSelectedItem(null);
            }
        }

        @Override
        public void listElementReplaced(ObservableList list, int index, Object oldValue) {
            if (index >= 0 && index < list.size()) {
                setSelectedItem((T) list.get(index));
            }
        }

        @Override
        public void listElementPropertyChanged(ObservableList unused1, int unused2) {
        }
    }
}
