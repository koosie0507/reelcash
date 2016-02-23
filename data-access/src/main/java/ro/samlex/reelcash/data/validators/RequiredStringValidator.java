package ro.samlex.reelcash.data.validators;

import org.jdesktop.beansbinding.Validator;

public class RequiredStringValidator extends Validator<String> {

    private static final String DEFAULT_MESSAGE = "* value required";
    private final String message;

    public RequiredStringValidator(String message) {
        this.message = message == null || message.isEmpty() ? DEFAULT_MESSAGE : message;
    }

    @Override
    public Validator.Result validate(String arg) {
        if (arg == null || arg.isEmpty()) {
            return new Validator.Result(null, message);
        }
        return null;
    }
}
