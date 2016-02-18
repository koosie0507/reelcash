package ro.samlex.reelcash.tests.data;

import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import ro.samlex.reelcash.data.Party;

public class PartyTests {

    private static final String TEST_STRING = "A string that someone else is very unlikely to have thought of";

    @Test
    public void givenParty_settingNameToAValidValue_partyWillHaveGivenName() {
        Party p = new Party();
        p.setName("a name");
        Assert.assertEquals("a name", p.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenParty_settingNameToNull_throwsIllegalArgumentException() {
        Party p = new Party();
        p.setName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenParty_settingNameToEmptyString_throwsIllegalArgumentException() {
        Party p = new Party();
        p.setName("");
    }

    @Test
    public void givenParty_usingStreet_streetAddressRelfectsValue() {
        Party p = new Party().street(TEST_STRING);

        assertEquals(TEST_STRING, p.getAddress().getStreet());
    }

    @Test
    public void givenParty_usingTown_streetAddressRelfectsValue() {
        Party p = new Party().town(TEST_STRING);

        assertEquals(TEST_STRING, p.getAddress().getTown());
    }

    @Test
    public void givenParty_usingPostalCode_streetAddressRelfectsValue() {
        Party p = new Party().postalCode(TEST_STRING);

        assertEquals(TEST_STRING, p.getAddress().getPostalCode());
    }

    @Test
    public void givenParty_usingCountry_streetAddressRelfectsValue() {
        Party p = new Party().country(TEST_STRING);

        assertEquals(TEST_STRING, p.getAddress().getCountry());
    }

    @Test
    public void givenParty_usingRegion_streetAddressRelfectsValue() {
        Party p = new Party().region(TEST_STRING);

        assertEquals(TEST_STRING, p.getAddress().getRegion());
    }

    @Test
    public void givenParty_usingBank_streetAddressRelfectsValue() {
        Party p = new Party().bank(TEST_STRING);

        assertEquals(TEST_STRING, p.getBankAccount().getBank());
    }

    @Test
    public void givenParty_usingAccountNumber_streetAddressRelfectsValue() {
        Party p = new Party().accountNumber(TEST_STRING);

        assertEquals(TEST_STRING, p.getBankAccount().getAccountNumber());
    }

    @Test
    public void givenParty_usingFiscalId_streetAddressRelfectsValue() {
        Party p = new Party().fiscalId(TEST_STRING);

        assertEquals(TEST_STRING, p.getLegalInformation().getFiscalId());
    }

    @Test
    public void givenParty_usingRegistration_streetAddressRelfectsValue() {
        Party p = new Party().registration(TEST_STRING);

        assertEquals(TEST_STRING, p.getLegalInformation().getRegistration());
    }
}
