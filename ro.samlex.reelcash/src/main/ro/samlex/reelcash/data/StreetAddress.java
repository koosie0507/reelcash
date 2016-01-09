package ro.samlex.reelcash.data;

public class StreetAddress implements ContactChannel {
    private final String town;
    private final String street;
    private final String region;
    private final String postalCode;
    private final String country;
    private final String formattedAddress;

    public StreetAddress(String street, String town, String region, String postalCode, String country) {
        this.street = street;
        this.town = town;
        this.region = region;
        this.postalCode = postalCode;
        this.country = country;
        this.formattedAddress = street + ", " + town + ", " + region + ", " + postalCode + ", " + country;
    }

    public String getStreet() {
        return street;
    }

    public String getTown() {
        return town;
    }

    public String getRegion() {
        return region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return this.formattedAddress;
    }
}
