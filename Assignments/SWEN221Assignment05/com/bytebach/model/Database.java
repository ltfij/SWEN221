package com.bytebach.model;

import java.util.Collection;
import java.util.List;

/**
 * This interface specifies all behaviours of a basic database
 * 
 * @author djp
 *
 */
public interface Database {

    /**
     * Return the list of tables currently stored in the database. Observe that this list is
     * implicitly unmodifiable, because of the return type.
     * 
     * @return
     */
    public Collection<? extends Table> tables();

    /**
     * Return the table with the given name, or null if none exists.
     */
    public Table table(String name);

    /**
     * Create a table with the given name, and fields. If one already exists, then an
     * InvalidOperation is thrown.
     * 
     * @param name
     *            --- Table name
     * @param fields
     *            --- fields
     */
    public void createTable(String name, List<Field> fields);

    /**
     * Delete the table with the given name. Observe that this must additionally result in the
     * removal of any rows containing a field of reference type, which refers to this table. If no
     * matching table is found, then an <code>InvalidOperation</code> should be thrown
     * 
     * @param name
     */
    public void deleteTable(String name);
}
