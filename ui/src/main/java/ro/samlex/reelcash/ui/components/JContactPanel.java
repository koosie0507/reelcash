package ro.samlex.reelcash.ui.components;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Validator;
import ro.samlex.reelcash.data.Party;
import ro.samlex.reelcash.ui.validation.InPlaceValidationNotifier;
import ro.samlex.reelcash.ui.validation.RequiredStringValidator;
import ro.samlex.reelcash.viewmodels.SimpleViewModel;

public class JContactPanel extends javax.swing.JPanel {
    
    private final InPlaceValidationNotifier validationNotifier;

    public JContactPanel() {
        validationNotifier = new InPlaceValidationNotifier();
        initComponents();
        this.bindingGroup.addBindingListener(validationNotifier);
    }

    @Getter
    public SimpleViewModel<Party> getDataContext() {
        return this.dataContext;
    }
    
    public InPlaceValidationNotifier getValidationErrorCollector() {
        return this.validationNotifier;
    }
    
    public void forceValidation() {
        for(Binding b: this.bindingGroup.getBindings()){
            b.saveAndNotify();
        }
    }
    
    private static Validator required(String errorMessage) {
        return new RequiredStringValidator(errorMessage);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        lblName = new javax.swing.JLabel();
        nameText = new javax.swing.JTextField();
        lblAddress = new javax.swing.JLabel();
        addressText = new javax.swing.JTextField();
        lblTown = new javax.swing.JLabel();
        townText = new javax.swing.JTextField();
        lblRegion = new javax.swing.JLabel();
        regionText = new javax.swing.JTextField();
        lblCode = new javax.swing.JLabel();
        codeText = new javax.swing.JTextField();
        lblCountry = new javax.swing.JLabel();
        countryText = new javax.swing.JTextField();
        lblIBAN = new javax.swing.JLabel();
        ibanText = new javax.swing.JTextField();
        lblBank = new javax.swing.JLabel();
        bankText = new javax.swing.JTextField();
        lblVATID = new javax.swing.JLabel();
        vatidText = new javax.swing.JTextField();
        lblRegistration = new javax.swing.JLabel();
        registrationText = new javax.swing.JTextField();

        setFocusable(false);
        setMaximumSize(new java.awt.Dimension(800, 320));
        setMinimumSize(new java.awt.Dimension(720, 200));
        setPreferredSize(new java.awt.Dimension(720, 210));
        setLayout(new java.awt.GridBagLayout());

        lblName.setText("Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 8, 4, 4);
        add(lblName, gridBagConstraints);

        nameText.setMaximumSize(new java.awt.Dimension(360, 23));
        nameText.setMinimumSize(new java.awt.Dimension(120, 23));
        nameText.setPreferredSize(new java.awt.Dimension(240, 23));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dataContext, org.jdesktop.beansbinding.ELProperty.create("${model.name}"), nameText, org.jdesktop.beansbinding.BeanProperty.create("text"), "nameBinding");
        binding.setValidator(required("* contact name is required"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 8);
        add(nameText, gridBagConstraints);

        lblAddress.setText("Address:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 8, 4, 4);
        add(lblAddress, gridBagConstraints);

        addressText.setMaximumSize(new java.awt.Dimension(360, 23));
        addressText.setMinimumSize(new java.awt.Dimension(120, 23));
        addressText.setPreferredSize(new java.awt.Dimension(240, 23));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dataContext, org.jdesktop.beansbinding.ELProperty.create("${model.address.street}"), addressText, org.jdesktop.beansbinding.BeanProperty.create("text"), "streetBinding");
        binding.setValidator(required("* street address is required"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 8);
        add(addressText, gridBagConstraints);

        lblTown.setText("Town:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 8, 4, 4);
        add(lblTown, gridBagConstraints);

        townText.setMaximumSize(new java.awt.Dimension(360, 23));
        townText.setMinimumSize(new java.awt.Dimension(120, 23));
        townText.setPreferredSize(new java.awt.Dimension(240, 23));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dataContext, org.jdesktop.beansbinding.ELProperty.create("${model.address.town}"), townText, org.jdesktop.beansbinding.BeanProperty.create("text"), "townBinding");
        binding.setValidator(required("* town is required"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 8);
        add(townText, gridBagConstraints);

        lblRegion.setText("Region:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 8, 4, 4);
        add(lblRegion, gridBagConstraints);

        regionText.setMaximumSize(new java.awt.Dimension(360, 23));
        regionText.setMinimumSize(new java.awt.Dimension(120, 23));
        regionText.setPreferredSize(new java.awt.Dimension(240, 23));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dataContext, org.jdesktop.beansbinding.ELProperty.create("${model.address.region}"), regionText, org.jdesktop.beansbinding.BeanProperty.create("text"), "regionBinding");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 8);
        add(regionText, gridBagConstraints);

        lblCode.setText("Code:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 8, 4, 4);
        add(lblCode, gridBagConstraints);

        codeText.setMaximumSize(new java.awt.Dimension(360, 23));
        codeText.setMinimumSize(new java.awt.Dimension(120, 23));
        codeText.setPreferredSize(new java.awt.Dimension(240, 23));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dataContext, org.jdesktop.beansbinding.ELProperty.create("${model.address.postalCode}"), codeText, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 8);
        add(codeText, gridBagConstraints);

        lblCountry.setText("Country:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 8, 4, 4);
        add(lblCountry, gridBagConstraints);

        countryText.setMaximumSize(new java.awt.Dimension(360, 23));
        countryText.setMinimumSize(new java.awt.Dimension(120, 23));
        countryText.setPreferredSize(new java.awt.Dimension(240, 23));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dataContext, org.jdesktop.beansbinding.ELProperty.create("${model.address.country}"), countryText, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(required("* country is required"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 8);
        add(countryText, gridBagConstraints);

        lblIBAN.setText("IBAN:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 8, 4, 4);
        add(lblIBAN, gridBagConstraints);

        ibanText.setMaximumSize(new java.awt.Dimension(360, 23));
        ibanText.setMinimumSize(new java.awt.Dimension(120, 23));
        ibanText.setPreferredSize(new java.awt.Dimension(240, 23));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dataContext, org.jdesktop.beansbinding.ELProperty.create("${model.bankAccount.accountNumber}"), ibanText, org.jdesktop.beansbinding.BeanProperty.create("text"), "accountBinding");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 8);
        add(ibanText, gridBagConstraints);

        lblBank.setText("Bank:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 8, 4, 4);
        add(lblBank, gridBagConstraints);

        bankText.setMaximumSize(new java.awt.Dimension(360, 23));
        bankText.setMinimumSize(new java.awt.Dimension(120, 23));
        bankText.setPreferredSize(new java.awt.Dimension(240, 23));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dataContext, org.jdesktop.beansbinding.ELProperty.create("${model.bankAccount.bank}"), bankText, org.jdesktop.beansbinding.BeanProperty.create("text"), "bankBinding");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 8);
        add(bankText, gridBagConstraints);

        lblVATID.setText("VAT ID:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 8, 4, 4);
        add(lblVATID, gridBagConstraints);

        vatidText.setMaximumSize(new java.awt.Dimension(360, 23));
        vatidText.setMinimumSize(new java.awt.Dimension(120, 23));
        vatidText.setPreferredSize(new java.awt.Dimension(240, 23));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dataContext, org.jdesktop.beansbinding.ELProperty.create("${model.legalInformation.fiscalId}"), vatidText, org.jdesktop.beansbinding.BeanProperty.create("text"), "vatidBinding");
        binding.setValidator(required("* PIN, VAT ID or any other tax ID is required"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 8);
        add(vatidText, gridBagConstraints);

        lblRegistration.setText("Registration:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 8, 4, 4);
        add(lblRegistration, gridBagConstraints);

        registrationText.setMaximumSize(new java.awt.Dimension(360, 23));
        registrationText.setMinimumSize(new java.awt.Dimension(120, 23));
        registrationText.setPreferredSize(new java.awt.Dimension(240, 23));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dataContext, org.jdesktop.beansbinding.ELProperty.create("${model.legalInformation.registration}"), registrationText, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(required("* registration number is required")
        );
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 8);
        add(registrationText, gridBagConstraints);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addressText;
    private javax.swing.JTextField bankText;
    private javax.swing.JTextField codeText;
    private javax.swing.JTextField countryText;
    private final ro.samlex.reelcash.viewmodels.SimpleViewModel dataContext = new ro.samlex.reelcash.viewmodels.SimpleViewModel();
    private javax.swing.JTextField ibanText;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblBank;
    private javax.swing.JLabel lblCode;
    private javax.swing.JLabel lblCountry;
    private javax.swing.JLabel lblIBAN;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblRegion;
    private javax.swing.JLabel lblRegistration;
    private javax.swing.JLabel lblTown;
    private javax.swing.JLabel lblVATID;
    private javax.swing.JTextField nameText;
    private javax.swing.JTextField regionText;
    private javax.swing.JTextField registrationText;
    private javax.swing.JTextField townText;
    private javax.swing.JTextField vatidText;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
