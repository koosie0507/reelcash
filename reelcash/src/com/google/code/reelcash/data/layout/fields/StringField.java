package com.google.code.reelcash.data.layout.fields;

import com.google.code.reelcash.data.KeyRole;

/**
 * Implements the @see com.google.code.reelcash.models.fields.Field abstract class. Values
 * for this field should be String types.
 *
 * @author andrei.olar
 */
public class StringField extends Field {

    private static final long serialVersionUID = -1367261005920942956L;
    private static final int DEFAULT_MAX_SIZE = 255;
    private int maxSize;

    /**
     * Creates a new field of String.
     * @param name the name of the new field.
     * @param role the role of the new field.
     * @param mandatory whether the field is mandatory or not.
     * @param maxSize the maximum length of the strings (in characters).
     */
    public StringField(String name, KeyRole role, boolean mandatory, int maxSize) {
        super(name, role, String.class, mandatory);
        this.maxSize = maxSize;
    }

    /**
     * Creates a new field of String.
     * @param name the name of the new field.
     * @param role the role of the new field.
     * @param mandatory whether the field is mandatory or not.
     */
    public StringField(String name, KeyRole role, boolean mandatory) {
        this(name, role, mandatory, DEFAULT_MAX_SIZE);
    }

    /**
     * Creates a new field of String.
     * @param name the name of the new field.
     * @param role the role of the new field.
     */
    public StringField(String name, KeyRole role) {
        this(name, role, true, DEFAULT_MAX_SIZE);
    }

    /**
     * Creates a new field of String.
     * @param name the name of the new field.
     */
    public StringField(String name) {
        this(name, KeyRole.NONE, true, DEFAULT_MAX_SIZE);
    }

    /**
     * Gets the maximum length (in characters) permitted for a string within this field.
     * @return the maximum permitted length (in characters).
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Returns a string which will definetly not exceed the max size specified for this field. If the input value
     * exceeds the maximum size then the string will be truncated from the left.
     * @param actualValue an input string.
     * @return a string of a length which is not larger than that returned by <code>getMaxSize()</code>
     */
    @Override
    public Object getValidValue(Object actualValue) {
        if (null == actualValue || !(actualValue instanceof String))
            return actualValue;

        int actualSize = ((String) actualValue).length();
        if (maxSize < actualSize)
            return ((String) actualValue).substring(actualSize - maxSize);
        return actualValue;
    }

    /**
     * Sets the maximum length (in characters) permitted for a string within this field.
     * @param maxSize the maximum permitted length (in characters).
     */
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
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

        if (null != value && ((String) value).length() > maxSize)
            return false;

        return true;
    }
}
