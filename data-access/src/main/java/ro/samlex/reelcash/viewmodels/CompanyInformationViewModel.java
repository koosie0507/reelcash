package ro.samlex.reelcash.viewmodels;

import ro.samlex.reelcash.io.OutputSink;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Writer;
import jdk.nashorn.internal.objects.annotations.Getter;
import ro.samlex.reelcash.data.Party;

public class CompanyInformationViewModel {

    private final Party model;

    public CompanyInformationViewModel() {
        this(new Party());
    }

    public CompanyInformationViewModel(Party model) {
        this.model = model;
    }

    @Getter
    public Party getModel() {
        return model;
    }

    public void save(OutputSink outputSink) throws IOException {
        try (Writer writer = outputSink.newWriter()) {
            writer.append(new Gson().toJson(model));
        }
    }
}
