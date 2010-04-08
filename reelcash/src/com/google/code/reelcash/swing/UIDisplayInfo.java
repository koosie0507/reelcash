package com.google.code.reelcash.swing;

import com.google.code.reelcash.ReelcashException;
import com.google.code.reelcash.data.layout.fields.*;
import java.awt.Dimension;
import java.text.NumberFormat;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;

/**
 * Provides methods and properties which are useful for creating user interface
 * components. This class is abstract and should be implemented for each new
 * {@link com.google.code.reelcash.data.layout.fields.Field} type which is implemented.
 *
 * @author andrei.olar 
 */
public abstract class UIDisplayInfo {

    private String caption;
    private int horzContentAlign;
    private int vertContentAlign;
    private Dimension minimumSize;
    private Dimension preferredSize;
    private Dimension maximumSize;
    private boolean visible;
    private boolean readOnly;
    /**
     * Specifies the default width of the components generated with this toolkit.
     */
    public static final int DEFAULT_WIDTH = 100;
    /**
     * Specifies the default height of the components generated with this toolkit.
     */
    public static final int DEFAULT_HEIGHT = 22;
    /**
     * Default number of decimals of a double precision value.
     */
    public static final int DEFAULT_DOUBLE_DECIMALS = 4;
    /**
     * Default number of integer part decimals of a double precision value.
     */
    public static final int DEFAULT_DOUBLE_SCALE = 10;

    protected UIDisplayInfo(Field field) {
        caption = field.getName();
        horzContentAlign = JTextField.LEFT;
        vertContentAlign = JTextField.TOP;
        minimumSize = new Dimension(0, 0);
        maximumSize = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
        preferredSize = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        visible = true;
        readOnly = false;
    }

    /**
     * Returns a display information instance for the specified field. 
     * 
     * @param f the field to create the display info for.
     * @return a display info instance. 
     */
    public static UIDisplayInfo newInstance(Field f) {
        if (f instanceof BigDecimalField)
            return new UIBigDecimalDisplayInfo((BigDecimalField) f);

        if (f instanceof StringField)
            return new UIStringDisplayInfo((StringField) f);

        if (f instanceof BooleanField)
            return new UIBooleanDisplayInfo((BooleanField) f);

        if (f instanceof DoubleField)
            return new UIDoubleDisplayInfo((DoubleField) f);

        if (f instanceof DateField)
            return new UIDateDisplayInfo((DateField) f);

        if (f instanceof IntegerField)
            return new UIIntegerDisplayInfo((IntegerField) f);


        throw new ReelcashException();
    }

    /**
     * Creates a new label with the information supplied by the current instance.
     *
     * @return a display label.
     */
    public JLabel createDescriptionLabel() {
        JLabel result = new JLabel(getCaption());
        result.setLabelFor(getDisplayComponent());
        return result;
    }

    /**
     * Returns the caption or "display text" of the display info
     * @return
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Returns a display component which should be properly initialized.
     *
     * @return a {@link javax.swing.JComponent} instance.
     */
    public abstract JComponent getDisplayComponent();

    public int getHorzContentAlign() {
        return horzContentAlign;
    }

    public Dimension getMaximumSize() {
        return maximumSize;
    }

    public Dimension getMinimumSize() {
        return minimumSize;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Dimension getPreferredSize() {
        return preferredSize;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public int getVertContentAlign() {
        return vertContentAlign;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setHorzContentAlign(int horzContentAlign) {
        this.horzContentAlign = horzContentAlign;
    }

    public void setMaximumSize(Dimension maximumSize) {
        this.maximumSize = maximumSize;
    }

    public void setMinimumSize(Dimension minimumSize) {
        this.minimumSize = minimumSize;
    }

    public void setPreferredSize(Dimension preferredSize) {
        this.preferredSize = preferredSize;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public void setVertContentAlign(int vertContentAlign) {
        this.vertContentAlign = vertContentAlign;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}

class UIBigDecimalDisplayInfo extends UIDisplayInfo {

    private BigDecimalField field;

    UIBigDecimalDisplayInfo(BigDecimalField field) {
        super(field);
        this.field = field;
    }

    @Override
    public JComponent getDisplayComponent() {
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(field.getPrecision());
        format.setMaximumIntegerDigits(field.getScale() - field.getPrecision());

        JFormattedTextField result = new JFormattedTextField(format);
        result.setMinimumSize(getMinimumSize());
        result.setMaximumSize(getMaximumSize());
        result.setPreferredSize(getPreferredSize());
        result.setVisible(isVisible());
        result.setEditable(!isReadOnly());
        result.setValue(0);

        return result;
    }
}

class UIStringDisplayInfo extends UIDisplayInfo {

    private StringField field;

    UIStringDisplayInfo(StringField field) {
        super(field);
        this.field = field;
    }

    @Override
    public JComponent getDisplayComponent() {
        JTextField result = new JTextField();

        int preferredWidth = field.getMaxSize() * result.getFontMetrics(result.getFont()).getMaxAdvance();
        setPreferredSize(new Dimension(preferredWidth, DEFAULT_HEIGHT));

        result.setMinimumSize(getMinimumSize());
        result.setMaximumSize(getMaximumSize());
        result.setPreferredSize(getPreferredSize());
        result.setVisible(isVisible());
        result.setEditable(!isReadOnly());
        result.setText("");

        return result;
    }
}

class UIIntegerDisplayInfo extends UIDisplayInfo {

    private IntegerField field;

    UIIntegerDisplayInfo(IntegerField field) {
        super(field);
        this.field = field;
    }

    @Override
    public JComponent getDisplayComponent() {
        SpinnerNumberModel model = new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);

        JSpinner result = new JSpinner(model);

        result.setMinimumSize(getMinimumSize());
        result.setMaximumSize(getMaximumSize());
        result.setPreferredSize(getPreferredSize());
        result.setVisible(isVisible());
        result.setEnabled(!isReadOnly());

        return result;
    }
}

class UIDoubleDisplayInfo extends UIDisplayInfo {

    private DoubleField field;

    UIDoubleDisplayInfo(DoubleField field) {
        super(field);
        this.field = field;
    }

    @Override
    public JComponent getDisplayComponent() {
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(DEFAULT_DOUBLE_DECIMALS);
        format.setMaximumIntegerDigits(DEFAULT_DOUBLE_SCALE);

        JFormattedTextField result = new JFormattedTextField(format);
        result.setMinimumSize(getMinimumSize());
        result.setMaximumSize(getMaximumSize());
        result.setPreferredSize(getPreferredSize());
        result.setVisible(isVisible());
        result.setEditable(!isReadOnly());
        result.setValue(0);

        return result;
    }
}

class UIDateDisplayInfo extends UIDisplayInfo {

    private DateField field;

    UIDateDisplayInfo(DateField field) {
        super(field);
        this.field = field;
    }

    @Override
    public JComponent getDisplayComponent() {
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner result = new JSpinner(dateModel);

        result.setMinimumSize(getMinimumSize());
        result.setMaximumSize(getMaximumSize());
        result.setPreferredSize(getPreferredSize());
        result.setVisible(isVisible());
        result.setEnabled(!isReadOnly());

        return result;
    }
}

class UIBooleanDisplayInfo extends UIDisplayInfo {

    private BooleanField field;

    UIBooleanDisplayInfo(BooleanField field) {
        super(field);
        this.field = field;
    }

    @Override
    public JComponent getDisplayComponent() {
        JCheckBox result = new JCheckBox(Resources.getString("unchecked_text"), false);

        result.setMinimumSize(getMinimumSize());
        result.setMaximumSize(getMaximumSize());
        result.setPreferredSize(getPreferredSize());
        result.setVisible(isVisible());
        result.setEnabled(!isReadOnly());

        return result;
    }
}
