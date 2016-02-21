package ro.samlex.reelcash.ui.validation;

import org.jdesktop.beansbinding.Validator;

public class RequiredStringValidator extends Validator<String> {

    private final String message;

    public RequiredStringValidator(String message) {
        this.message = message;

    }

    @Override
    public Validator.Result validate(String arg) {
        if (arg == null || arg.isEmpty()) {
            return new Validator.Result(null, message);
        }
        return null;
    }
}
