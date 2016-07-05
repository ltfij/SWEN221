package com.bytebach.model;

/**
 * A StringValue represents a text based value in a database. It can match two Types in database's
 * schema, TEXT and TEXTAREA. The latter type can contain "\n" characters.
 * 
 * @author Hector
 *
 */
public class StringValue implements Value {
    private final String value;

    public StringValue(String val) {
        this.value = val;
    }

    /**
     * Return the text of this value
     * 
     * @return
     */
    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof StringValue) {
            StringValue v = (StringValue) o;
            return v.value.equals(value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
