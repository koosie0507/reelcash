package ro.samlex.reelcash.data;

import ro.samlex.reelcash.PropertyChangeObservable;

public class Party extends PropertyChangeObservable {

    private String name;
    private StreetAddress address;
    private BankInformation bankingInformation;
    private LegalInformation legalInformation;

    public Party() {
        name = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
        name = value;
    }

    public StreetAddress getAddress() {
        return this.address;
    }

    public void setAddress(StreetAddress address) {
        Object old = this.address;
        this.address = address;
        firePropertyChanged("address", old, this.address);
    }

    public BankInformation getBankingInformation() {
        return bankingInformation;
    }

    public void setBankingInformation(String bankName, String accountNumber) {
        Object old = this.bankingInformation;
        this.bankingInformation = new BankInformation(bankName, accountNumber);
        firePropertyChanged("bankingInformation", old, this.bankingInformation);
    }

    public LegalInformation getLegalInformation() {
        return legalInformation;
    }

    public void setLegalInformation(String fiscalId, String registrationNumber) {
        Object old = this.legalInformation;
        this.legalInformation = new LegalInformation(fiscalId, registrationNumber);
        firePropertyChanged("legalInformation", old, this.legalInformation);
    }
}
