package ro.samlex.reelcash.ui.validation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.Binding;

public final class JComponentBindingListener extends AbstractBindingListener implements ValidationErrorCollector {

    private final HashMap<Binding, ValidatedComponentInfo> bindingErrors;
    private final ValidationErrorCollectorBase collector;

    public JComponentBindingListener() {
        this.bindingErrors = new HashMap<>();
        this.collector = new ValidationErrorCollectorBase() {
            @Override
            public Iterable<String> getErrors() {
                ArrayList<String> errors = new ArrayList<>();
                for (ValidatedComponentInfo info : bindingErrors.values()) {
                    errors.add(info.errorMessage);
                }
                return errors;
            }

            @Override
            public boolean hasErrors() {
                return bindingErrors.isEmpty();
            }

        };
    }

    @Override
    public boolean hasErrors() {
        return collector.hasErrors();
    }

    @Override
    public Iterable<String> getErrors() {
        return collector.getErrors();
    }

    @Override
    public String getErrorString() {
        return collector.getErrorString();
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
