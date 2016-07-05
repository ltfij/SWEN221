package com.bytebach.model;

/**
 * A BooleanVaue is simply a boolean value used in tables in database.
 * 
 * @author djp
 *
 */
public class BooleanValue implements Value {
    private final boolean value;

    public BooleanValue(boolean val) {
        this.value = val;
    }

    /**
     * Return the boolean in this value
     * 
     * @return
     */
    public boolean value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BooleanValue) {
            BooleanValue v = (BooleanValue) o;
            return v.value == value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value ? 1 : 0;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}
