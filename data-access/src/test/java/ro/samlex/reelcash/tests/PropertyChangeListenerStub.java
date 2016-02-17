package ro.samlex.reelcash.tests;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public final class PropertyChangeListenerStub implements PropertyChangeListener {

    private PropertyChangeEvent event;

    public PropertyChangeEvent getEvent() {
        return event;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        event = evt;
    }

}
