package ro.samlex.reelcash.tests;

import ro.samlex.reelcash.PropertyChangeObservable;

public final class PropertyChangeObservableDummy extends PropertyChangeObservable {

    private String someProperty;

    public PropertyChangeObservableDummy() {
    }

    public String getSomeProperty() {
        return someProperty;
    }

    public void setSomeProperty(String someProperty) {
        String oldSomeProperty = this.someProperty;
        this.someProperty = someProperty;
        firePropertyChanged("someProperty", oldSomeProperty, someProperty);
    }

}
