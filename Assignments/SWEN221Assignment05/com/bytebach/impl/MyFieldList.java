package com.bytebach.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.bytebach.model.Field;
import com.bytebach.model.InvalidOperation;

/**
 * This is a wrapper class for a list of fields, i.e. the schema. The meaning of wrapping it instead
 * of using a List of fields directly is that so we can check the validity of the given fields, and
 * prevent from modifying the schema.
 * 
 * @author Hector
 *
 */
public class MyFieldList implements List<Field> {

    private ArrayList<Field> fieldList;

    public MyFieldList(List<Field> fields, MyTable table) {

        // null check
        if (fields == null) {
            throw new InvalidOperation("The given fields cannot be null.");
        }
        if (table == null) {
            throw new InvalidOperation("The given table cannot be null.");
        }

        // check if there are same titles in the given fields
        for (int i = 0; i < fields.size() - 1; i++) {
            String title = fields.get(i).title();
            String nextTitle = fields.get(i + 1).title();
            if (title.equals(nextTitle)) {
                throw new InvalidOperation("Cannot have same titles in schema.");
            }
        }

        // check if at least 1 key field is found
        boolean foundKey = false;
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).isKey()) {
                foundKey = true;
                break;
            }
        }
        if (!foundKey) {
            throw new InvalidOperation("At least one key field is needed.");
        }

        this.fieldList = new ArrayList<>(fields);
    }

    /*
     * =========================================================
     * 
     * Methods of searching elements in this list. These violates no rules of database
     * 
     * =========================================================
     */

    @Override
    public int size() {
        return fieldList.size();
    }

    @Override
    public boolean isEmpty() {
        return fieldList.isEmpty();
    }

    @Override
    public Field get(int index) {
        return fieldList.get(index);
    }

    @Override
    public boolean contains(Object o) {
        return fieldList.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return fieldList.containsAll(c);
    }

    @Override
    public int indexOf(Object o) {
        return fieldList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return fieldList.lastIndexOf(o);
    }

    @Override
    public Iterator<Field> iterator() {
        return fieldList.iterator();
    }

    @Override
    public ListIterator<Field> listIterator() {
        return fieldList.listIterator();
    }

    @Override
    public ListIterator<Field> listIterator(int index) {
        return fieldList.listIterator();
    }

    @Override
    public Object[] toArray() {
        return fieldList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return fieldList.toArray(a);
    }

    @Override
    public List<Field> subList(int fromIndex, int toIndex) {
        return fieldList.subList(fromIndex, toIndex);
    }

    /*
     * =======================================================
     * 
     * Forbidden methods
     * 
     * =======================================================
     */

    @Override
    public boolean add(Field e) {
        throw new InvalidOperation("Cannot modify the schema.");
    }

    @Override
    public void add(int index, Field element) {
        throw new InvalidOperation("Cannot modify the schema.");
    }

    @Override
    public boolean addAll(Collection<? extends Field> c) {
        throw new InvalidOperation("Cannot modify the schema.");
    }

    @Override
    public boolean addAll(int index, Collection<? extends Field> c) {
        throw new InvalidOperation("Cannot modify the schema.");
    }

    @Override
    public Field remove(int index) {
        throw new InvalidOperation("Cannot modify the schema.");
    }

    @Override
    public boolean remove(Object o) {
        throw new InvalidOperation("Cannot modify the schema.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new InvalidOperation("Cannot modify the schema.");
    }

    @Override
    public void clear() {
        throw new InvalidOperation("Cannot modify the schema.");
    }

    @Override
    public Field set(int index, Field element) {
        throw new InvalidOperation("Cannot modify the schema.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new InvalidOperation("Cannot modify the schema.");
    }

}
