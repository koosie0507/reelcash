package ro.samlex.reelcash.ui.renderers.list;

import java.awt.Color;
import java.awt.Component;
import java.net.URL;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
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
        add(invoiceNumberLabel);
        add(createRightIconLabel());
        add(recipientLabel);
    }
    
    private void setLabelText(JLabel label, Color color, String text) {
        label.setForeground(color);
        label.setText(text);
    }
    
    private Color getForeground(JList list, boolean selected) {
        return selected?list.getSelectionForeground():list.getForeground();
    }
    
    private Color getBackground(JList list, boolean selected) {
        return selected?list.getSelectionBackground():list.getBackground();
    }

    private JLabel createRightIconLabel() {
        JLabel label = new JLabel();
        Border emptyBorder = new EmptyBorder(2, 4, 2, 4);
        label.setBorder(emptyBorder);
        final URL imageIconURL = getClass().getResource("right.png");
        if(imageIconURL != null) {
            label.setIcon(new ImageIcon(imageIconURL));
            label.setText(null);
        } else {
            label.setText("->");
        }
        
        label.setAlignmentX(0.5f);
        label.setAlignmentY(0.5f);
        return label;
    }

    private Component createRenderer(JList list, Invoice invoice, boolean selected) {
        Color background = getBackground(list, selected);
        Color foreground = getForeground(list, selected);
        setLabelText(invoiceNumberLabel, foreground, String.format("%d / %tF", invoice.getNumber(), invoice.getDate()));
        setLabelText(recipientLabel, foreground, invoice.getRecipient().getName());
        setBackground(background);
        setForeground(foreground);
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
