package ro.samlex.reelcash.data;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Invoice {
    private UUID uuid = UUID.randomUUID();
    private Integer number;
    private Date date;
    private Party recipient;
    private Party emitter;
    private List<InvoiceItem> invoicedItems = new ArrayList<>();
    
    public Party getEmitter() {
        return emitter;
    }

    public Party getRecipient() {
        return recipient;
    }

    public List<InvoiceItem> getInvoicedItems() {
        return invoicedItems;
    }

    public void setEmitter(Party value) {
        if (value == null) {
            throw new NullPointerException("Invoice.setEmitter: new emitter is null");
        }
        this.emitter = value;
    }

    public void setRecipient(Party value) {
        if (value == null) {
            throw new NullPointerException("Invoice.setRecipient: new recipient is null");
        }
        recipient = value;
    }
    
    public void load(InputStream is) throws IOException {
        if (is == null) throw new NullPointerException("Invoice.load: null input stream");
        Gson gson = new Gson();
        try (InputStreamReader isr = new InputStreamReader(is)) {
            Invoice loaded = gson.fromJson(isr, Invoice.class);
            uuid = loaded.uuid;
            number = loaded.number;
            date = loaded.date;
            recipient = loaded.recipient;
            emitter = loaded.emitter;
            invoicedItems = loaded.invoicedItems;
        }
    }

    public void save(OutputStream os) throws IOException {
        if(os == null) throw new NullPointerException("Invoice.save: null output stream");
        if(this.emitter == null) throw new IllegalStateException("Invoice.save: null emitter");
        if(this.recipient == null) throw new IllegalStateException("Invoice.save: null recipient");
        
        Gson gson = new Gson();
        String json = gson.toJson(this);
        try (OutputStreamWriter writer = new OutputStreamWriter(os)) {
            writer.write(json);
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Invoice other = (Invoice) obj;
        if (!Objects.equals(this.number, other.number)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }
    
    
}