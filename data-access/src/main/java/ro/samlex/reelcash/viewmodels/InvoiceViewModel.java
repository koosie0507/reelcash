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
    private ItemListUpdater itemsListener;

    public InvoiceViewModel() {
        itemsListener = new ItemListUpdater(model.getInvoicedItems());
        getItems().addObservableListListener(itemsListener);
    }

    @Getter
    public Invoice getModel() {
        return model;
    }

    @Setter
    public void setModel(Invoice value) {
        Invoice old = this.model;
        this.model = value;
        setupItems();
        firePropertyChanged("model", old, this.model);
    }

    private void setupItems() {
        getItems().removeObservableListListener(itemsListener);
        itemsListener = null;
        getItems().clear();
        if (this.model != null) {
            getItems().addAll(this.model.getInvoicedItems());
            itemsListener = new ItemListUpdater(this.model.getInvoicedItems());
            getItems().addObservableListListener(itemsListener);
        }
    }

    public void save(OutputSink sink) throws IOException {
        if (sink == null) {
            throw new IllegalArgumentException("null output sink");
        }
        
        if (this.model == null) {
            throw new IllegalStateException("eeeh?!?");
        }

        try (Writer w = sink.newWriter()) {
            if (w == null) {
                throw new IllegalArgumentException("null writer");
            }
            w.write(new Gson().toJson(this.model));
        }
    }
}
