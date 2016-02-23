package ro.samlex.reelcash.data.validators;

import java.util.regex.Pattern;
import org.jdesktop.beansbinding.Validator;

public class PostalCodeValidator extends Validator<String> {

    private final String description;
    private final Pattern pattern = Pattern.compile("\\d{6}");

    public PostalCodeValidator(String message) {
        this.description = message;
    }

    @Override
    public Result validate(String postalCode) {
        if (postalCode != null && !postalCode.isEmpty()) {
            if (!pattern.matcher(postalCode).matches()) {
                return new Result(null, this.description);
            }
        }
        return null;
    }

}
