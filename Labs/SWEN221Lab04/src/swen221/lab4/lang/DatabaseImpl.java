package swen221.lab4.lang;

import java.util.ArrayList;

public class DatabaseImpl implements Database {
    
    ColumnType [] schema;
    int keyField;
    ArrayList<Object[]> rows;
    
    public DatabaseImpl(ColumnType[] schema, int keyField, ArrayList<Object[]> rows) {
        this.schema = schema;
        this.keyField = keyField;
        this.rows = rows;
    }

    @Override
    public ColumnType[] getSchema() {
        return schema;
    }

    @Override
    public int getKeyField() {
        return keyField;
    }

    @Override
    public int size() {
        return rows.size();
    }

    @Override
    public void addRow(Object... data) throws InvalidRowException, DuplicateKeyException {
        if (data.length != schema.length) {
            throw new InvalidRowException();
        }
        
        for (Object[] row : rows) {
            if (row[keyField].equals(data[keyField])) {
                throw new DuplicateKeyException();
            }
        }
        
        Object[] row = new Object[schema.length];
        for (int i = 0; i < data.length; i++) {
            if (schema[i].getType().isInstance(data[i])) {
                row[i] = data[i];
            } else {
                throw new InvalidRowException();
            }
        }
        
        rows.add(row);
    }

    @Override
    public Object[] getRow(Object key) throws InvalidKeyException {
        
        for (Object[] row : rows) {
            if (row[keyField].equals(key)) {
                return row;
            }
        }
        
        throw new InvalidKeyException();
    }

    @Override
    public Object[] getRow(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= rows.size()) {
            throw new IndexOutOfBoundsException();
        }
        
        return rows.get(index);
    }
}
