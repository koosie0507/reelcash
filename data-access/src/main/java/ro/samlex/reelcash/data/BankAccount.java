package ro.samlex.reelcash.data;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import ro.samlex.reelcash.PropertyChangeObservable;

public class BankAccount extends PropertyChangeObservable {

    private String bank;
    private String accountNumber;

    @Getter
    public String getBank() {
        return bank;
    }

    @Setter
    public void setBank(String bank) {
        String oldValue = this.bank;
        this.bank = bank;
        firePropertyChanged("bank", oldValue, this.bank);
    }

    @Getter
    public String getAccountNumber() {
        return accountNumber;
    }

    @Setter
    public void setAccountNumber(String accountNumber) {
        String oldValue = this.bank;
        this.accountNumber = accountNumber;
        firePropertyChanged("accountNumber", oldValue, this.accountNumber);
    }
}
