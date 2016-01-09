package ro.samlex.reelcash.tests.data;

import org.junit.Assert;
import org.junit.Test;
import ro.samlex.reelcash.data.ContactChannel;
import ro.samlex.reelcash.data.Party;
import ro.samlex.reelcash.data.StreetAddress;

public class PartyTests {
    @Test
    public void given_party_with_specified_name__the_name_is_stored_in_memory() {
        Party p = new Party("a name");
        Assert.assertEquals("a name", p.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_party_with_null_name__illegal_argument_exception_is_thrown() {
        new Party(null);
    }

    @Test
    public void given_party_has_street_address__the_address_string_contains_street_address() {
        Party p = new Party("a").addAddress(new StreetAddress("street+number", "city", "region", "code", "country"));

        Assert.assertTrue(p.getAddresses().iterator().hasNext());

        ContactChannel address = p.getAddresses().iterator().next();
        Assert.assertEquals("street+number, city, region, code, country", address.toString());
    }

    @Test
    public void given_party_has_street_address__removing_it__leaves_party_without_addresses() {
        Party p = new Party("a")
                .addAddress(new StreetAddress("street+number", "city", "region", "code", "country"))
                .removeAt(0);

        Assert.assertFalse(p.getAddresses().iterator().hasNext());
    }

    @Test
    public void given_valid_banking_information__it_can_be_assigned_to_party() {
        Party p = new Party("a");
        p.setBankingInformation("Bank name", "IBAN");

        Assert.assertEquals("Bank name", p.getBankingInformation().getBank());
        Assert.assertEquals("IBAN", p.getBankingInformation().getAccountNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_banking_information_without_bank_name__illegal_argument_exception_is_thrown() {
        Party p = new Party("a");

        p.setBankingInformation(null, "IBAN");
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_banking_information_without_bank_account_number__illegal_argument_exception_is_thrown() {
        Party p = new Party("a");

        p.setBankingInformation("bank", null);
    }

    @Test
    public void given_valid_legal_information__it_can_be_assigned_to_party() {
        Party p = new Party("a");
        p.setLegalInformation("CIF/NIF/CNP", "ORC");

        Assert.assertEquals("CIF/NIF/CNP", p.getLegalInformation().getFiscalIdentification());
        Assert.assertEquals("ORC", p.getLegalInformation().getRegistrationNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_legal_information_without_fiscal_id__illegal_argument_exception_is_thrown() {
        Party p = new Party("a");

        p.setLegalInformation(null, "ORC");
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_legal_information_without_registration_number__illegal_argument_exception_is_thrown() {
        Party p = new Party("a");

        p.setLegalInformation("CIF", null);
    }
}
