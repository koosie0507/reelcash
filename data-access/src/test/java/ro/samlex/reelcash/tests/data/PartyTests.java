package ro.samlex.reelcash.tests.data;

import org.junit.Assert;
import org.junit.Test;
import ro.samlex.reelcash.data.Party;
import ro.samlex.reelcash.data.StreetAddress;

public class PartyTests {
    @Test
    public void given_party_with_specified_name__the_name_is_stored_in_memory() {
        Party p = new Party();
        p.setName("a name");
        Assert.assertEquals("a name", p.getName());
    }

    @Test(expected = NullPointerException.class)
    public void given_party_with_null_name__NullPointerException_is_thrown() {
        Party p = new Party();
        p.setName(null);
    }

    @Test
    public void given_party_has_street_address__the_address_string_contains_street_address() {
        Party p = new Party();
        
        p.setAddress(new StreetAddress("street+number", "city", "region", "code", "country"));

        Assert.assertEquals("street+number, city, region, code, country", p.getAddress().toString());
    }

    @Test
    public void given_valid_banking_information__it_can_be_assigned_to_party() {
        Party p = new Party();
        p.setBankingInformation("Bank name", "IBAN");

        Assert.assertEquals("Bank name", p.getBankingInformation().getBank());
        Assert.assertEquals("IBAN", p.getBankingInformation().getAccountNumber());
    }

    @Test(expected = NullPointerException.class)
    public void given_banking_information_without_bank_name__illegal_argument_exception_is_thrown() {
        Party p = new Party();

        p.setBankingInformation(null, "IBAN");
    }

    @Test(expected = NullPointerException.class)
    public void given_banking_information_without_bank_account_number__illegal_argument_exception_is_thrown() {
        Party p = new Party();

        p.setBankingInformation("bank", null);
    }

    @Test
    public void given_valid_legal_information__it_can_be_assigned_to_party() {
        Party p = new Party();
        p.setLegalInformation("CIF/NIF/CNP", "ORC");

        Assert.assertEquals("CIF/NIF/CNP", p.getLegalInformation().getFiscalIdentification());
        Assert.assertEquals("ORC", p.getLegalInformation().getRegistrationNumber());
    }

    @Test(expected = NullPointerException.class)
    public void given_legal_information_without_fiscal_id__illegal_argument_exception_is_thrown() {
        Party p = new Party();

        p.setLegalInformation(null, "ORC");
    }

    @Test(expected = NullPointerException.class)
    public void given_legal_information_without_registration_number__illegal_argument_exception_is_thrown() {
        Party p = new Party();

        p.setLegalInformation("CIF", null);
    }
}
