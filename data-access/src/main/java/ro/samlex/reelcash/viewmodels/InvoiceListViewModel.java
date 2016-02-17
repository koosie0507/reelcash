package ro.samlex.reelcash.viewmodels;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ro.samlex.reelcash.commands.Command;
import ro.samlex.reelcash.data.Invoice;
import ro.samlex.reelcash.io.InputSource;

public final class InvoiceListViewModel extends SelectorViewModel<Invoice> {

    private final LoadInvoicesCommand loadInvoicesCommand;

    public InvoiceListViewModel(Iterable<InputSource> inputSources) {
        this.loadInvoicesCommand = new LoadInvoicesCommand(inputSources);
    }

    public Command getLoadInvoicesCommand() {
        return loadInvoicesCommand;
    }

    private final class LoadInvoicesCommand implements Command {

        private final Iterable<InputSource> inputSources;

        LoadInvoicesCommand(Iterable<InputSource> inputSources) {
            this.inputSources = inputSources;
        }

        @Override
        public boolean isExecutable() {
            return true;
        }

        @Override
        public void execute() {
            List<Invoice> invoices = new ArrayList<>();
            for (InputSource i : this.inputSources) {
                int idx = invoices.size();
                try (Reader reader = i.newReader()) {
                    Invoice item = new Gson().fromJson(reader, Invoice.class);
                    invoices.add(idx, item);
                } catch (IOException ex) {
                    Logger.getLogger(InvoiceListViewModel.class.getName()).log(Level.SEVERE, "Could not read from input source", ex);
                } catch (JsonSyntaxException ex) {
                    Logger.getLogger(InvoiceListViewModel.class.getName()).log(Level.WARNING, "Invalid JSON in input source", ex);
                }
            }
            getItems().addAll(invoices);
        }
    }
}
