package ro.samlex.reelcash.viewmodels;

import java.util.List;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.observablecollections.ObservableListListener;

public final class ItemListUpdater<T> implements ObservableListListener {

    private final List<T> items;

    public ItemListUpdater(List<T> items) {
        this.items = items;
    }

    @Override
    public void listElementsAdded(ObservableList list, int index, int count) {
        final List subList = list.subList(index, index + count);
        items.addAll(index, subList);
    }

    @Override
    public void listElementsRemoved(ObservableList list, int index, List removed) {
        items.removeAll(removed);
    }

    @Override
    public void listElementReplaced(ObservableList list, int index, Object replaced) {
        items.set(index, (T) list.get(index));
    }

    @Override
    public void listElementPropertyChanged(ObservableList list, int index) {
        if(index<items.size()) items.set(index, (T) list.get(index));
    }

}
