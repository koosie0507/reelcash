package ro.samlex.reelcash.viewmodels;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.observablecollections.ObservableListListener;
import ro.samlex.reelcash.data.Invoice;
import ro.samlex.reelcash.data.InvoiceItem;
import ro.samlex.reelcash.io.OutputSink;

public class InvoiceViewModel extends SelectorViewModel<InvoiceItem> {

    private Invoice model = new Invoice();
    private final ItemsChangeListener itemsListener = new ItemsChangeListener();

    public InvoiceViewModel() {
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
        getItems().clear();
        if (this.model != null) {
            getItems().addAll(this.model.getInvoicedItems());
        }
        getItems().addObservableListListener(itemsListener);
    }

    public void save(OutputSink sink) throws IOException {
        if (sink == null) {
            throw new IllegalArgumentException("null output sink");
        }

        try (Writer w = sink.newWriter()) {
            w.write(new Gson().toJson(this.model));
        }
    }

    private class ItemsChangeListener implements ObservableListListener {

        @Override
        public void listElementsAdded(ObservableList list, int index, int count) {
            final List subList = list.subList(index, index + count);
            model.getInvoicedItems().addAll(index, subList);
        }

        @Override
        public void listElementsRemoved(ObservableList list, int index, List removed) {
            model.getInvoicedItems().removeAll(removed);
        }

        @Override
        public void listElementReplaced(ObservableList list, int index, Object replaced) {
            model.getInvoicedItems().set(index, (InvoiceItem) list.get(index));
        }

        @Override
        public void listElementPropertyChanged(ObservableList list, int index) {
            model.getInvoicedItems().set(index, (InvoiceItem) list.get(index));
        }
    }

}
