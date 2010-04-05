package com.google.code.reelcash.data.layout.fields;

import com.google.code.reelcash.data.KeyRole;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/**
 * Represents a set of fields.
 *
 * @author andrei.olar 
 */
public class FieldSet implements Set<Field> {

    private Set<Field> fieldSet;
    private Set<Field> primary;
    private Set<Field> unique;
    private Set<Field> keys;

    {
        fieldSet = Collections.emptySet();
    }

    /**
     * Adds a field to the current set.
     *
     * @param e field to add.
     *
     * @return true, if the field has been added.
     */
    public boolean add(Field e) {
        e.setFieldSet(this);
        return fieldSet.add(e);
    }

    /**
     * Adds all of the fields in the given collection to the current field set.
     *
     * @param c the collection of fields to add fields from.
     *
     * @return true if all the fields have been successfully added. 
     */
    public boolean addAll(Collection<? extends Field> c) {
        for (Field x : c) {
            x.setFieldSet(this);
        }
        return fieldSet.addAll(c);
    }

    public void clear() {
        for (Field x : fieldSet) {
            x.setFieldSet(null);
        }
        fieldSet.clear();
    }

    public boolean contains(String fieldName) {
        for (Field f : fieldSet) {
            if (f.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(Object o) {
        if (o instanceof Field) {
            return fieldSet.contains((Field) o);
        }
        return false;
    }

    public boolean containsAll(Collection<?> c) {
        return fieldSet.containsAll(c);
    }

    /**
     * Gets the field with the given name.
     * 
     * @param name the name of the seeked field.
     *
     * @return the field with the given name.
     */
    public Field get(String name) {
        for (Field f : fieldSet) {
            if (f.getName().equals(name)) {
                return f;
            }
        }

        throw new FieldNotFoundException(name);
    }

    public Set<Field> getKeys() {
        if (null == keys) {
            for (Field f : fieldSet) {
                if (KeyRole.KEY == f.getKeyRole()) {
                    keys.add(f);
                }
            }
        }
        return keys;
    }

    public Set<Field> getPrimary() {
        if (null == primary) {
            for (Field f : fieldSet) {
                if (f.getKeyRole() == KeyRole.PRIMARY) {
                    primary.add(f);
                }
            }
        }
        return primary;
    }

    public Set<Field> getUnique() {
        if (null == unique) {
            for (Field f : fieldSet) {
                if (f.getKeyRole() == KeyRole.UNIQUE) {
                    unique.add(f);
                }
            }
        }
        return unique;
    }

    public boolean isEmpty() {
        return fieldSet.isEmpty();
    }

    public Iterator<Field> iterator() {
        return fieldSet.iterator();
    }

    public boolean remove(Object o) {
        if (o instanceof Field) {
            ((Field) o).setFieldSet(null);
            return fieldSet.remove((Field) o);
        }
        return false;
    }

    public boolean removeAll(Collection<?> c) {
        for (Object x : c) {
            ((Field) x).setFieldSet(null);
        }
        return fieldSet.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        for (Object x : fieldSet) {
            if (!c.contains(x)) {
                ((Field) x).setFieldSet(null);
            }
        }
        return fieldSet.retainAll(c);
    }

    public int size() {
        return fieldSet.size();
    }

    public Object[] toArray() {
        return fieldSet.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return fieldSet.toArray(a);
    }
}
