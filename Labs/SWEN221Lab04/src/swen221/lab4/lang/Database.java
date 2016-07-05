package swen221.lab4.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines an interface representing a simple notion of a database. A database
 * consists of rows of data which are divided into columns. Exactly one column
 * can be nominated as the "key" field, such that no two rows are permitted with
 * the same key field.
 * 
 * @author David J. Pearce
 *
 */
public interface Database {
	
	/**
	 * Get the schema for this database. This returns the name and type for each
	 * column in the database. All rows in the database must confirm to this
	 * schema.
	 * 
	 * @return
	 */
	public ColumnType[] getSchema();
	
	/**
	 * Return the column designated as the special key field. No two rows in the
	 * database may exist with the same value for this field.
	 * 
	 * @return
	 */
	public int getKeyField();
	
	/**
	 * Get the number of rows in the database.
	 * 
	 * @return
	 */
	public int size();
	
	/**
	 * Add a row to the database. This row must conform to the schema, and its
	 * key field cannot match that of any other previously added to the
	 * database.
	 * 
	 * @param data
	 *            the data for the row
	 * @throws InvalidRowException
	 *             If the row does not conform to the database schema.
	 * @throws DuplicateKeyException
	 *             if a row with matching key field already exists.
	 */	
	public void addRow(Object... data) throws InvalidRowException,DuplicateKeyException;
	
	/**
	 * Get a row from the database matching the given field. If no such field
	 * exists, then an InvalidKeyException is thrown.
	 * 
	 * @param key
	 *            The value to match against the key field of a row.
	 * @return The data items making up a row of the database. The number of
	 *         items must match the number of columns identified by the schema.
	 * @throws InvalidKeyException
	 */
	public Object[] getRow(Object key) throws InvalidKeyException;
	
	/**
	 * Get a row from the database based on its index value, rather than its key
	 * field. The index cannot be negative and must be less than the number of
	 * rows, otherwise an IndexOutOfBoundsException is thrown.
	 * 
	 * @param index
	 * @return the index-th row of the ordered data, where the first row is row
	 *         0
	 * @throws IndexOutOfBoundsException
	 *             if a row at the given index does not exist
	 */
	public Object[] getRow(int index) throws IndexOutOfBoundsException;		
}
