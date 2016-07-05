package swen221.lab4.lang;

/**
 * Represents a distinct element of data which can be present in a given row
 * file. Currently, there are only two types provided here. However, we could
 * easily add more.
 * 
 * @author David J. Pearce
 *
 */
public interface RowType {
	/**
	 * Check whether a give item in a database row is an instance of a particular type.
	 * @return
	 */
	public boolean isInstance(Object item);
	
	/**
	 * The type of integer fields.
	 * 
	 * @author David J. Pearce
	 */
	public class Integer implements RowType {

		@Override
		public boolean isInstance(Object item) {
			return item instanceof java.lang.Integer;
		}		
	}

	/**
	 * The type of string fields.
	 * 
	 * @author David J. Pearce
	 */
	public class String implements RowType {

		@Override
		public boolean isInstance(Object item) {
			return item instanceof java.lang.String;
		}		
	}
}
