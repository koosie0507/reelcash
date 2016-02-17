package ro.samlex.reelcash.tests.viewmodels;

import java.beans.PropertyChangeEvent;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;
import ro.samlex.reelcash.tests.PropertyChangeListenerStub;
import ro.samlex.reelcash.viewmodels.SelectorViewModel;

public class SelectorViewModelTests {

    @Test
    public void givenViewModel_inInitialState_invoiceListIsEmpty() {
        SelectorViewModel sut = new SelectorViewModel();

        assertTrue(sut.getItems().isEmpty());
    }
    
    @Test
    public void givenViewModel_inInitialState_selectedInvoiceIsNull() {
        SelectorViewModel sut = new SelectorViewModel();
        
        assertNull(sut.getSelectedItem());
    }
    
    @Test
    public void givenViewModelWithOneItem_settingSelectedItem_raisesPropertyChange() {
        PropertyChangeListenerStub listenerStub = new PropertyChangeListenerStub();
        SelectorViewModel<String> sut = new SelectorViewModel<>();
        sut.addPropertyChangeListener(listenerStub);
        sut.getItems().add("a string");
        
        sut.setSelectedItem("a string");
        
        PropertyChangeEvent actual = listenerStub.getEvent();        
        assertEquals("a string", actual.getNewValue());
        assertEquals("selectedItem", actual.getPropertyName());
        assertEquals(sut, actual.getSource());
    }
    
    @Test
    public void givenViewModelWithoutItems_settingSelectedItem_doesNotRaisePropertyChange() {
        PropertyChangeListenerStub listenerStub = new PropertyChangeListenerStub();
        SelectorViewModel<String> sut = new SelectorViewModel<>();
        sut.addPropertyChangeListener(listenerStub);
        
        sut.setSelectedItem("a string");
        
        assertNull(listenerStub.getEvent());
    }
    
    @Test
    public void givenViewModelWithOneItem_settingSelectedItemToSomethingOtherThanTheViewModelItem_doesNotRaisePropertyChange() {
        PropertyChangeListenerStub listenerStub = new PropertyChangeListenerStub();
        SelectorViewModel<String> sut = new SelectorViewModel<>();
        sut.getItems().add("test");
        sut.addPropertyChangeListener(listenerStub);
        
        sut.setSelectedItem("something else");
        
        assertNull(listenerStub.getEvent());
    }
    
    @Test
    public void givenViewModelWithOneItem_selectingTheSameItemTheSecondTime_doesNotRaisePropertyChange() {
        PropertyChangeListenerStub listenerStub = new PropertyChangeListenerStub();
        SelectorViewModel<String> sut = new SelectorViewModel<>();
        sut.getItems().add("test");
        sut.setSelectedItem("something else");
        sut.addPropertyChangeListener(listenerStub);
        
        sut.setSelectedItem("something else");
        
        assertNull(listenerStub.getEvent());
    }
    
    @Test
    public void givenViewModelWithOneItem_removingTheItem_setsTheSelectedItemToNull() {
        SelectorViewModel sut = new SelectorViewModel();
        sut.getItems().add(new Object());
        
        sut.getItems().remove(sut.getItems().get(0));
        
        assertNull(sut.getSelectedItem());
    }
    
    @Test
    public void givenViewModelWithOneItem_addingAnItem_setsTheSelectedItemToNewItem() {
        SelectorViewModel<String> sut = new SelectorViewModel<>();
        String expected = "expected";
        sut.getItems().add("bozo");
        
        sut.getItems().add(expected);
        
        assertEquals(expected, sut.getSelectedItem());
    }
    
    @Test
    public void givenViewModelWithOneItem_replacingTheItem_setsTheSelectedItemToNewItem() {
        SelectorViewModel<String> sut = new SelectorViewModel<>();
        String expected = "expected";
        sut.getItems().add("bozo");
        
        sut.getItems().set(0, expected);
        
        assertEquals(expected, sut.getSelectedItem());
    }
    
    @Test
    public void givenViewModelWithTwoItems_removingSelectedItem_setsSelectedItemToNull() {
        SelectorViewModel<String> sut = new SelectorViewModel<>();
        sut.getItems().add("bozo 1");
        sut.getItems().add("bozo 2");
        sut.setSelectedItem("bozo 1");
        
        sut.getItems().remove("bozo 1");
        
        assertNull(sut.getSelectedItem());
    }
    
    @Test
    public void givenViewModelWithTwoItems_removingItemWhichIsNotSelected_doesNotSetSelectedItem() {
        PropertyChangeListenerStub listenerStub = new PropertyChangeListenerStub();
        SelectorViewModel<String> sut = new SelectorViewModel<>();
        sut.getItems().add("bozo 1");
        sut.getItems().add("bozo 2");
        sut.setSelectedItem("bozo 1");
        sut.addPropertyChangeListener(listenerStub);
        
        sut.getItems().remove("bozo 2");
        
        assertEquals("bozo 1", sut.getSelectedItem());
        assertNull(listenerStub.getEvent());
    }
    
    @Test
    public void givenViewModel_addingMultipleItems_selectsTheFirstItemInTheBunch() {
        SelectorViewModel<String> sut = new SelectorViewModel<>();

        sut.getItems().addAll(Arrays.asList("bozo 1", "bozo 2", "bozo 3"));
        
        assertEquals("bozo 1", sut.getSelectedItem());
    }
}
