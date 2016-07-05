package com.bytebach.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.bytebach.model.Field;
import com.bytebach.model.InvalidOperation;
import com.bytebach.model.ReferenceValue;
import com.bytebach.model.Value;

/**
 * This is a wrapper class for a list of values, which is essentially a row in a table. In addition,
 * it wraps in a parent pointer to the table it belongs to.
 * 
 * @author Hector
 *
 */
public class MyRow implements List<Value> {

    private ArrayList<Value> list;
    private MyTable table;

    /**
     * Constructor
     * 
     * @param table
     *            --- a pointer to the table it belongs to.
     * @param list
     *            --- a list of values to make this row
     */
    public MyRow(MyTable table, List<Value> list) {
        // null check
        if (list == null) {
            throw new InvalidOperation("Cannot construct a row from null.");
        }
        if (table == null) {
            throw new InvalidOperation("The given table cannot be null.");
        }

        this.list = new ArrayList<>(list);
        this.table = table;
    }

    /**
     * A getter method to get the pointer to the table it belongs to.
     * 
     * @return --- a pointer to the table it belongs to.
     */
    public MyTable getTable() {
        return table;
    }

    @Override
    public Value set(int index, Value element) {
        // cannot set a null
        if (element == null) {
            throw new InvalidOperation("Cannot set a null.");
        }
        // boundary check
        if (index >= list.size() || index < 0) {
            throw new InvalidOperation("Index out of boundary.");
        }
        // Cannot set a key field
        if (table.fields().get(index).isKey()) {
            throw new InvalidOperation("Cannot modify a key value.");
        }
        // element must correspond to the type determined in schema
        Field field = table.fields().get(index);
        if (!MyDatabase.checkTypeMatch(field, element)) {
            throw new InvalidOperation("Cannot set a value with incorrect type.");
        }
        // if it's a reference, need to check its validity, and to update two maps in MyDatabase.
        if (element instanceof ReferenceValue) {
            MyDatabase.updateReference(table, (ReferenceValue) element, this);
        }

        return list.set(index, element);
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
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public Value get(int index) {
        return list.get(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public Iterator<Value> iterator() {
        return list.iterator();
    }

    @Override
    public ListIterator<Value> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<Value> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<Value> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    /*
     * =======================================================
     * 
     * Forbidden methods
     * 
     * =======================================================
     */

    @Override
    public boolean add(Value e) {
        throw new InvalidOperation("Unsupported Operation");
    }

    @Override
    public void add(int index, Value element) {
        throw new InvalidOperation("Unsupported Operation");
    }

    @Override
    public boolean addAll(Collection<? extends Value> c) {
        throw new InvalidOperation("Unsupported Operation");
    }

    @Override
    public boolean addAll(int index, Collection<? extends Value> c) {
        throw new InvalidOperation("Unsupported Operation");
    }

    @Override
    public Value remove(int index) {
        throw new InvalidOperation("Unsupported Operation");
    }

    @Override
    public boolean remove(Object o) {
        throw new InvalidOperation("Unsupported Operation");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new InvalidOperation("Unsupported Operation");
    }

    @Override
    public void clear() {
        throw new InvalidOperation("Unsupported Operation");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new InvalidOperation("Unsupported Operation");
    }

}
