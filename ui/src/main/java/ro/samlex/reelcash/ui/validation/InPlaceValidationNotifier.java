package ro.samlex.reelcash.ui.validation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.Binding;

public class InPlaceValidationNotifier extends AbstractBindingListener {

    private final HashMap<Binding, ValidatedComponentInfo> bindingErrors;

    public InPlaceValidationNotifier() {
        this.bindingErrors = new HashMap<>();
    }

    public boolean hasErrors() {
        return !this.bindingErrors.isEmpty();
    }

    public String getErrorString() {
        StringBuilder errorMessageBuilder = new StringBuilder("There are some errors that need your attention");
        errorMessageBuilder.append(System.lineSeparator());
        errorMessageBuilder.append(System.lineSeparator());
        for (ValidatedComponentInfo info : this.bindingErrors.values()) {
            errorMessageBuilder.append(info.errorMessage);
            errorMessageBuilder.append(";");
            errorMessageBuilder.append(System.lineSeparator());

        }
        errorMessageBuilder.append(System.lineSeparator());
        errorMessageBuilder.append(System.lineSeparator());
        errorMessageBuilder.append("Please correct the errors and try saving again.");
        return errorMessageBuilder.toString();
    }

    @Override
    public void synced(Binding binding) {
        if (!bindingErrors.containsKey(binding)) {
            return;
        }
        bindingErrors.remove(binding).setDefaultMode();
    }

    @Override
    public void syncFailed(Binding binding, Binding.SyncFailure failure) {
        if (failure.getType() != Binding.SyncFailureType.VALIDATION_FAILED) {
            return;
        }

        ValidatedComponentInfo info;
        if (this.bindingErrors.containsKey(binding)) {
            info = this.bindingErrors.get(binding);
        } else {
            final JComponent target = (JComponent) binding.getTargetObject();
            final String errorMessage = failure.getValidationResult().getDescription();
            info = new ValidatedComponentInfo(target, errorMessage);
        }
        info.setErrorMode();
        this.bindingErrors.put(binding, info);
    }

    private static final class ValidatedComponentInfo {

        private final Border componentBorder;
        private final String componentTooltip;
        private boolean isInErrorMode;
        final JComponent component;
        final String errorMessage;

        public ValidatedComponentInfo(JComponent component, String errorMessage) {
            this.isInErrorMode = false;
            this.component = component;
            this.componentBorder = this.component.getBorder();
            this.componentTooltip = this.component.getToolTipText();
            this.errorMessage = errorMessage;
        }

        final void setErrorMode() {
            if (!this.isInErrorMode) {
                this.component.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(Color.red, 1),
                                this.componentBorder));
                this.component.setToolTipText(errorMessage);
                this.isInErrorMode = true;
            }
        }

        final void setDefaultMode() {
            if (this.isInErrorMode) {
                this.component.setBorder(componentBorder);
                this.component.setToolTipText(componentTooltip);
                this.isInErrorMode = false;
            }
        }
    }
}
