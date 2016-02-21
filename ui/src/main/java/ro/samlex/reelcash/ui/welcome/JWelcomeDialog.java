package ro.samlex.reelcash.ui.welcome;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import ro.samlex.reelcash.Application;
import ro.samlex.reelcash.Reelcash;
import ro.samlex.reelcash.SysUtils;
import ro.samlex.reelcash.io.FileOutputSink;
import ro.samlex.reelcash.ui.ApplicationMessages;

public class JWelcomeDialog extends javax.swing.JDialog {

    public JWelcomeDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contactPanel = new ro.samlex.reelcash.ui.components.JContactPanel();
        contactPanel.getDataContext().setModel(new ro.samlex.reelcash.data.Party());
        actionPanel = new javax.swing.JPanel();
        saveButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Company Information");
        setAutoRequestFocus(false);
        setMaximumSize(new java.awt.Dimension(800, 480));
        setMinimumSize(new java.awt.Dimension(720, 280));
        setName("welcomedialog"); // NOI18N
        setPreferredSize(new java.awt.Dimension(720, 320));
        setResizable(false);

        contactPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(24, 8, 16, 8), "Your Company's Contact Information", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 24))); // NOI18N
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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        if (evt.getSource() == this.exitButton) {
            System.exit(0);
        }
    }//GEN-LAST:event_exitButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        if (evt.getSource() != this.saveButton || contactPanel.getDataContext() == null) {
            return;
        }
        contactPanel.forceValidation();
        if(contactPanel.getValidationErrorCollector().hasErrors()) 
        {
            ApplicationMessages.showError(this, contactPanel.getValidationErrorCollector().getErrorString());
            return;
        }
        Path companyDataPath = FileSystems.getDefault().getPath(
                SysUtils.getDbFolderPath().toString(), Reelcash.COMPANY_DATA_FILE_NAME);
        try {
            contactPanel.getDataContext().save(new FileOutputSink(companyDataPath));
            Application.getInstance().setCompany(contactPanel.getDataContext().getModel());
            this.setVisible(false);
            Application.showMainFrame();
        } catch (IOException ex) {
            ApplicationMessages.showError(this, "Error when saving company information: " + ex.getMessage());
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPanel;
    private ro.samlex.reelcash.ui.components.JContactPanel contactPanel;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
