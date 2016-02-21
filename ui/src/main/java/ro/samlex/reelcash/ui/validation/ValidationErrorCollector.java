package ro.samlex.reelcash.ui.validation;

public interface ValidationErrorCollector {

    boolean hasErrors();

    Iterable<String> getErrors();

    String getErrorString();
}
