package ro.samlex.reelcash.ui.renderers.list;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import ro.samlex.reelcash.data.Invoice;

public class InvoiceRenderer extends JPanel implements ListCellRenderer {

    private final JLabel invoiceNumberLabel;
    private final JLabel recipientLabel;

    public InvoiceRenderer() {
        invoiceNumberLabel = new JLabel();
        recipientLabel = new JLabel();
        initializeLayout();
    }

    private void initializeLayout() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(createInvoiceIconLabel());
        add(invoiceNumberLabel);
        add(createRightIconLabel());
        add(recipientLabel);
    }

    private void setLabelText(JLabel label, Color color, String text) {
        label.setForeground(color);
        label.setText(text);
    }

    private Color getForeground(JList list, boolean selected) {
        return selected ? list.getSelectionForeground() : list.getForeground();
    }

    private Color getBackground(JList list, boolean selected) {
        return selected ? list.getSelectionBackground() : list.getBackground();
    }
    
    private JLabel createInvoiceIconLabel() {
        JLabel label = new JLabel();
        Border emptyBorder = new EmptyBorder(2, 4, 2, 16);
        label.setBorder(emptyBorder);
        final URL imageIconURL = getClass().getResource("/invoice.png");
        BufferedImage img;
        try {
            img = ImageIO.read(imageIconURL);
            label.setIcon(new ImageIcon(img.getScaledInstance(24, 24, Image.SCALE_FAST)));
            label.setText(null);
        } catch (NullPointerException | IOException e) {
            label.setText("[]");
        }

        label.setAlignmentX(0.5f);
        label.setAlignmentY(0.5f);
        return label;        
    }
    
    private JLabel createRightIconLabel() {
        JLabel label = new JLabel();
        Border emptyBorder = new EmptyBorder(2, 8, 2, 8);
        label.setBorder(emptyBorder);
        final URL imageIconURL = getClass().getResource("/right.png");
        BufferedImage img;
        try {
            img = ImageIO.read(imageIconURL);
            label.setIcon(new ImageIcon(img.getScaledInstance(16, 16, Image.SCALE_FAST)));
            label.setText(null);
        } catch (NullPointerException | IOException e) {
            label.setText("->");
        }

        label.setAlignmentX(0.5f);
        label.setAlignmentY(0.46f);
        return label;
    }
    
    private void setBorder(boolean selected) {
        setBorder(selected
                ?new EtchedBorder(EtchedBorder.LOWERED)
                :new EmptyBorder(4,4,4,2));
    }

    private Component createRenderer(JList list, Invoice invoice, boolean selected) {
        Color background = getBackground(list, selected);
        Color foreground = getForeground(list, selected);
        setLabelText(invoiceNumberLabel, foreground, String.format("no. %d / %tF", invoice.getNumber(), invoice.getDate()));
        setLabelText(recipientLabel, foreground, invoice.getRecipient().getName());
        setBackground(background);
        setForeground(foreground);
        setBorder(selected);
        
        return this;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value instanceof Invoice) {
            return createRenderer(list, (Invoice) value, isSelected);
        }
        return this;
    }
}
