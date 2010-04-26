package com.google.code.reelcash.data.layout.fields;

import com.google.code.reelcash.data.KeyRole;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Implements the @see com.google.code.reelcash.models.fields.Field abstract class. Values
 * for this field should be BigDecimal types.
 *
 * @author andrei.olar
 */
public class BigDecimalField extends Field {

    private static final long serialVersionUID = -1367261005920942956L;
    private int scale;
    private int precision;

    /**
     * Creates a new field of BigDecimal.
     * @param name the name of the new field.
     * @param role the role of the new field.
     * @param mandatory whether the field is mandatory or not.
     * @param scale decimal scale
     * @param precision decimal precision
     */
    public BigDecimalField(String name, KeyRole role, boolean mandatory, int precision, int scale) {
        super(name, role, BigDecimal.class, mandatory);
        this.precision = precision;
        this.scale = scale;
    }

    /**
     * Creates a new field of BigDecimal.
     * @param name the name of the new field.
     * @param role the role of the new field.
     * @param mandatory whether the field is mandatory or not.
     */
    public BigDecimalField(String name, KeyRole role, boolean mandatory) {
        this(name, role, mandatory, 10, 2);
    }

    /**
     * Creates a new field of BigDecimal.
     * @param name the name of the new field.
     * @param role the role of the new field.
     */
    public BigDecimalField(String name, KeyRole role) {
        this(name, role, true, 10, 2);
    }

    /**
     * Creates a new field of BigDecimal.
     * @param name the name of the new field.
     */
    public BigDecimalField(String name) {
        this(name, KeyRole.NONE, true, 10, 2);
    }

    @Override
    public Object getDefaultValue() {
        if (isMandatory())
            return new BigDecimal(0);
        else
            return null;
    }

    /**
     * Gets the precision of the decimals.
     * @return the precision.
     */
    public int getPrecision() {
        return precision;
    }

    /**
     * Gets the scale of the decimals.
     * @return the scale.
     */
    public int getScale() {
        return scale;
    }

    @Override
    public Object getValidValue(Object actualValue) {
        if (null == actualValue)
            return actualValue;

        BigDecimal actualDecimal;
        if (actualValue instanceof Number)
            actualDecimal = new BigDecimal(((Number) actualValue).doubleValue());
        else
            actualDecimal = ((BigDecimal) actualValue);
        return actualDecimal.setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * Sets the precision of the decimals. (the precision represents the total number of digits in the decimal)
     * @param precision the precision.
     */
    public void setPrecision(int precision) {
        this.precision = precision;
    }

    /**
     * Sets the scale of the decimals. (the scale represents the number of digits on the right of the decimal point)
     * @param scale the scale.
     */
    public void setScale(int scale) {
        this.scale = scale;
    }

    /**
     * This method should be called each time a value needs validation before performing operations
     * on it. The value is validated with respect to the rules specified by the current field
     * instance.
     *
     * @param value the value which should be validated with respect to the current field instance's
     * rules.
     * @return true, if the value is valid.
     */
    @Override
    public boolean validateValue(Object value) {
        // check whether the field is mandatory and the value is null
        if (null == value && (isMandatory() || KeyRole.PRIMARY.equals(getKeyRole())))
            return false;

        // check whether the value is assignable to the given type
        if (null != value)
            if (!(value instanceof Number))
                return false;
        return true;
    }
}
