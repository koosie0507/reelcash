package ro.samlex.reelcash.viewmodels;

import ro.samlex.reelcash.io.OutputSink;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Writer;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import ro.samlex.reelcash.PropertyChangeObservable;
import ro.samlex.reelcash.data.Party;

public class CompanyInformationViewModel extends PropertyChangeObservable {

    private Party model;

    @Getter
    public Party getModel() {
        return model;
    }

    @Setter
    public void setModel(Party model) {
        Party old = this.model;
        this.model = model;
        firePropertyChanged("model", old, this.model);
    }

    public void save(OutputSink outputSink) throws IOException {
        if (this.model == null) {
            throw new IllegalStateException();
        }
        try (Writer writer = outputSink.newWriter()) {
            writer.append(new Gson().toJson(model));
        }
    }
}
