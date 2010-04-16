package com.google.code.reelcash.data;

import com.google.code.reelcash.data.banks.BankAccountNode;
import com.google.code.reelcash.data.banks.BankNode;
import com.google.code.reelcash.data.contacts.ContactIdentityNode;
import com.google.code.reelcash.data.contacts.ContactLocationNode;
import com.google.code.reelcash.data.contacts.ContactNode;
import com.google.code.reelcash.data.contacts.ContactPhoneNode;
import com.google.code.reelcash.data.contacts.ContactWebAddressNode;
import com.google.code.reelcash.data.contacts.PhoneNode;
import com.google.code.reelcash.data.contacts.WebAddressNode;
import com.google.code.reelcash.data.geo.CityNode;
import com.google.code.reelcash.data.geo.CountryNode;
import com.google.code.reelcash.data.geo.CountyNode;
import com.google.code.reelcash.data.geo.LocationNode;
import com.google.code.reelcash.data.geo.RegionNode;
import com.google.code.reelcash.data.layout.DataLayout;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import java.util.Hashtable;

/**
 * Provides a data layout for the registries supported by the application.
 * @author andrei.olar
 */
public class RegistryLayout extends DataLayout {

    public static final String TEXT = "_text";
    private static final Object SYNC_ROOT = new Object();
    private static RegistryLayout instance;
    private final String name;
    private final Hashtable<DataLayoutNode, String> descriptions;

    private RegistryLayout() {
        name = RegistryNames.getString("registrylayout_name");

        descriptions = new Hashtable<DataLayoutNode, String>();
        CountryNode.getInstance().setText(RegistryNames.getString(CountryNode.getInstance().getName().concat(TEXT)));
        RegionNode.getInstance().setText(RegistryNames.getString(RegionNode.getInstance().getName().concat(TEXT)));
        CountyNode.getInstance().setText(RegistryNames.getString(CountyNode.getInstance().getName().concat(TEXT)));
        CityNode.getInstance().setText(RegistryNames.getString(CityNode.getInstance().getName().concat(TEXT)));
        LocationNode.getInstance().setText(RegistryNames.getString(LocationNode.getInstance().getName().concat(TEXT)));
        ContactNode.getInstance().setText(RegistryNames.getString(ContactNode.getInstance().getName().concat(TEXT)));
        ContactIdentityNode.getInstance().setText(RegistryNames.getString(ContactIdentityNode.getInstance().getName().concat(TEXT)));
        ContactLocationNode.getInstance().setText(RegistryNames.getString(ContactLocationNode.getInstance().getName().concat(TEXT)));
        ContactPhoneNode.getInstance().setText(RegistryNames.getString(ContactPhoneNode.getInstance().getName().concat(TEXT)));
        ContactWebAddressNode.getInstance().setText(RegistryNames.getString(ContactWebAddressNode.getInstance().getName().concat(TEXT)));
        PhoneNode.getInstance().setText(RegistryNames.getString(PhoneNode.getInstance().getName().concat(TEXT)));
        WebAddressNode.getInstance().setText(RegistryNames.getString(WebAddressNode.getInstance().getName().concat(TEXT)));
        BankNode.getInstance().setText(RegistryNames.getString(BankNode.getInstance().getName().concat(TEXT)));
        BankAccountNode.getInstance().setText(RegistryNames.getString(BankAccountNode.getInstance().getName().concat(TEXT)));
        addRoot(CountryNode.getInstance());
        addRoot(RegionNode.getInstance());
        addRoot(CountyNode.getInstance());
        addRoot(CityNode.getInstance());
        addRoot(LocationNode.getInstance());
        addRoot(ContactNode.getInstance());
        addRoot(PhoneNode.getInstance());
        addRoot(WebAddressNode.getInstance());
        addRoot(BankNode.getInstance());
    }

    public static RegistryLayout getInstance() {
        synchronized (SYNC_ROOT) {
            if (null == instance)
                instance = new RegistryLayout();
        }
        return instance;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final RegistryLayout other = (RegistryLayout) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
            return false;
        return true;
    }



    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return name;
    }
}
