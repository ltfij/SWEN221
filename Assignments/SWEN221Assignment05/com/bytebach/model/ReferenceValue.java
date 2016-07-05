package com.bytebach.model;

import java.util.Arrays;

/**
 * A Reference is a special kind of row entry which refers to an entry in another table. This is
 * useful for ensuring consistency between tables. For example, if we have an order for a given
 * customer, then that customer must exist! Furthermore, we want cascading delete semantics; that
 * is, when a table entry X is removed, then all entries which contain references to X are also
 * removed (since otherwise they are effectively dangling pointers).
 * 
 * @author djp
 * 
 */
public class ReferenceValue implements Value {
    private final String table;
    private final Value[] keys;

    /**
     * Create a reference to a table entry. This is specified as a table name, and a list of keys
     * for that table. Obviously, the number and type of keys must match the table field
     * declarations.
     * 
     * @param table
     * @param keys
     */
    public ReferenceValue(String table, Value... keys) {
        this.table = table;
        this.keys = keys;

        // XXX should I check it here for whether the number and type of keys
        // match the table field declarations.
    }

    /**
     * Create a reference to a table entry. This is specified as a table name, and a list of keys
     * for that table. Obviously, the number and type of keys must match the table field
     * declarations.
     * 
     * @param table
     * @param keys
     *            --- as objects, which are automatically converted to Values.
     */
    public ReferenceValue(String table, Object... keys) {
        this.table = table;
        this.keys = new Value[keys.length];
        for (int i = 0; i != keys.length; ++i) {
            Object k = keys[i];
            if (k instanceof Integer) {
                this.keys[i] = new IntegerValue((Integer) k);
            } else if (k instanceof Boolean) {
                this.keys[i] = new BooleanValue((Boolean) k);
            } else if (k instanceof String) {
                this.keys[i] = new StringValue((String) k);
            } else if (k instanceof ReferenceValue) {
                this.keys[i] = (ReferenceValue) k;
            } else {
                throw new IllegalArgumentException("Invalid key parameters: " + k);
            }
        }
    }

    /**
     * Get the table name.
     * 
     * @return
     */
    public String table() {
        return table;
    }

    /**
     * Get the keys associated with this reference
     * 
     * @return
     */
    public Value[] keys() {
        return keys;
    }

    @Override
    public int hashCode() {
        return table.hashCode() + keys.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ReferenceValue) {
            ReferenceValue r = (ReferenceValue) o;
            return r.table.equals(table) && Arrays.equals(keys, r.keys);
        }
        return false;
    }

    @Override
    public String toString() {
        String r = "[" + table;
        for (Value k : keys) {
            r += ":" + k;
        }
        return r + "]";
    }
}
