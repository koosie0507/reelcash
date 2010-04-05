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
        // TODO: throw an exception
        if (!(actualValue instanceof BigDecimal) || null == actualValue)
            return actualValue;

        BigDecimal actualDecimal = ((BigDecimal) actualValue);
        int diff = precision - actualDecimal.precision();
        actualDecimal = actualDecimal.movePointLeft(diff);
        return actualDecimal.setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * Sets the precision of the decimals.
     * @param precision the precision.
     */
    public void setPrecision(int precision) {
        this.precision = precision;
    }

    /**
     * Sets the scale of the decimals.
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
        if (!super.validateValue(value))
            return false;

        BigDecimal dec = (BigDecimal) value;
        return (dec.scale() <= scale && dec.precision() <= precision);
    }
}
