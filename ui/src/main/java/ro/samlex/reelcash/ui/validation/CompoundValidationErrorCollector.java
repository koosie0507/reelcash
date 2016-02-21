package ro.samlex.reelcash.ui.validation;

import java.util.ArrayList;

public class CompoundValidationErrorCollector extends ValidationErrorCollectorBase {

    private final ValidationErrorCollector[] collectors;

    public CompoundValidationErrorCollector(ValidationErrorCollector... collectors) {
        this.collectors = collectors;
    }

    @Override
    public boolean hasErrors() {
        for (ValidationErrorCollector collector : collectors) {
            if (collector.hasErrors()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterable<String> getErrors() {
        ArrayList<String> strings = new ArrayList<>();
        for (ValidationErrorCollector collector : collectors) {
            for (String error : collector.getErrors()) {
                if (!strings.contains(error)) {
                    strings.add(error);
                }
            }
        }
        return strings;
    }
}
