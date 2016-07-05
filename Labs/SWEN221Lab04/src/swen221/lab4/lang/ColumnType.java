package swen221.lab4.lang;

/**
 * Represents a column in the database. This determines the column's name and
 * type, and essentially defines part of the database's schema.
 * 
 * @author David J. Pearce
 *
 */
public final class ColumnType {
	private final String name;
	private final RowType type;

	public ColumnType(String name, RowType type) {
		this.name = name;
		this.type = type;			
	}

	public String getName() {
		return name;
	}

	public RowType getType() {
		return type;
	}
	
	
	
	
}