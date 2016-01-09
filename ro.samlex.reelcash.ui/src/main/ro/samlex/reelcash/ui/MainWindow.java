package ro.samlex.reelcash.ui;

import ro.samlex.reelcash.Reelcash;
import ro.samlex.reelcash.data.Party;
import ro.samlex.reelcash.data.StreetAddress;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class MainWindow {
    public static final String COMPANY_DATA_CARD = "EnterCompanyData";
    private JPanel contentPane;
    private JButton btnAddCompanyData;
    private JPanel pnlFirstTimeWelcome;
    private JPanel pnlEnterCompanyData;
    private JTextField txtName;
    private JTextField txtAddress;
    private JTextField txtSettlementName;
    private JTextField txtCountry;
    private JTextField txtRegion;
    private JTextField txtPostalCode;
    private JTextField txtBankAccount;
    private JTextField txtBankName;
    private JTextField txtCommercialNumber;
    private JTextField txtFiscalNumber;
    private JButton btnSaveCompanyInformation;
    private JButton exitButton;
    private JFrame applicationFrame;

    public MainWindow() {
        initializeFrame();
        ButtonActionListener buttonListener = new ButtonActionListener();
        btnAddCompanyData.addActionListener(buttonListener);
        exitButton.addActionListener(buttonListener);
        btnSaveCompanyInformation.addActionListener(buttonListener);
    }

    private void initializeFrame() {
        JFrame frame = new JFrame(Reelcash.APPLICATION_FRIENDLY_NAME);
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        applicationFrame = frame;
    }

    public void show() {
        applicationFrame.pack();
        applicationFrame.setLocationRelativeTo(null);
        applicationFrame.setVisible(true);
    }

    private class ButtonActionListener implements ActionListener {
        private void switchToEnterDataPane() {
            CardLayout layout = (CardLayout) contentPane.getLayout();
            layout.show(contentPane, COMPANY_DATA_CARD);
        }

        private void exitApplication() {
            applicationFrame.dispatchEvent(new WindowEvent(applicationFrame, WindowEvent.WINDOW_CLOSING));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == btnAddCompanyData) {
                switchToEnterDataPane();
            } else if (source == btnSaveCompanyInformation) {
                saveCompanyInformation();
            } else {
                exitApplication();
            }
        }

        private void saveCompanyInformation() {
            try {
                Party saved = new Party(txtName.getText())
                        .addAddress(
                                new StreetAddress(
                                        txtAddress.getText(),
                                        txtSettlementName.getText(),
                                        txtRegion.getText(),
                                        txtPostalCode.getText(),
                                        txtCountry.getText()));
                saved.setBankingInformation(txtBankName.getText(), txtBankAccount.getText());
                saved.setLegalInformation(txtFiscalNumber.getText(), txtCommercialNumber.getText());
                JOptionPane.showMessageDialog(
                        applicationFrame,
                        saved.getAddresses().iterator().next().toString(),
                        Reelcash.APPLICATION_FRIENDLY_NAME,
                        JOptionPane.INFORMATION_MESSAGE);
            }
            catch(IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        applicationFrame,
                        ex,
                        Reelcash.APPLICATION_FRIENDLY_NAME + " has encountered an error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.show();
    }
}
