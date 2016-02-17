package ro.samlex.reelcash.viewmodels;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ro.samlex.reelcash.PropertyChangeObservable;
import ro.samlex.reelcash.commands.Command;
import ro.samlex.reelcash.data.Invoice;

public final class InvoiceListViewModel extends SelectorViewModel<Invoice> {

    private final LoadInvoicesCommand loadInvoicesCommand;

    public InvoiceListViewModel(Iterator<Reader> inputSource) {
        this.loadInvoicesCommand = new LoadInvoicesCommand(inputSource);
    }

    public Command getLoadInvoicesCommand() {
        return loadInvoicesCommand;
    }

    private final class LoadInvoicesCommand implements Command {

        private final Iterator<Reader> inputSource;

        LoadInvoicesCommand(Iterator<Reader> inputSource) {
            this.inputSource = inputSource;
        }

        @Override
        public boolean isExecutable() {
            return true;
        }

        @Override
        public void execute() {
            List<Invoice> invoices = new ArrayList<Invoice>();
            while (inputSource.hasNext()) {
                int idx = invoices.size();
                try (Reader reader = (Reader) inputSource.next()) {
                    Invoice item = new Gson().fromJson(reader, Invoice.class);
                    invoices.add(idx, item);
                } catch (IOException ex) {
                    Logger.getLogger(InvoiceListViewModel.class.getName()).log(Level.SEVERE, "Could not read from input source", ex);
                    invoices.remove(idx);
                } catch (JsonSyntaxException ex) {
                    Logger.getLogger(InvoiceListViewModel.class.getName()).log(Level.WARNING, "Invalid JSON in input source", ex);
                }
            }
            getItems().addAll(invoices);
        }
    }
}
