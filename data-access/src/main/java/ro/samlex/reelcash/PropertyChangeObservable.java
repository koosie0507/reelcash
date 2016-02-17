package ro.samlex.reelcash;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class PropertyChangeObservable {

    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    protected final <T> void firePropertyChanged(String propertyName, T oldValue, T newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    public final void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public final void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
