package ro.samlex.reelcash.data;

import java.math.BigDecimal;
import java.util.Objects;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import ro.samlex.reelcash.PropertyChangeObservable;

public class InvoiceItem extends PropertyChangeObservable {

    private String name;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private BigDecimal vat;

    public InvoiceItem() {
        this.name = "";
        this.quantity = new BigDecimal(1);
        this.unitPrice = new BigDecimal("0.0");
        this.vat = new BigDecimal("0.24");
    }

    @Getter
    public String getName() {
        return this.name;
    }

    @Setter
    public void setName(String value) {
        String old = this.name;
        this.name = value;
        firePropertyChanged("name", old, value);
    }

    @Getter
    public double getVat() {
        return this.vat.doubleValue();
    }

    @Setter
    public void setVat(BigDecimal value) {
        if (value.signum() < 0) {
            throw new IllegalArgumentException("VAT value can't be negative");
        }
        if (value.compareTo(new BigDecimal(1)) > 0) {
            throw new IllegalArgumentException("VAT value can't be greater than one");
        }
        BigDecimal old = this.vat;
        this.vat = value;
        firePropertyChanged("vat", old, value);
    }

    @Getter
    public double getQuantity() {
        return this.quantity.doubleValue();
    }

    @Setter
    public void setQuantity(BigDecimal value) {
        if (value.signum() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        BigDecimal old = this.quantity;
        this.quantity = value;
        firePropertyChanged("quantity", old, value);
    }

    @Getter
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    @Setter
    public void setUnitPrice(BigDecimal value) {
        BigDecimal old = this.unitPrice;
        this.unitPrice = value;
        firePropertyChanged("unitPrice", old, value);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.quantity);
        hash = 17 * hash + Objects.hashCode(this.unitPrice);
        hash = 17 * hash + Objects.hashCode(this.vat);
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
        final InvoiceItem other = (InvoiceItem) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.quantity, other.quantity)) {
            return false;
        }
        if (!Objects.equals(this.unitPrice, other.unitPrice)) {
            return false;
        }
        return Objects.equals(this.vat, other.vat);
    }

}
