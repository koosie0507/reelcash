package ro.samlex.reelcash.ui.welcome;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import ro.samlex.reelcash.Application;
import ro.samlex.reelcash.Reelcash;
import ro.samlex.reelcash.SysUtils;
import ro.samlex.reelcash.io.FileOutputSink;
import ro.samlex.reelcash.ui.ApplicationMessages;
import ro.samlex.reelcash.viewmodels.CompanyInformationViewModel;

public class JWelcomeDialog extends javax.swing.JDialog {

    public JWelcomeDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    @Getter
    public CompanyInformationViewModel getDataContext() {
        return this.dataContext;
    }

    @Setter
    public void setDataContext(CompanyInformationViewModel dataContext) {
        Object oldValue = this.dataContext;
        this.dataContext = dataContext;
        firePropertyChange("dataContext", oldValue, this.dataContext);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        dataContext = new ro.samlex.reelcash.viewmodels.CompanyInformationViewModel();
        contactPanel = new ro.samlex.reelcash.ui.components.JContactPanel();
        actionPanel = new javax.swing.JPanel();
        saveButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Company Information");
        setMaximumSize(new java.awt.Dimension(800, 480));
        setMinimumSize(new java.awt.Dimension(720, 280));
        setModal(true);
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setModalityType(java.awt.Dialog.ModalityType.TOOLKIT_MODAL);
        setName("welcomedialog"); // NOI18N
        setPreferredSize(new java.awt.Dimension(720, 320));
        setType(java.awt.Window.Type.POPUP);

        contactPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(24, 8, 16, 8), "Your Company's Contact Information", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 24))); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dataContext, org.jdesktop.beansbinding.ELProperty.create("${model}"), contactPanel, org.jdesktop.beansbinding.BeanProperty.create("model"), "contactPanelModelBinding");
        bindingGroup.addBinding(binding);

        getContentPane().add(contactPanel, java.awt.BorderLayout.CENTER);

        saveButton.setMnemonic('d');
        saveButton.setText("Done");
        saveButton.setToolTipText("");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        actionPanel.add(saveButton);

        exitButton.setMnemonic('x');
        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
        actionPanel.add(exitButton);

        getContentPane().add(actionPanel, java.awt.BorderLayout.PAGE_END);

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        if (this.dataContext == null) {
            return;
        }
        Path companyDataPath = FileSystems.getDefault().getPath(
                SysUtils.getDbFolderPath(), Reelcash.COMPANY_DATA_FILE_NAME);
        try {
            this.dataContext.getModel().setAddress(contactPanel.getModel().getAddress());
            this.dataContext.getModel().setName(contactPanel.getModel().getName());
            this.dataContext.save(new FileOutputSink(companyDataPath));
            this.setVisible(false);
            Application.showMainFrame();
        } catch (IOException ex) {
            ApplicationMessages.showError(this, "Error when saving company information");
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPanel;
    private ro.samlex.reelcash.ui.components.JContactPanel contactPanel;
    private ro.samlex.reelcash.viewmodels.CompanyInformationViewModel dataContext;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton saveButton;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
