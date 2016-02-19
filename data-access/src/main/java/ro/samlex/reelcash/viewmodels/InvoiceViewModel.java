package ro.samlex.reelcash.viewmodels;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Writer;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import ro.samlex.reelcash.data.Invoice;
import ro.samlex.reelcash.data.InvoiceItem;
import ro.samlex.reelcash.io.OutputSink;

public class InvoiceViewModel extends SelectorViewModel<InvoiceItem> {

    private Invoice model = new Invoice();

    @Getter
    public Invoice getModel() {
        return model;
    }

    @Setter
    public void setModel(Invoice selectedItem) {
        Invoice old = this.model;
        this.model = selectedItem;
        firePropertyChanged("model", old, this.model);
    }

    public void save(OutputSink sink) throws IOException {
        if (sink == null) {
            throw new IllegalArgumentException("null output sink");
        }

        try (Writer w = sink.newWriter()) {
            w.write(new Gson().toJson(this.model));
        }
    }

}
