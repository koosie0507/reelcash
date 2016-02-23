package ro.samlex.reelcash.tests.data.validators;

import org.jdesktop.beansbinding.Validator.Result;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import ro.samlex.reelcash.data.validators.PostalCodeValidator;

public class PostalCodeValidatorTests {

    @Test
    public void validate_invalidPostalCode_returnsResultWithSpecifiedDescription() {
        final String expected = "expected";
        PostalCodeValidator sut = new PostalCodeValidator(expected);

        Result actual = sut.validate("a");

        assertEquals(expected, actual.getDescription());
    }

    @Test
    public void validate_nullPostalCode_returnsNull() {
        assertNull(new PostalCodeValidator("message").validate(null));
    }

    @Test
    public void validate_emptyPostalCode_returnsNull() {
        assertNull(new PostalCodeValidator("message").validate(null));
    }
    
    @Test
    public void validate_validPostalCode_returnsNull() {
        assertNull(new PostalCodeValidator("message").validate("123456"));
    }
    
    @Test
    public void validate_postalCodeContainingMoreThan6Digits_returnsResultWithExpectedDescription() {
        final String expected = "expected";
        PostalCodeValidator sut = new PostalCodeValidator(expected);

        Result actual = sut.validate("1234567");

        assertEquals(expected, actual.getDescription());        
    }
    
    @Test
    public void validate_postalCodeContainingLessThan6Digits_returnsResultWithExpectedDescription() {
        final String expected = "expected";
        PostalCodeValidator sut = new PostalCodeValidator(expected);

        Result actual = sut.validate("12345");

        assertEquals(expected, actual.getDescription());        
    }
    
    @Test
    public void validate_postalCodeWithExactlyOneNonDigit_returnsResultWithExpectedDescription() {
        final String expected = "expected";
        PostalCodeValidator sut = new PostalCodeValidator(expected);

        Result actual = sut.validate("I23456");

        assertEquals(expected, actual.getDescription());        
    }
}
