package ro.samlex.reelcash.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import ro.samlex.reelcash.PropertyChangeObservable;

public class Invoice extends PropertyChangeObservable {

    private final UUID uuid = UUID.randomUUID();
    private Integer number = new Integer(1);
    private Date date = today();
    private Party recipient;
    private Party emitter;
    private final List<InvoiceItem> invoicedItems = new ArrayList<>();

    private static Date today() {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.AM_PM);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        return cal.getTime();
    }

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
            throw new IllegalArgumentException("Invoice.setEmitter: new emitter is null");
        }
        this.emitter = value;
    }

    public void setRecipient(Party value) {
        if (value == null) {
            throw new IllegalArgumentException("Invoice.setRecipient: new recipient is null");
        }
        recipient = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        int old = this.number;
        this.number = number;
        firePropertyChanged("number", old, this.number);
    }

    @Override
    public int hashCode() {
        return this.uuid.hashCode();
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
        return Objects.equals(this.uuid, ((Invoice) obj).uuid);
    }

    @Override
    public String toString() {
        return uuid.toString();
    }
}
