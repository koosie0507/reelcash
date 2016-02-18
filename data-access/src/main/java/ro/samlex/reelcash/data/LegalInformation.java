package ro.samlex.reelcash.data;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import ro.samlex.reelcash.PropertyChangeObservable;

public class LegalInformation extends PropertyChangeObservable {

    private String fiscalId;
    private String registration;

    @Getter
    public String getFiscalId() {
        return fiscalId;
    }

    @Setter
    public void setFiscalId(String id) {
        String old = this.fiscalId;
        this.fiscalId = id;
        firePropertyChanged("fiscalId", old, this.fiscalId);
    }

    @Getter
    public String getRegistration() {
        return registration;
    }

    @Setter
    public void setRegistration(String registration) {
        String old = this.fiscalId;
        this.registration = registration;
        firePropertyChanged("registration", old, this.registration);
    }

}
