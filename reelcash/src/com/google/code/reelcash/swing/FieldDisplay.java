package com.google.code.reelcash.swing;

import com.google.code.reelcash.ReelcashException;
import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.layout.fields.*;
import com.google.code.reelcash.model.DataRowComboModel;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import javax.swing.ComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
public abstract class FieldDisplay {

    private String key;
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

    protected FieldDisplay(Field field) {
        key = field.getName();
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
    public static FieldDisplay newInstance(Field f) {
        if (f instanceof BigDecimalField)
            return new BigDecimalFieldInfo((BigDecimalField) f);

        if (f instanceof StringField)
            return new StringFieldDisplay((StringField) f);

        if (f instanceof BooleanField)
            return new BooleanFieldDisplay((BooleanField) f);

        if (f instanceof DoubleField)
            return new DoubleFieldDisplay((DoubleField) f);

        if (f instanceof DateField)
            return new DateFieldDisplay((DateField) f);

        if (f instanceof IntegerField)
            return new IntegerFieldDisplay((IntegerField) f);

        if (f instanceof ReferenceField)
            return new ReferenceFieldDisplay((ReferenceField) f);


        throw new ReelcashException();
    }

    /**
     * Clears any data that's been entered in the component.
     */
    public abstract void clearData();

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

    /**
     * Returns the name of the field which should be displayed using this field display.
     * 
     * @return the name of the field which should be displayed.
     */
    public String getKey() {
        return key;
    }

    public Dimension getMaximumSize() {
        return maximumSize;
    }

    public Dimension getMinimumSize() {
        return minimumSize;
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

    public abstract Object getValue();

    public boolean isVisible() {
        return visible;
    }

    public void setCaption(String caption) {
        this.caption = caption;
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

    public abstract void setValue(Object value);

    public void setVertContentAlign(int vertContentAlign) {
        this.vertContentAlign = vertContentAlign;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}

class BigDecimalFieldInfo extends FieldDisplay {

    private BigDecimalField field;
    private JFormattedTextField displayComponent;

    BigDecimalFieldInfo(BigDecimalField field) {
        super(field);
        this.field = field;
    }

    @Override
    public void clearData() {
        ((JFormattedTextField) getDisplayComponent()).setValue(0);
    }

    @Override
    public JComponent getDisplayComponent() {
        if (null == displayComponent) {
            NumberFormat format = NumberFormat.getInstance();
            format.setMaximumFractionDigits(field.getPrecision());
            format.setMaximumIntegerDigits(field.getScale() - field.getPrecision());

            displayComponent = new JFormattedTextField(format);
            displayComponent.setName(field.getName());
            displayComponent.setMinimumSize(getMinimumSize());
            displayComponent.setMaximumSize(getMaximumSize());
            displayComponent.setPreferredSize(getPreferredSize());
            displayComponent.setVisible(isVisible());
            displayComponent.setEditable(!isReadOnly());
            displayComponent.setValue(0);
        }
        return displayComponent;

    }

    @Override
    public Object getValue() {
        return new java.math.BigDecimal(((JFormattedTextField) getDisplayComponent()).getText());
    }

    @Override
    public void setValue(Object value) {
        if (!(value instanceof BigDecimal))
            return;

        BigDecimal decimal = (BigDecimal) value;
        ((JFormattedTextField) getDisplayComponent()).setValue(decimal);
    }
}

class StringFieldDisplay extends FieldDisplay {

    private StringField field;
    private JTextField displayComponent;

    StringFieldDisplay(StringField field) {
        super(field);
        this.field = field;
    }

    @Override
    public void clearData() {
        ((JTextField) getDisplayComponent()).setText("");
    }

    @Override
    public JComponent getDisplayComponent() {
        if (null == displayComponent) {
            displayComponent = new JTextField();
            displayComponent.setName(field.getName());
            int preferredWidth = field.getMaxSize() * displayComponent.getFontMetrics(displayComponent.getFont()).getMaxAdvance();
            setPreferredSize(new Dimension(preferredWidth, DEFAULT_HEIGHT));

            displayComponent.setMinimumSize(getMinimumSize());
            displayComponent.setMaximumSize(getMaximumSize());
            displayComponent.setPreferredSize(getPreferredSize());
            displayComponent.setVisible(isVisible());
            displayComponent.setEditable(!isReadOnly());
            displayComponent.setText("");
        }
        return displayComponent;
    }

    @Override
    public Object getValue() {
        return ((JTextField) getDisplayComponent()).getText();
    }

    @Override
    public void setValue(Object value) {
        if (!(value instanceof String))
            return;

        String str = (String) value;
        ((JTextField) getDisplayComponent()).setText(str);
    }
}

class IntegerFieldDisplay extends FieldDisplay {

    private IntegerField field;
    private JSpinner displayComponent;

    IntegerFieldDisplay(IntegerField field) {
        super(field);
        this.field = field;
    }

    @Override
    public void clearData() {
        ((JSpinner) getDisplayComponent()).setValue(0);
    }

    @Override
    public JComponent getDisplayComponent() {
        if (null == displayComponent) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);

            displayComponent = new JSpinner(model);

            displayComponent.setName(field.getName());
            displayComponent.setMinimumSize(getMinimumSize());
            displayComponent.setMaximumSize(getMaximumSize());
            displayComponent.setPreferredSize(getPreferredSize());
            displayComponent.setVisible(isVisible());
            displayComponent.setEnabled(!isReadOnly());
        }
        return displayComponent;
    }

    @Override
    public Object getValue() {
        return new Integer(((Number) ((JSpinner) getDisplayComponent()).getValue()).intValue());
    }

    @Override
    public void setValue(Object value) {
        if (!(value instanceof Integer))
            return;

        Integer i = (Integer) value;
        ((JSpinner) getDisplayComponent()).setValue(i);
    }
}

class DoubleFieldDisplay extends FieldDisplay {

    private DoubleField field;
    private JFormattedTextField displayComponent;

    DoubleFieldDisplay(DoubleField field) {
        super(field);
        this.field = field;
    }

    @Override
    public void clearData() {
        ((JFormattedTextField) getDisplayComponent()).setValue(0.0);
    }

    @Override
    public JComponent getDisplayComponent() {
        if (null == displayComponent) {
            NumberFormat format = NumberFormat.getInstance();
            format.setMaximumFractionDigits(DEFAULT_DOUBLE_DECIMALS);
            format.setMaximumIntegerDigits(DEFAULT_DOUBLE_SCALE);

            displayComponent = new JFormattedTextField(format);
            displayComponent.setName(field.getName());
            displayComponent.setMinimumSize(getMinimumSize());
            displayComponent.setMaximumSize(getMaximumSize());
            displayComponent.setPreferredSize(getPreferredSize());
            displayComponent.setVisible(isVisible());
            displayComponent.setEditable(!isReadOnly());
            displayComponent.setValue(0);
        }

        return displayComponent;
    }

    @Override
    public Object getValue() {
        return Double.valueOf(((JFormattedTextField) getDisplayComponent()).getValue().toString());
    }

    @Override
    public void setValue(Object value) {
        if (!(value instanceof Double))
            return;

        Double i = (Double) value;
        ((JFormattedTextField) getDisplayComponent()).setValue(i);
    }
}

class DateFieldDisplay extends FieldDisplay {

    private DateField field;
    private JSpinner displayComponent;

    DateFieldDisplay(DateField field) {
        super(field);
        this.field = field;
    }

    @Override
    public JComponent getDisplayComponent() {
        if (null == displayComponent) {
            SpinnerDateModel dateModel = new SpinnerDateModel();
            displayComponent = new JSpinner(dateModel);
            displayComponent.setName(field.getName());
            displayComponent.setMinimumSize(getMinimumSize());
            displayComponent.setMaximumSize(getMaximumSize());
            displayComponent.setPreferredSize(getPreferredSize());
            displayComponent.setVisible(isVisible());
            displayComponent.setEnabled(!isReadOnly());
        }
        return displayComponent;
    }

    @Override
    public void clearData() {
        ((JSpinner) getDisplayComponent()).setValue(new java.util.Date(Calendar.getInstance().getTimeInMillis()));
    }

    @Override
    public Object getValue() {
        return (java.util.Date) ((JSpinner) getDisplayComponent()).getValue();
    }

    @Override
    public void setValue(Object value) {
        if (!(value instanceof java.sql.Date))
            return;

        java.sql.Date i = (java.sql.Date) value;
        ((JSpinner) getDisplayComponent()).setValue(new java.util.Date(i.getTime()));
    }
}

class BooleanFieldDisplay extends FieldDisplay implements ItemListener {

    private BooleanField field;
    private JCheckBox displayComponent;

    BooleanFieldDisplay(BooleanField field) {
        super(field);
        this.field = field;
    }

    @Override
    public JComponent getDisplayComponent() {
        if (null == displayComponent) {
            displayComponent = new JCheckBox(Resources.getString("unchecked_text"), false);

            displayComponent.setName(field.getName());
            displayComponent.setMinimumSize(getMinimumSize());
            displayComponent.setMaximumSize(getMaximumSize());
            displayComponent.setPreferredSize(getPreferredSize());
            displayComponent.setVisible(isVisible());
            displayComponent.setEnabled(!isReadOnly());
            displayComponent.addItemListener(this);
        }
        return displayComponent;
    }

    @Override
    public void clearData() {
        boolean b = ((Boolean) field.getDefaultValue()).booleanValue();
        ((JCheckBox) getDisplayComponent()).setSelected(b);
    }

    public void itemStateChanged(ItemEvent e) {
        JCheckBox component = (JCheckBox) e.getSource();
        component.setText(component.isSelected() ? Resources.getString("checked_text") : Resources.getString("unchecked_text"));
    }

    @Override
    public Object getValue() {
        return new Boolean(((JCheckBox) getDisplayComponent()).isSelected());
    }

    @Override
    public void setValue(Object value) {
        if (!(value instanceof Boolean))
            return;

        ((JCheckBox) getDisplayComponent()).setSelected(((Boolean) value).booleanValue());
    }
}

class ReferenceFieldDisplay extends FieldDisplay {

    private ReferenceField field;
    private JComboBox displayComponent;

    ReferenceFieldDisplay(ReferenceField field) {
        super(field);
        this.field = field;
    }

    @Override
    public void clearData() {
        ((JComboBox) getDisplayComponent()).setSelectedIndex(-1);
    }

    @Override
    public JComponent getDisplayComponent() {
        if (null == displayComponent) {
            displayComponent = new JComboBox();
            displayComponent.setName(field.getName());
            displayComponent.setMinimumSize(getMinimumSize());
            displayComponent.setMaximumSize(getMaximumSize());
            displayComponent.setPreferredSize(getPreferredSize());
            displayComponent.setVisible(isVisible());
            displayComponent.setEnabled(!isReadOnly());
            //new AutoCompleteJComboBoxer(displayComponent);
        }
        return displayComponent;
    }

    @Override
    public Object getValue() {
        Object value = ((JComboBox) getDisplayComponent()).getSelectedItem();
        if (value instanceof DataRow
                && ((JComboBox) getDisplayComponent()).getModel() instanceof DataRowComboModel)
            value = ((DataRow) value).getValue(
                    ((DataRowComboModel) ((JComboBox) getDisplayComponent()).getModel()).getValueMemberIndex());
        return value;
    }

    @Override
    public void setValue(Object value) {
        ComboBoxModel model = ((JComboBox) getDisplayComponent()).getModel();
        if (null != value && model instanceof DataRowComboModel) {
            DataRow row = null;
            for (int i = model.getSize() - 1; i > -1; i--) {
                if (((DataRowComboModel) model).getElementValueAt(i).equals(value))
                    row = (DataRow) model.getElementAt(i);
            }
            ((JComboBox) getDisplayComponent()).setSelectedItem(row);
        }
        else
            ((JComboBox) getDisplayComponent()).setSelectedItem(value);
    }
}
