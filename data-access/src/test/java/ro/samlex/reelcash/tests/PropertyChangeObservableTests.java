package ro.samlex.reelcash.tests;

import java.beans.PropertyChangeEvent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import ro.samlex.reelcash.PropertyChangeObservable;

public class PropertyChangeObservableTests {

    private static final class PropertyChangeObservableDummy extends PropertyChangeObservable {

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


    @Test
    public void givenObservableInstance_changingProperty_notifiesSubscribedListener() {
        PropertyChangeListenerStub listenerStub = new PropertyChangeListenerStub();
        PropertyChangeObservableDummy sut = new PropertyChangeObservableDummy();
        sut.setSomeProperty("old-value");
        sut.addPropertyChangeListener(listenerStub);
        
        sut.setSomeProperty("new-value");
        
        PropertyChangeEvent actual = listenerStub.getEvent();        
        assertEquals("new-value", actual.getNewValue());
        assertEquals("old-value", actual.getOldValue());
        assertEquals("someProperty", actual.getPropertyName());
        assertEquals(sut, actual.getSource());
    }
    
    @Test
    public void givenObservableInstance_changingPropertyWhileUnsubscribed_doesNotNotifyAnyone() {
        PropertyChangeListenerStub listenerStub = new PropertyChangeListenerStub();
        PropertyChangeObservableDummy sut = new PropertyChangeObservableDummy();
        sut.setSomeProperty("old-value");
        sut.addPropertyChangeListener(listenerStub);
        sut.removePropertyChangeListener(listenerStub);
        
        sut.setSomeProperty("new-value");
        
        PropertyChangeEvent actual = listenerStub.getEvent();        
        assertNull(actual);
    }
    
    @Test
    public void givenObservableInstance_notChangingPropertyWhileSubscribed_doesNotNotifyAnyone() {
        PropertyChangeListenerStub listenerStub = new PropertyChangeListenerStub();
        PropertyChangeObservableDummy sut = new PropertyChangeObservableDummy();
        sut.setSomeProperty("old-value");
        sut.addPropertyChangeListener(listenerStub);
        
        sut.setSomeProperty("old-value");
        
        PropertyChangeEvent actual = listenerStub.getEvent();        
        assertNull(actual);
    }
}
