package com.bytebach.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import com.bytebach.model.InvalidOperation;
import com.bytebach.model.Value;

/**
 * This is a wrapper class for a list of rows, which is essentially all values of the table. In
 * addition, it wraps in a parent pointer to the table it belongs to.
 * 
 * @author Hector
 *
 */
public class MyRowList implements List<List<Value>> {

    private ArrayList<MyRow> rowList;
    private MyTable table;

    /**
     * Constructor.
     * 
     * @param table
     *            --- the pointer to the table it belongs to.
     */
    public MyRowList(MyTable table) {
        // null check
        if (table == null) {
            throw new InvalidOperation("The given table cannot be null.");
        }

        this.rowList = new ArrayList<>();
        this.table = table;
    }

    /**
     * A getter method to ger the pointer to the table it belongs to.
     * 
     * @return --- the pointer to the table it belongs to.
     */
    public MyTable getTable() {
        return this.table;
    }

    @Override
    public boolean add(List<Value> element) {
        MyRow addedRow = new MyRow(table, element);

        // check whether it's safe to add
        MyDatabase.checkSafeToAddRow(this, element);

        // if there is a reference value, need to update two maps in MyDatabase.
        MyDatabase.updateReference(table, addedRow);

        return rowList.add(addedRow); // List.add() always return true
    }

    @Override
    public void add(int index, List<Value> element) {
        MyRow addedRow = new MyRow(table, element);

        // check whether it's safe to add
        MyDatabase.checkSafeToAddRow(this, element);

        // if there is a reference value, need to update two maps in MyDatabase.
        MyDatabase.updateReference(table, addedRow);

        rowList.add(index, addedRow);
    }

    @Override
    public List<Value> remove(int index) {
        // boundary check
        if (index >= rowList.size() || index < 0) {
            throw new InvalidOperation("Index out of boundary.");
        }

        // cascading delete:
        Map<List<Value>, Set<MyRow>> rowToRefRow = table.getDatabase().getRowToRefRow();
        List<Value> thisRow = this.get(index);
        Set<MyRow> referencingRows = rowToRefRow.get(thisRow);

        if (referencingRows != null) {
            for (MyRow row : referencingRows) {
                row.getTable().rows().remove(row);
            }
            referencingRows.clear();
        }

        return rowList.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        if (contains(o)) {
            int index = indexOf(o);
            this.remove(index);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        for (Object element : c) {
            if (contains(element)) {
                int index = indexOf(element);
                this.remove(index);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        for (int i = 0; i < rowList.size(); i++) {
            this.remove(i);
        }
    }

    @Override
    public List<Value> set(int index, List<Value> element) {
        // boundary check
        if (index >= rowList.size() || index < 0) {
            throw new InvalidOperation("Index out of boundary.");
        }

        // a set action is essentially an add + a delete.
        this.add(index, element);
        return this.remove(index + 1);
    }

    @Override
    public int size() {
        return rowList.size();
    }

    @Override
    public boolean isEmpty() {
        return rowList.isEmpty();
    }

    @Override
    public List<Value> get(int index) {
        return rowList.get(index);
    }

    @Override
    public int indexOf(Object o) {
        return rowList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return rowList.lastIndexOf(o);
    }

    @Override
    public boolean contains(Object o) {
        return rowList.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return rowList.containsAll(c);
    }

    @Override
    public Iterator<List<Value>> iterator() {
        return new Iterator<List<Value>>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return this.currentIndex < rowList.size();
            }

            @Override
            public List<Value> next() {
                if (this.hasNext()) {
                    int current = currentIndex;
                    currentIndex++;
                    return rowList.get(current);
                }
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                throw new InvalidOperation("Unsupported Operation");
            }
        };
    }

    @Override
    public Object[] toArray() {
        return rowList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return rowList.toArray(a);
    }

    @Override
    public List<List<Value>> subList(int fromIndex, int toIndex) {
        List<MyRow> sub = rowList.subList(fromIndex, toIndex);
        List<List<Value>> newSub = new ArrayList<>();
        for (MyRow row : sub) {
            newSub.add(row);
        }
        return newSub;
    }

    /*
     * ======================================================
     * 
     * Forbidden methods
     * 
     * ======================================================
     */

    @Override
    public ListIterator<List<Value>> listIterator() {
        throw new InvalidOperation("Unsupported Operation.");
    }

    @Override
    public ListIterator<List<Value>> listIterator(int index) {
        throw new InvalidOperation("Unsupported Operation.");
    }

    @Override
    public boolean addAll(int index, Collection<? extends List<Value>> c) {
        throw new InvalidOperation("Unsupported Operation.");
    }

    @Override
    public boolean addAll(Collection<? extends List<Value>> c) {
        throw new InvalidOperation("Unsupported Operation.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new InvalidOperation("Unsupported Operation.");
    }
}
