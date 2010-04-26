package com.google.code.reelcash.data;

import com.google.code.reelcash.data.banks.BankAccountNode;
import com.google.code.reelcash.data.banks.BankNode;
import com.google.code.reelcash.data.banks.CurrencyNode;
import com.google.code.reelcash.data.business.BusinessNode;
import com.google.code.reelcash.data.business.BusinessRepresentativeNode;
import com.google.code.reelcash.data.business.FiscalIdentificationNode;
import com.google.code.reelcash.data.business.LegalStatusNode;
import com.google.code.reelcash.data.contacts.ContactIdentityNode;
import com.google.code.reelcash.data.contacts.ContactLocationNode;
import com.google.code.reelcash.data.contacts.ContactNode;
import com.google.code.reelcash.data.contacts.ContactPhoneNode;
import com.google.code.reelcash.data.contacts.ContactWebAddressNode;
import com.google.code.reelcash.data.contacts.PhoneNode;
import com.google.code.reelcash.data.contacts.WebAddressNode;
import com.google.code.reelcash.data.documents.DocumentAttributeNode;
import com.google.code.reelcash.data.documents.DocumentStateNode;
import com.google.code.reelcash.data.documents.DocumentTypeNode;
import com.google.code.reelcash.data.documents.SeriesRangeNode;
import com.google.code.reelcash.data.geo.CityNode;
import com.google.code.reelcash.data.geo.CountryNode;
import com.google.code.reelcash.data.geo.CountyNode;
import com.google.code.reelcash.data.geo.LocationNode;
import com.google.code.reelcash.data.geo.RegionNode;
import com.google.code.reelcash.data.goods.GoodNode;
import com.google.code.reelcash.data.goods.UnitNode;
import com.google.code.reelcash.data.layout.DataLayout;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.permissions.BusinessPermissionNode;
import com.google.code.reelcash.data.permissions.PermissionNode;
import com.google.code.reelcash.data.taxes.ExciseTypeNode;
import com.google.code.reelcash.data.taxes.GoodExciseNode;
import com.google.code.reelcash.data.taxes.GoodTaxNode;
import com.google.code.reelcash.data.taxes.TaxTypeNode;
import com.google.code.reelcash.data.taxes.VatTypeNode;
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
        BusinessNode.getInstance().setText(RegistryNames.getString(BusinessNode.getInstance().getName().concat(TEXT)));
        BusinessRepresentativeNode.getInstance().setText(RegistryNames.getString(BusinessRepresentativeNode.getInstance().getName().concat(TEXT)));
        FiscalIdentificationNode.getInstance().setText(RegistryNames.getString(FiscalIdentificationNode.getInstance().getName().concat(TEXT)));
        LegalStatusNode.getInstance().setText(RegistryNames.getString(LegalStatusNode.getInstance().getName().concat(TEXT)));
        PermissionNode.getInstance().setText(RegistryNames.getString(PermissionNode.getInstance().getName().concat(TEXT)));
        BusinessPermissionNode.getInstance().setText(RegistryNames.getString(BusinessPermissionNode.getInstance().getName().concat(TEXT)));
        DocumentTypeNode.getInstance().setText(RegistryNames.getString(DocumentTypeNode.getInstance().getName().concat(TEXT)));
        DocumentAttributeNode.getInstance().setText(RegistryNames.getString(DocumentAttributeNode.getInstance().getName().concat(TEXT)));
        DocumentStateNode.getInstance().setText(RegistryNames.getString(DocumentStateNode.getInstance().getName().concat(TEXT)));
        SeriesRangeNode.getInstance().setText(RegistryNames.getString(SeriesRangeNode.getInstance().getName().concat(TEXT)));
        CurrencyNode.getInstance().setText(RegistryNames.getString(CurrencyNode.getInstance().getName().concat(TEXT)));
        VatTypeNode.getInstance().setText(RegistryNames.getString(VatTypeNode.getInstance().getName().concat(TEXT)));
        ExciseTypeNode.getInstance().setText(RegistryNames.getString(ExciseTypeNode.getInstance().getName().concat(TEXT)));
        TaxTypeNode.getInstance().setText(RegistryNames.getString(TaxTypeNode.getInstance().getName().concat(TEXT)));
        GoodNode.getInstance().setText(RegistryNames.getString(GoodNode.getInstance().getName().concat(TEXT)));
        GoodTaxNode.getInstance().setText(RegistryNames.getString(GoodTaxNode.getInstance().getName().concat(TEXT)));
        GoodExciseNode.getInstance().setText(RegistryNames.getString(GoodExciseNode.getInstance().getName().concat(TEXT)));
        UnitNode.getInstance().setText(RegistryNames.getString(UnitNode.getInstance().getName().concat(TEXT)));
        addRoot(CountryNode.getInstance());
        addRoot(RegionNode.getInstance());
        addRoot(CountyNode.getInstance());
        addRoot(CityNode.getInstance());
        addRoot(LocationNode.getInstance());
        addRoot(ContactNode.getInstance());
        addRoot(PhoneNode.getInstance());
        addRoot(WebAddressNode.getInstance());
        addRoot(CurrencyNode.getInstance());
        addRoot(BankNode.getInstance());
        addRoot(LegalStatusNode.getInstance());
        addRoot(BusinessNode.getInstance());
        addRoot(PermissionNode.getInstance());
        addRoot(DocumentTypeNode.getInstance());
        addRoot(DocumentStateNode.getInstance());
        addRoot(SeriesRangeNode.getInstance());
        addRoot(VatTypeNode.getInstance());
        addRoot(ExciseTypeNode.getInstance());
        addRoot(TaxTypeNode.getInstance());
        addRoot(GoodNode.getInstance());
        addRoot(GoodTaxNode.getInstance());
        addRoot(GoodExciseNode.getInstance());
        addRoot(UnitNode.getInstance());
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
