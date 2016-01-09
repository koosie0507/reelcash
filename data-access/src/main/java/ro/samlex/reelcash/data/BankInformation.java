package ro.samlex.reelcash.data;

public class BankInformation {
    private final String bank;
    private final String accountNumber;

    public BankInformation(String bank, String accountNumber) {
        if(bank == null || bank.isEmpty())
            throw new IllegalArgumentException();
        if(accountNumber == null || accountNumber.isEmpty())
            throw new IllegalArgumentException();
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
