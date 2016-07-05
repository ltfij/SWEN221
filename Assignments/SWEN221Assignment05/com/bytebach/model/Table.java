package com.bytebach.model;

import java.util.List;

/**
 * This interface specifies all behaviours of a Table in a database.
 * 
 * @author djp
 *
 */
public interface Table {

    /**
     * Get the table name
     * 
     * @return
     */
    public String name();

    /**
     * Return the list of all fields (i.e. including key fields) in the table. This list is <b>not
     * modifiable</b>. Any attempts to modify this list should result in an InvalidOperation
     * exception.
     */
    public List<Field> fields();

    /**
     * <p>
     * Return the list of rows in the table. Each row is simply a list of values, which must
     * correspond to the types determined in fields
     * </p>
     * 
     * <p>
     * The list returned is <b>modifiable</b>. This means one can add and remove rows from the table
     * via the list interface. Special care must be taken to ensure that such operations are checked
     * to ensure they do not violate the database constraints. However, one is not permitted to add
     * and remove fields within a row; rather, one may update them.
     * </p>
     * 
     * @return
     */
    public List<List<Value>> rows();

    /**
     * <p>
     * Return row matching a given key. The row is simply a list of values, which must correspond to
     * the types determined in fields. If no matching row is found, then an
     * <code>InvalidOperation</code> should be thrown.
     * </p>
     * 
     * <p>
     * The list returned is <b>partially modifiable</b>. One cannot add or remove items from the
     * list; however, one can set items in the list (provided they are not key fields). Special care
     * must be taken to ensure that such operations are checked to ensure they do not violate the
     * database constraints.
     * </p>
     * 
     * @return
     */
    public List<Value> row(Value... keys);

    /**
     * Delete a row with the matching keys. If no matching row is found, then an
     * <code>InvalidOperation</code> should be thrown
     * 
     * @param keys
     */
    public void delete(Value... keys);
}
