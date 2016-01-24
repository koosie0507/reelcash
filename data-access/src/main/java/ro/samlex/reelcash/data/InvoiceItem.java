package ro.samlex.reelcash.data;

import java.math.BigDecimal;

public class InvoiceItem {

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

    public void setName(String value) {
        if (value == null) {
            throw new NullPointerException("Null invoice item name");
        }
        this.name = value;
    }

    public String getName() {
        return this.name;
    }

    public void setVat(double value) {
        BigDecimal possibleVat = new BigDecimal(value);
        if (possibleVat.signum() < 0) {
            throw new IllegalArgumentException("VAT value can't be negative");
        }
        if (possibleVat.compareTo(new BigDecimal(1)) > 0) {
            throw new IllegalArgumentException("VAT value can't be greater than one");
        }
        this.vat = possibleVat;
    }

    public double getVAT() {
        return this.vat.doubleValue();
    }

    public void setQuantity(double qty) {
        BigDecimal possibleQuantity = new BigDecimal(qty);
        if (possibleQuantity.signum() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.quantity = possibleQuantity;
    }

    public double getQuantity() {
        return this.quantity.doubleValue();
    }

    public double getUnitPrice() {
        return unitPrice.doubleValue();
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = new BigDecimal(unitPrice);
    }
}
