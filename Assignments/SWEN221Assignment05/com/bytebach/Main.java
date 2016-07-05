package com.bytebach;

import java.io.*;
import java.util.*;
import com.bytebach.server.*;
import com.bytebach.model.*;
import com.bytebach.impl.*;

public class Main {

    // This determines the address of the style sheet to use. You're welcome to
    // use your own if you like. If you don't know what a stylesheet is, don't
    // worry --- you can safely ignore it.
    private static final String stylesheet = "http://www.ecs.vuw.ac.nz/~djp/bernies.css";

    /**
     * Simply start the server and run.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Create master database of all orders and parts.
        Database bachDB = new MyDatabase();
        initBachDB(bachDB);

        if (args.length > 0) {
            populateDBfromFile(args[0], bachDB);
        }

        // Finally, create server and run!
        new WebServer(bachDB, stylesheet).run();
    }

    private static void initBachDB(Database bachDB) {
        // First, create customer table
        ArrayList<Field> customerFields = new ArrayList<Field>();
        customerFields.add(new Field("Customer ID", Field.Type.INTEGER, true));
        customerFields.add(new Field("Name", Field.Type.TEXT, false));
        customerFields.add(new Field("Address", Field.Type.TEXTAREA, false));
        customerFields.add(new Field("Business", Field.Type.BOOLEAN, false));
        customerFields.add(new Field("Notes", Field.Type.TEXTAREA, false));
        bachDB.createTable("Customers", customerFields);

        // Second, create order table
        ArrayList<Field> orderFields = new ArrayList<Field>();
        orderFields.add(new Field("Order ID", Field.Type.INTEGER, true));
        orderFields.add(new Field("Customer", "Customers", false));
        orderFields.add(new Field("Item", "Items", false));
        orderFields.add(new Field("Quantity", Field.Type.INTEGER, false));
        bachDB.createTable("Orders", orderFields);

        // Third, create items table
        ArrayList<Field> itemsFields = new ArrayList<Field>();
        itemsFields.add(new Field("Item ID", Field.Type.INTEGER, true));
        itemsFields.add(new Field("Category", "Categories", false));
        itemsFields.add(new Field("Manufacturer", Field.Type.TEXT, false));
        itemsFields.add(new Field("Model", Field.Type.TEXT, false));
        itemsFields.add(new Field("Description", Field.Type.TEXTAREA, false));
        itemsFields.add(new Field("Price", Field.Type.INTEGER, false));
        itemsFields.add(new Field("Quantity", Field.Type.INTEGER, false));
        bachDB.createTable("Items", itemsFields);

        // Finally, create categories table
        ArrayList<Field> catFields = new ArrayList<Field>();
        catFields.add(new Field("Category ID", Field.Type.INTEGER, true));
        catFields.add(new Field("Name", Field.Type.TEXT, false));
        bachDB.createTable("Categories", catFields);

    }

    private static void populateDBfromFile(String filename, Database bachDB) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                ArrayList<Value> row = new ArrayList<Value>();
                for (int i = 1; i < split.length; ++i) {
                    String item = split[i];
                    if (Character.isDigit(item.charAt(0))) {
                        row.add(new IntegerValue(Integer.parseInt(item)));
                    } else if (item.equals("TRUE")) {
                        row.add(new BooleanValue(true));
                    } else if (item.equals("FALSE")) {
                        row.add(new BooleanValue(false));
                    } else if (item.charAt(0) == '"') {
                        // strip of "..."
                        item = item.substring(1, item.length() - 1);
                        row.add(new StringValue(item.replace("\\n", "\n")));
                    } else if (item.charAt(0) == '[') {
                        // strip of [...]
                        item = item.substring(1, item.length() - 1);
                        String[] s = item.split(":");
                        String table = s[0];
                        Object[] keys = new Object[1];
                        keys[0] = Integer.parseInt(s[1]);
                        row.add(new ReferenceValue(table, keys));
                    } else {
                        throw new RuntimeException("Invalid table item in row: " + line + "(" + item + ")");
                    }
                }
                Table table = bachDB.table(split[0]);
                table.rows().add(row);
            }
        } catch (IOException e) {
            System.err.println("Error loading file: " + filename + " (" + e.getMessage() + ")");
        }
    }
}
