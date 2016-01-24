package ro.samlex.reelcash.data;

import java.io.Serializable;

public class BankInformation implements Serializable {
    private final String bank;
    private final String accountNumber;

    public BankInformation(String bank, String accountNumber) {
        if(bank == null || bank.isEmpty())
            throw new NullPointerException("BankInformation: bank name is null");
        if(accountNumber == null || accountNumber.isEmpty())
            throw new NullPointerException("BankInformation: account number is null");
        this.bank = bank;
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getBank() {
        return bank;
    }
}
