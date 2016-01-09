package ro.samlex.reelcash.data;

public class LegalInformation {
    private final String fiscalIdentification;
    private final String registrationNumber;

    public LegalInformation(String fiscalIdentification, String registrationNumber) {
        if (fiscalIdentification == null || fiscalIdentification.isEmpty())
            throw new IllegalArgumentException();
        if (registrationNumber == null || registrationNumber.isEmpty())
            throw new IllegalArgumentException();
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
