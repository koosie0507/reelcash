package ro.samlex.reelcash.data;

import java.io.Serializable;

public class LegalInformation implements Serializable {
    private final String fiscalIdentification;
    private final String registrationNumber;

    public LegalInformation(String fiscalIdentification, String registrationNumber) {
        if (fiscalIdentification == null || fiscalIdentification.isEmpty())
            throw new NullPointerException("LegalInformation: VAT ID is null");
        if (registrationNumber == null || registrationNumber.isEmpty())
            throw new NullPointerException("LegalInformation: registration number is null");
        this.fiscalIdentification = fiscalIdentification;
        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getFiscalIdentification() {
        return fiscalIdentification;
    }
}
