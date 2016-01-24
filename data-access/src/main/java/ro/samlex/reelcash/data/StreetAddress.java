package ro.samlex.reelcash.data;

import java.io.Serializable;

public class StreetAddress implements Serializable {
    private String town;
    private String street;
    private String region;
    private String postalCode;
    private String country;
    private String formattedAddress;

    public StreetAddress() {
        this.street = this.town = this.region = this.postalCode = this.country = this.formattedAddress = "";
    }
    
    private void validateNotNull(Object nullable, String message) {
        if(nullable == null)
            throw new NullPointerException(message);
    }
    
    public StreetAddress(String street, String town, String region, String postalCode, String country) {
        validateNotNull(street, "StreetAddress: street is null");
        validateNotNull(town, "StreetAddress: town is null");
        validateNotNull(region, "StreetAddress: region is null");
        validateNotNull(postalCode, "StreetAddress: postalCode is null");
        validateNotNull(country, "StreetAddress: country is null");
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
