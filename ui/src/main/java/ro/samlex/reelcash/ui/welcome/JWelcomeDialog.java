/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.samlex.reelcash.ui.welcome;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import ro.samlex.reelcash.Application;
import ro.samlex.reelcash.data.Party;
import ro.samlex.reelcash.io.CompanyDataStreamFactory;
import ro.samlex.reelcash.ui.ApplicationMessages;

/**
 *
 * @author cusi
 */
public class JWelcomeDialog extends javax.swing.JDialog {

    /**
     * Creates new form JWelcomeDialog
     */
    public JWelcomeDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        try (OutputStream os = new CompanyDataStreamFactory().createOutputStream()) {
            Party p = contactPanel.createParty();
            p.save(os);
            this.setVisible(false);
            Application.showMainFrame();
        }
        catch(NullPointerException e) {
            ApplicationMessages.showError(this, "Some mandatory information is missing");
        }
        catch(IllegalArgumentException e) {
            ApplicationMessages.showError(this, "Some of the entered information is invalid");
        }
        catch (IOException e) {
            ApplicationMessages.showError(this, e.getMessage());
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPanel;
    private ro.samlex.reelcash.ui.components.JContactPanel contactPanel;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
