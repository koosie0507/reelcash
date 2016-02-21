package ro.samlex.reelcash.ui.validation;

public abstract class ValidationErrorCollectorBase implements ValidationErrorCollector {

    @Override
    public final String getErrorString() {
        final StringBuilder builder = new StringBuilder();
        appendMessageHeader(builder);
        for (String errorMessage : getErrors()) {
            appendErrorMessage(builder, errorMessage);
        }
        appendMessageFooter(builder);
        return builder.toString();
    }

    protected void appendMessageHeader(StringBuilder builder) {
        builder.append("There are some errors that need your attention");
        builder.append(System.lineSeparator());
        builder.append(System.lineSeparator());

    }

    protected void appendMessageFooter(StringBuilder builder) {
        builder.append(System.lineSeparator());
        builder.append(System.lineSeparator());
        builder.append("Please correct the errors and try saving again.");
    }

    protected void appendErrorMessage(StringBuilder builder, String errorMessage) {
        builder.append(errorMessage);
        builder.append(";");
        builder.append(System.lineSeparator());
    }
}
