package ro.samlex.reelcash.tests.viewmodels;

import java.util.ArrayList;
import java.util.List;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import ro.samlex.reelcash.PropertyChangeObservable;
import ro.samlex.reelcash.tests.PropertyChangeObservableDummy;
import ro.samlex.reelcash.viewmodels.ItemListUpdater;

public class ItemListUpdaterTests {

    @Test
    public void givenListUpdater_changingPropertyOnObservableListItem_updatesList() {
        PropertyChangeObservable item1 = new PropertyChangeObservable() {};
        List<PropertyChangeObservable> updated = new ArrayList<>();
        updated.add(new PropertyChangeObservable(){});
        ItemListUpdater sut = new ItemListUpdater(updated);
        final ObservableCollections.ObservableListHelper<PropertyChangeObservable> observableListHelper = ObservableCollections.observableListHelper(
                new ArrayList<PropertyChangeObservable>());
        ObservableList<PropertyChangeObservable> observable = observableListHelper.getObservableList();
        observable.add(item1);
        observable.addObservableListListener(sut);

        observableListHelper.fireElementChanged(0);
        
        assertEquals(item1, updated.get(0));
    }

    @Test
    public void givenListUpdater_changingPropertyOnItemWhichIsNotInlist_doesNothing() {
        PropertyChangeObservable item1 = new PropertyChangeObservable(){};
        PropertyChangeObservable item2 = new PropertyChangeObservable(){};
        List<PropertyChangeObservable> updated = new ArrayList<>();
        updated.add(item1);
        ItemListUpdater sut = new ItemListUpdater(updated);
        final ObservableCollections.ObservableListHelper<PropertyChangeObservable> observableListHelper = ObservableCollections.observableListHelper(
                new ArrayList<PropertyChangeObservable>());
        ObservableList<PropertyChangeObservable> observable = observableListHelper.getObservableList();
        observable.add(item1);
        observable.add(item2);
        observable.addObservableListListener(sut);

        observableListHelper.fireElementChanged(1);
        
        assertEquals(item1, updated.get(0));
    }
}
