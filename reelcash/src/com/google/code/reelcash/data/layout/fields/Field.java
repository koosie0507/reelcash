/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data.layout.fields;

import com.google.code.reelcash.data.InvalidFieldReferenceException;
import com.google.code.reelcash.data.InvalidFieldValueException;
import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.KeyRoleComparator;
import java.io.Serializable;

/**
 * <p>This class is the abstract base class for all field classes. It contains all definitions which are common
 * to all fields.</p>
 *
 * @author andrei.olar
 */
public abstract class Field implements Serializable, Cloneable, Comparable {

    private static final long serialVersionUID = -4856521934598709569L;
    private String name;
    private Class<?> type;
    private boolean mandatory;
    private KeyRole keyRole;
    private Field referencedField;
    private FieldSet fieldSet;

    {
        referencedField = null;
        fieldSet = null;
    }

    /**
     * Creates a new instance of the field class.
     *
     * @param name the name of the new field.
     * @param keyRole the role of the new field.
     * @param type the type of the new field.
     * @param mandatory if this is true (default), the field must contain non-null values.
     */
    protected Field(String name, KeyRole keyRole, Class<?> type, boolean mandatory) {
        if (null == name)
            throw new IllegalArgumentException(Resources.getString("nullfieldnameerror"));

        this.name = name;
        this.keyRole = keyRole;
        this.type = null == type ? String.class : type;
        this.mandatory = mandatory;
    }

    /**
     * Compares this field with another field. A sorting algorithm based on
     * this method will place the fields in order by the key role they have,
     * then by name and eventually by type and length.
     * @param o another instance of the Field class.
     * @return -1, 0 or 1 depending on the result of the comparison.
     */
    public int compareTo(Object o) {
        if (!(o instanceof Field))
            return 0; // indeterminate

        Field f = (Field) o;
        int result = KeyRoleComparator.getInstance().compare(this.keyRole, f.keyRole);
        if (0 == result) {
            result = this.name.compareToIgnoreCase(f.name);
            if (0 == result)
                result = this.type.getName().compareToIgnoreCase(f.type.getName());
        }

        return result;
    }

    /**
     * Tests two fields for equality. The fields are tested by name because
     * in a collection there can't be two fields with the same name.
     * @param obj another field instance.
     * @return true, if the other field has the same name as the current field.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Field other = (Field) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
            return false;
        return true;
    }

    /**
     * Gets the field set to which the current field belongs.
     * @return the field set to which the current field belongs.
     */
    public FieldSet getFieldSet() {
        return fieldSet;
    }

    /**
     * Gets the role this field has.
     *
     * @return one of the @see code.google.code.reelcash.models.KeyRole members.
     */
    public KeyRole getKeyRole() {
        return keyRole;
    }

    /**
     * Gets the current field's name.
     * @return the name of the current field instance.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the field which is referenced by the current field. Works great for simple foreign keys.
     * @return the field which is referenced by the current instance.
     */
    public Field getReferencedField() {
        return referencedField;
    }

    /**
     * Returns a value based on the value passed as parameter.
     * @param actualValue the actual value.
     * @return a value which should be considered valid.
     */
    public Object getValidValue(Object actualValue) {
        if (!validateValue(actualValue))
            throw new InvalidFieldValueException(actualValue, this.name);
        return actualValue;
    }

    /**
     * Is true when the field is not allowed to contain null values.
     * @return true, if the field is mandatory.
     */
    public boolean isMandatory() {
        return mandatory;
    }

    /**
     * Returns the field name's hash code.
     * @return the hash code of the field's name.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.name == null ? 0 : this.name.hashCode());
        return hash;
    }

    /**
     * Sets the role of this field as a key.
     *
     * @param value the new role.
     */
    public void setKeyRole(KeyRole value) {
        this.keyRole = value;
    }

    /**
     * Sets wether this field is mandatory or not. A mandatory field is not allowed to contain
     * null values.
     *
     * @param value the value to set
     */
    public void setMandatory(boolean value) {
        this.mandatory = value;
    }

    /**
     * Sets the field set which contains the field.
     * @param value the referenced container.
     */
    public void setFieldSet(FieldSet value) {
        this.fieldSet = value;
    }

    /**
     * Sets the field which is referenced by the current field.
     * @param value the referenced field.
     */
    public void setReferencedField(Field value) {
        if (null != value.fieldSet && !value.fieldSet.equals(fieldSet))
            throw new InvalidFieldReferenceException(name, value.name);

        this.referencedField = value;
    }

    /**
     * Returns the name, role and type of a field.
     *
     * @return name, role and type of a field.
     */
    @Override
    public String toString() {
        return name.concat(", ").concat(keyRole.toString()).concat(", ").concat(type.getSimpleName());
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
    public boolean validateValue(Object value) {
        // check whether the field is mandatory and the value is null
        if (null == value && (mandatory || KeyRole.PRIMARY.equals(keyRole)))
            return false;

        // check whether the value is assignable to the given type 
        if (!(type.isInstance(value)))
            return false;

        return true;
    }
}
