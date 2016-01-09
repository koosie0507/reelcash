package ro.samlex.reelcash.data;

import java.util.ArrayList;
import java.util.List;

public class Party {
    private final String name;
    private List<ContactChannel> addresses;
    private BankInformation bankingInformation;
    private LegalInformation legalInformation;

    public Party(String name) {
        if(name == null) throw new IllegalArgumentException();
        addresses = new ArrayList<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Party addAddress(ContactChannel address) {
        addresses.add(address);
        return this;
    }

    public Iterable<ContactChannel> getAddresses() {
        return addresses;
    }

    public Party removeAt(int addressIndex) {
        addresses.remove(addressIndex);
        return this;
    }

    public BankInformation getBankingInformation() {
        return bankingInformation;
    }

    public void setBankingInformation(String bankName, String accountNumber) {
        this.bankingInformation = new BankInformation(bankName, accountNumber);
    }

    public LegalInformation getLegalInformation() {
        return legalInformation;
    }

    public void setLegalInformation(String fiscalId, String registrationNumber) {
        this.legalInformation = new LegalInformation(fiscalId, registrationNumber);
    }
}
