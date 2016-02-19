package ro.samlex.reelcash.tests.viewmodels;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Writer;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import org.junit.Test;
import ro.samlex.reelcash.PropertyChangeObservable;
import ro.samlex.reelcash.data.Party;
import ro.samlex.reelcash.io.OutputSink;
import ro.samlex.reelcash.tests.PropertyChangeListenerStub;
import ro.samlex.reelcash.tests.io.StringListOutputSink;
import ro.samlex.reelcash.viewmodels.SimpleViewModel;

public class SimpleViewModelTests {

    @Test
    public void givenViewModel_inAnyCase_itExtendsPropertyChangedObservable() {
        assertThat(new SimpleViewModel(), instanceOf(PropertyChangeObservable.class));
    }

    @Test
    public void givenViewModel_inInitialState_modelIsNull() {
        assertNull(new SimpleViewModel().getModel());
    }

    @Test
    public void givenViewModel_settingNewModel_firesPropertyChange() {
        PropertyChangeListenerStub listener = new PropertyChangeListenerStub();
        SimpleViewModel sut = new SimpleViewModel();
        sut.addPropertyChangeListener(listener);

        sut.setModel(new Object());

        assertEquals(sut, listener.getEvent().getSource());
        assertEquals("model", listener.getEvent().getPropertyName());
        assertNull(listener.getEvent().getOldValue());
        assertEquals(sut.getModel(), listener.getEvent().getNewValue());
    }

    @Test(expected = IllegalStateException.class)
    public void givenViewModel_inInitialState_saveThrowsIllegalStateException() {
        SimpleViewModel sut = new SimpleViewModel();

        try {
            sut.save(new OutputSink() {
                @Override
                public Writer newWriter() throws IOException {
                    return null;
                }
            });
        } catch (IOException e) {
            fail("Save failed: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenViewModel_saveOnNullSink_throwsIllegalArgumentException() {
        SimpleViewModel sut = new SimpleViewModel();

        try {
            sut.save(null);
        } catch (IOException e) {
            fail("Save failed: " + e.getMessage());
        }
    }

    @Test
    public void givenViewModelWithModel_saveOnValidSink_savesExpectedContent() {
        StringListOutputSink sink = new StringListOutputSink();
        Party p = new Party().street("test street").fiscalId("abc");
        p.setName("test");
        SimpleViewModel<Party> sut = new SimpleViewModel<>();
        sut.setModel(p);

        try {
            sut.save(sink);
        } catch (IOException e) {
            fail("Save failed: " + e.getMessage());
        }

        String expected = new Gson().toJson(p);
        assertEquals(expected, sink.getWrittenValues().get(0));
    }

    @Test(expected = IOException.class)
    public void givenViewModelWithModel_saveOnSinkWhichThrowsIOException_throwsIOException() throws IOException {
        Party p = new Party().street("test street").fiscalId("abc");
        p.setName("test");
        SimpleViewModel<Party> sut = new SimpleViewModel<>();
        sut.setModel(p);

        sut.save(new OutputSink() {
            @Override
            public Writer newWriter() throws IOException {
                throw new IOException();
            }
        });
    }
}
