package com.bytebach.model;

/**
 * A IntergerValue is simply a integer value used in tables in database.
 * 
 * @author djp
 *
 */
public class IntegerValue implements Value {
    private final int value;

    public IntegerValue(int val) {
        this.value = val;
    }

    /**
     * Return the integer in this value
     * 
     * @return
     */
    public int value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof IntegerValue) {
            IntegerValue v = (IntegerValue) o;
            return v.value == value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
