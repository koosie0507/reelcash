package ro.samlex.reelcash.data;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import ro.samlex.reelcash.PropertyChangeObservable;

public class Party extends PropertyChangeObservable {

    private String name;
    private final StreetAddress address;
    private final BankAccount bankingInformation;
    private final LegalInformation legalInformation;

    public Party() {
        address = new StreetAddress();
        bankingInformation = new BankAccount();
        legalInformation = new LegalInformation();
    }

    @Getter
    public String getName() {
        return name;
    }

    @Setter
    public void setName(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        String oldValue = name;
        name = value;
        firePropertyChanged("name", oldValue, name);
    }

    @Getter
    public StreetAddress getAddress() {
        return this.address;
    }

    @Getter
    public BankAccount getBankAccount() {
        return bankingInformation;
    }

    @Getter
    public LegalInformation getLegalInformation() {
        return legalInformation;
    }

    public Party street(String street) {
        this.address.setStreet(street);
        return this;
    }

    public Party town(String town) {
        this.address.setTown(town);
        return this;
    }

    public Party postalCode(String postalCode) {
        this.address.setPostalCode(postalCode);
        return this;
    }

    public Party country(String country) {
        this.address.setCountry(country);
        return this;
    }

    public Party region(String region) {
        this.address.setRegion(region);
        return this;
    }

    public Party bank(String bank) {
        this.bankingInformation.setBank(bank);
        return this;
    }

    public Party accountNumber(String accountNumber) {
        this.bankingInformation.setAccountNumber(accountNumber);
        return this;
    }

    public Party fiscalId(String id) {
        this.legalInformation.setFiscalId(id);
        return this;
    }

    public Party registration(String registration) {
        this.legalInformation.setRegistration(registration);
        return this;
    }
}
