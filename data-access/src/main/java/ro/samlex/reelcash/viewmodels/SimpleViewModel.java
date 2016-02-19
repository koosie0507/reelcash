package ro.samlex.reelcash.viewmodels;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Writer;
import jdk.nashorn.internal.objects.annotations.Getter;
import ro.samlex.reelcash.PropertyChangeObservable;
import ro.samlex.reelcash.io.OutputSink;

public class SimpleViewModel<T> extends PropertyChangeObservable {

    private T model;

    @Getter
    public T getModel() {
        return model;
    }

    public void setModel(T value) {
        T old = this.model;
        this.model = value;
        firePropertyChanged("model", old, value);
    }

    public void save(OutputSink outputSink) throws IOException {
        if (outputSink == null) {
            throw new IllegalArgumentException();
        }
        if (this.model == null) {
            throw new IllegalStateException();
        }

        try (Writer w = outputSink.newWriter()) {
            w.append(new Gson().toJson(this.model));
        }
    }
}
