package ro.samlex.reelcash.ui.validation;

import java.util.Collection;
import org.jdesktop.beansbinding.Validator;

public class NonEmptyCollectionValidator extends Validator {

    private final String message;

    public NonEmptyCollectionValidator(String message) {
        this.message = (message == null)
                ? "At least one item is required"
                : message;
    }

    @Override
    public Result validate(Object t) {
        if (t == null) {
            return new Validator.Result(null, this.message);
        } else if (t instanceof Collection && ((Collection) t).isEmpty()) {
            return new Validator.Result(null, this.message);
        } else {
            return null;
        }
    }
}
