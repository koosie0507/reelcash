package ro.samlex.reelcash.ui;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MainWindow extends javax.swing.JFrame {
    private final Image appIcon;
    public MainWindow() throws IOException {
        appIcon = ImageIO.read(getClass().getResource("/generic_money_box.png"));
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        invoiceListPanel = new ro.samlex.reelcash.ui.components.JInvoiceListPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reelcash");
        setIconImage(this.appIcon);
        getContentPane().setLayout(new java.awt.CardLayout());

        invoiceListPanel.setMinimumSize(new java.awt.Dimension(640, 480));
        invoiceListPanel.setPreferredSize(new java.awt.Dimension(720, 560));
        getContentPane().add(invoiceListPanel, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ro.samlex.reelcash.ui.components.JInvoiceListPanel invoiceListPanel;
    // End of variables declaration//GEN-END:variables
}
