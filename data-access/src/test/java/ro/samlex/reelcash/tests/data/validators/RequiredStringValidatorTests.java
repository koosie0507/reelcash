package ro.samlex.reelcash.tests.data.validators;

import org.jdesktop.beansbinding.Validator.Result;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import ro.samlex.reelcash.data.validators.RequiredStringValidator;

public class RequiredStringValidatorTests {

    @Test
    public void validate_nullArg_showsSpecifiedMessage() {
        final String expected = "test";
        RequiredStringValidator sut = new RequiredStringValidator(expected);

        Result res = sut.validate(null);

        assertEquals(expected, res.getDescription());
    }

    @Test
    public void validate_emptyArg_showsSpecifiedMessage() {
        final String expected = "test";
        RequiredStringValidator sut = new RequiredStringValidator(expected);

        Result res = sut.validate(null);

        assertEquals(expected, res.getDescription());
    }

    @Test
    public void validate_validArg_returnsNull() {
        assertNull(new RequiredStringValidator("").validate("a"));
    }

    @Test
    public void validate_nullArgAndNullMessage_returnsResultWithDefaultDescription() {
        RequiredStringValidator sut = new RequiredStringValidator(null);

        Result res = sut.validate(null);

        assertEquals("* value required", res.getDescription());
    }
    
    @Test
    public void validate_nullArgAndEmptyMessage_returnsResultWithDefaultDescription() {
        RequiredStringValidator sut = new RequiredStringValidator("");
        
        Result res = sut.validate(null);
        
        assertEquals("* value required", res.getDescription());
    }
}
