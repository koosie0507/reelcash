package com.google.code.reelcash.data.layout.fields;

import com.google.code.reelcash.data.KeyRole;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Represents a set of fields.
 *
 * @author andrei.olar 
 */
public class FieldList implements List<Field> {

    private ArrayList<Field> data;
    private ArrayList<Field> primary;
    private ArrayList<Field> unique;
    private ArrayList<Field> keys;

    {
        data = new ArrayList();
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
        return data.add(e);
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
        return data.addAll(c);
    }

    public void clear() {
        for (Field x : data) {
            x.setFieldSet(null);
        }
        data.clear();
    }

    public boolean contains(String fieldName) {
        for (Field f : data) {
            if (f.getName().equals(fieldName))
                return true;
        }
        return false;
    }

    public boolean contains(Object o) {
        if (o instanceof Field)
            return data.contains((Field) o);
        return false;
    }

    public boolean containsAll(Collection<?> c) {
        return data.containsAll(c);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final FieldList other = (FieldList) obj;
        if (this.data != other.data && (this.data == null || !this.data.equals(other.data)))
            return false;
        if (this.primary != other.primary && (this.primary == null || !this.primary.equals(other.primary)))
            return false;
        if (this.unique != other.unique && (this.unique == null || !this.unique.equals(other.unique)))
            return false;
        if (this.keys != other.keys && (this.keys == null || !this.keys.equals(other.keys)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.data != null ? this.data.hashCode() : 0);
        hash = 53 * hash + (this.primary != null ? this.primary.hashCode() : 0);
        hash = 53 * hash + (this.unique != null ? this.unique.hashCode() : 0);
        hash = 53 * hash + (this.keys != null ? this.keys.hashCode() : 0);
        return hash;
    }

    /**
     * Gets the field with the given name.
     * 
     * @param name the name of the seeked field.
     *
     * @return the field with the given name.
     */
    public Field get(String name) {
        for (Field f : data) {
            if (f.getName().equals(name))
                return f;
        }

        throw new FieldNotFoundException(name);
    }

    public ArrayList<Field> getKeys() {
        if (null == keys) {
            keys = new ArrayList();
            for (Field f : data) {
                if (KeyRole.KEY == f.getKeyRole())
                    keys.add(f);
            }
        }
        return keys;
    }

    public ArrayList<Field> getPrimary() {
        if (null == primary) {
            primary = new ArrayList();
            for (Field f : data) {
                if (f.getKeyRole() == KeyRole.PRIMARY)
                    primary.add(f);
            }

            if (primary.isEmpty())
                primary = data;
        }
        return primary;
    }

    public ArrayList<Field> getUnique() {
        if (null == unique) {
            unique = new ArrayList();
            for (Field f : data) {
                if (f.getKeyRole() == KeyRole.UNIQUE)
                    unique.add(f);
            }
        }
        return unique;
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public Iterator<Field> iterator() {
        return data.iterator();
    }

    public boolean remove(Object o) {
        if (o instanceof Field) {
            ((Field) o).setFieldSet(null);
            return data.remove((Field) o);
        }
        return false;
    }

    public boolean removeAll(Collection<?> c) {
        for (Object x : c) {
            ((Field) x).setFieldSet(null);
        }
        return data.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        for (Object x : data) {
            if (!c.contains(x))
                ((Field) x).setFieldSet(null);
        }
        return data.retainAll(c);
    }

    public int size() {
        return data.size();
    }

    public Object[] toArray() {
        return data.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return data.toArray(a);
    }

    public boolean addAll(int index, Collection<? extends Field> c) {
        return data.addAll(index, c);
    }

    public Field get(int index) {
        return data.get(index);
    }

    public Field set(int index, Field element) {
        return data.set(index, element);
    }

    public void add(int index, Field element) {
        data.add(index, element);
    }

    public Field remove(int index) {
        return data.remove(index);
    }

    public int indexOf(Object o) {
        return data.indexOf(o);
    }

    public int indexOf(String fieldName) {
        int idx = 0;
        while (idx < data.size()) {
            if (data.get(idx).getName().equals(fieldName))
                return idx;
            idx++;
        }
        return -1;
    }

    public int lastIndexOf(String fieldName) {
        int idx = data.size() - 1;
        while (idx > -1) {
            if (data.get(idx).getName().equals(fieldName))
                return idx;
            idx--;
        }
        return idx;
    }

    public int lastIndexOf(Object o) {
        return data.lastIndexOf(o);
    }

    public ListIterator<Field> listIterator() {
        return data.listIterator();
    }

    public ListIterator<Field> listIterator(int index) {
        return data.listIterator(index);
    }

    public List<Field> subList(int fromIndex, int toIndex) {
        return data.subList(fromIndex, toIndex);
    }
}
