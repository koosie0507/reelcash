package ro.samlex.reelcash.data;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import ro.samlex.reelcash.PropertyChangeObservable;

public class StreetAddress extends PropertyChangeObservable {

    private String town;
    private String street;
    private String region;
    private String postalCode;
    private String country;

    @Setter
    public void setTown(String town) {
        String old = this.town;
        this.town = town;
        firePropertyChanged("town", old, this.town);
    }

    @Setter
    public void setStreet(String street) {
        String old = this.street;
        this.street = street;
        firePropertyChanged("street", old, this.street);
    }

    @Setter
    public void setRegion(String region) {
        String old = this.region;
        this.region = region;
        firePropertyChanged("region", old, this.region);
    }

    @Setter
    public void setPostalCode(String postalCode) {
        String old = this.postalCode;
        this.postalCode = postalCode;
        firePropertyChanged("postalCode", old, this.postalCode);
    }

    @Setter
    public void setCountry(String country) {
        String old = this.country;
        this.country = country;
        firePropertyChanged("country", old, this.country);
    }

    @Getter
    public String getStreet() {
        return street;
    }

    @Getter
    public String getTown() {
        return town;
    }

    @Getter
    public String getRegion() {
        return region;
    }

    @Getter
    public String getPostalCode() {
        return postalCode;
    }

    @Getter
    public String getCountry() {
        return country;
    }
}
