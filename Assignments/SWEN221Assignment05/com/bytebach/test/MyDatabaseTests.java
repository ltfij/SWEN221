package com.bytebach.test;

import org.junit.*;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;
import java.util.*;

import com.bytebach.impl.MyDatabase;
import com.bytebach.model.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyDatabaseTests {
    // The following provides some simple lists of fields to test with
    private static final Field[] FIELDS_1 = { new Field("ID", Field.Type.INTEGER, true),
            new Field("TEXT", Field.Type.TEXT, false) };
    private static final Field[] FIELDS_2 = { new Field("ID", Field.Type.INTEGER, true),
            new Field("REF", "table", false) };

    @Test
    public void test01_CreateTable() {
        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
    }

    @Test
    public void test02_CreateTable() {
        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        try {
            createTable(db, "table", FIELDS_1);
            fail("Shouldn't be able to creat table with same name");
        } catch (InvalidOperation e) {
        }
    }

    @Test
    public void test03_AddRow() {
        Object[][] rawData = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Value[][] rows = toValues(rawData);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);

        addRow(db, "table", rows[0]);
        addRow(db, "table", rows[1]);

        checkTable(db, "table", rows);
    }

    @Test
    public void test04_AddRow() {
        Object[][] rawData = { { 0, "Hello WOrld" }, { 0, "Blah" } };
        Value[][] rows = toValues(rawData);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        addRow(db, "table", rows[0]);
        try {
            addRow(db, "table", rows[1]);
            fail("Shouldn't be able to add row with same key field as another");
        } catch (InvalidOperation e) {

        }
    }

    @Test
    public void test05_RemoveRow() {
        Object[][] rawData = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Value[][] rows = toValues(rawData);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        addRow(db, "table", rows[0]);
        addRow(db, "table", rows[1]);
        removeRow2(db, "table", new IntegerValue(0));

        Value[][] nrows = toValues(new Object[][] { { 1, "Blah" } });

        checkTable(db, "table", nrows);
    }

    @Test
    public void test06_RemoveRow() {
        Object[][] rawData = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Value[][] rows = toValues(rawData);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        addRow(db, "table", rows[0]);
        addRow(db, "table", rows[1]);
        db.table("table").rows().remove(0);

        Value[][] nrows = toValues(new Object[][] { { 1, "Blah" } });

        checkTable(db, "table", nrows);
    }

    @Test
    public void test07_SetRow() {
        Object[][] rawData = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Value[][] rows = toValues(rawData);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        addRow(db, "table", rows[0]);
        addRow(db, "table", rows[1]);
        modifyRow2(db, "table", 1, "Blah", 0);

        rows[0][1] = new StringValue("Blah"); // mirror update

        checkTable(db, "table", rows);
    }

    @Test
    public void test08_SetRow() {
        Object[][] rawData = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Value[][] rows = toValues(rawData);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        addRow(db, "table", rows[0]);
        addRow(db, "table", rows[1]);

        try {
            // Here, we're trying to set a key value ... should be impossible!
            db.table("table").row(new IntegerValue(0)).set(0, new IntegerValue(1));
            fail("Shouldn't be able to change value of key field");
        } catch (InvalidOperation e) {

        }
    }

    @Test
    public void test09_SetRow() {
        Object[][] rawData = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Value[][] rows = toValues(rawData);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        addRow(db, "table", rows[0]);
        addRow(db, "table", rows[1]);

        try {
            db.table("table").row(new IntegerValue(0)).set(1, new IntegerValue(1));
            fail("Shouldn't be able to set field to value of incorrect type");
        } catch (InvalidOperation e) {

        }
    }

    @Test
    public void test10_SetRow() {
        Object[][] rawData = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Value[][] rows = toValues(rawData);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        addRow(db, "table", rows[0]);
        addRow(db, "table", rows[1]);

        try {
            db.table("table").row(new IntegerValue(0)).set(1,
                    new StringValue("A TEXT field cannot have new lines\nLike this.  Only TEXTAREAs can."));
            fail("Shouldn't be able to field to value of incorrect type");
        } catch (InvalidOperation e) {

        }
    }

    @Test
    public void test11_RowModification() {
        Object[][] rawData = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Value[][] rows = toValues(rawData);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        addRow(db, "table", rows[0]);
        addRow(db, "table", rows[1]);

        try {
            db.table("table").row(new IntegerValue(0)).add(new StringValue("Hello"));
            fail("Shouldn't be able to add field to row");
        } catch (InvalidOperation e) {

        }
    }

    @Test
    public void test12_RowModification() {
        Object[][] rawData = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Value[][] rows = toValues(rawData);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        addRow(db, "table", rows[0]);
        addRow(db, "table", rows[1]);

        try {
            db.table("table").row(new IntegerValue(0)).remove(0);
            fail("Shouldn't be able to remove field from row");
        } catch (InvalidOperation e) {

        }
    }

    @Test
    public void test13_ReferenceAdd() {
        Object[][] rawTableRows = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Object[][] rawRefRows = { { 0, new ReferenceValue("table", 0) } };
        Value[][] tableRows = toValues(rawTableRows);
        Value[][] refRows = toValues(rawRefRows);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        createTable(db, "refs", FIELDS_2);
        addRow(db, "table", tableRows[0]);
        addRow(db, "table", tableRows[1]);
        addRow(db, "refs", refRows[0]);

        checkTable(db, "table", tableRows);
        checkTable(db, "refs", refRows);
    }

    @Test
    public void test14_ReferenceAdd() {
        Object[][] rawTableRows = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Object[][] rawRefRows = { { 0, new ReferenceValue("table", 2) } };
        Value[][] tableRows = toValues(rawTableRows);
        Value[][] refRows = toValues(rawRefRows);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        createTable(db, "refs", FIELDS_2);
        addRow(db, "table", tableRows[0]);
        addRow(db, "table", tableRows[1]);
        try {
            addRow(db, "refs", refRows[0]);
            fail("Shouldn't be able to add row containing invalid reference");
        } catch (InvalidOperation e) {

        }
    }

    @Test
    public void test15_ReferenceAdd() {
        Object[][] rawTableRows = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Object[][] rawRefRows = { { 0, new ReferenceValue("BROKEN", 0) } };
        Value[][] tableRows = toValues(rawTableRows);
        Value[][] refRows = toValues(rawRefRows);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        createTable(db, "refs", FIELDS_2);
        addRow(db, "table", tableRows[0]);
        addRow(db, "table", tableRows[1]);
        try {
            addRow(db, "refs", refRows[0]);
            fail("Shouldn't be able to add row containing invalid reference");
        } catch (InvalidOperation e) {

        }
    }

    @Test
    public void test16_ReferenceSet() {
        Object[][] rawTableRows = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Object[][] rawRefRows = { { 0, new ReferenceValue("table", 0) } };
        Value[][] tableRows = toValues(rawTableRows);
        Value[][] refRows = toValues(rawRefRows);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        createTable(db, "refs", FIELDS_2);
        addRow(db, "table", tableRows[0]);
        addRow(db, "table", tableRows[1]);
        addRow(db, "refs", refRows[0]);
        modifyRow2(db, "refs", 1, new ReferenceValue("table", 1), 0);

        // mirror update
        refRows[0][1] = new ReferenceValue("table", 1);

        checkTable(db, "table", tableRows);
        checkTable(db, "refs", refRows);
    }

    @Test
    public void test17_ReferenceSet() {
        Object[][] rawTableRows = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Object[][] rawRefRows = { { 0, new ReferenceValue("table", 0) } };
        Value[][] tableRows = toValues(rawTableRows);
        Value[][] refRows = toValues(rawRefRows);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        createTable(db, "refs", FIELDS_2);
        addRow(db, "table", tableRows[0]);
        addRow(db, "table", tableRows[1]);
        addRow(db, "refs", refRows[0]);
        try {
            db.table("refs").row(new IntegerValue(0)).set(1, new ReferenceValue("table", 2));
            fail("Shouldn't be able to set row entry to invalid reference");
        } catch (InvalidOperation e) {

        }
    }

    @Test
    public void test18_ReferenceSet() {
        Object[][] rawTableRows = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Object[][] rawRefRows = { { 0, new ReferenceValue("table", 0) } };
        Value[][] tableRows = toValues(rawTableRows);
        Value[][] refRows = toValues(rawRefRows);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        createTable(db, "refs", FIELDS_2);
        addRow(db, "table", tableRows[0]);
        addRow(db, "table", tableRows[1]);
        addRow(db, "refs", refRows[0]);
        try {
            db.table("refs").row(new IntegerValue(0)).set(1, new ReferenceValue("BROKEN", 2));
            fail("Shouldn't be able to set row entry to invalid reference");
        } catch (InvalidOperation e) {

        }
    }

    @Test
    public void test19_ReferenceSet() {
        Object[][] rawTableRows = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Object[][] rawRefRows = { { 0, new ReferenceValue("table", 0) } };
        Value[][] tableRows = toValues(rawTableRows);
        Value[][] refRows = toValues(rawRefRows);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        createTable(db, "refs", FIELDS_2);
        addRow(db, "table", tableRows[0]);
        addRow(db, "table", tableRows[1]);
        addRow(db, "refs", refRows[0]);
        try {
            db.table("refs").row(new IntegerValue(0)).set(1, new ReferenceValue("table", 1, 1));
            fail("Shouldn't be able to set row entry to invalid reference");
        } catch (InvalidOperation e) {

        }
    }

    @Test
    public void test20_ReferenceSet() {
        Object[][] rawTableRows = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Object[][] rawRefRows = { { 0, new ReferenceValue("table", 0) } };
        Value[][] tableRows = toValues(rawTableRows);
        Value[][] refRows = toValues(rawRefRows);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        createTable(db, "refs", FIELDS_2);
        addRow(db, "table", tableRows[0]);
        addRow(db, "table", tableRows[1]);
        addRow(db, "refs", refRows[0]);
        try {
            db.table("refs").row(new IntegerValue(0)).set(1, new ReferenceValue("table"));
            fail("Shouldn't be able to set row entry to invalid reference");
        } catch (InvalidOperation e) {

        }
    }

    @Test
    public void test21_ReferenceSet() {
        Object[][] rawTableRows = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Object[][] rawRefRows = { { 0, new ReferenceValue("table", 0) } };
        Value[][] tableRows = toValues(rawTableRows);
        Value[][] refRows = toValues(rawRefRows);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        createTable(db, "refs", FIELDS_2);
        addRow(db, "table", tableRows[0]);
        addRow(db, "table", tableRows[1]);
        addRow(db, "refs", refRows[0]);
        try {
            db.table("refs").row(new IntegerValue(0)).set(1, new ReferenceValue("table", "invalidKey"));
            fail("Shouldn't be able to set row entry to invalid reference");
        } catch (InvalidOperation e) {

        }
    }

    @Test
    public void test22_CascadingDelete() {
        Object[][] rawTableRows = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Object[][] rawRefRows = { { 0, new ReferenceValue("table", 0) } };
        Value[][] tableRows = toValues(rawTableRows);
        Value[][] refRows = toValues(rawRefRows);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        createTable(db, "refs", FIELDS_2);
        addRow(db, "table", tableRows[0]);
        addRow(db, "table", tableRows[1]);
        addRow(db, "refs", refRows[0]);
        removeRow2(db, "table", new IntegerValue(0));

        // mirror update
        Object[][] nRawTableRows = { { 1, "Blah" } };
        Value[][] nTableRows = toValues(nRawTableRows);
        Object[][] nRawRefRows = {};
        Value[][] nRefRows = toValues(nRawRefRows);

        checkTable(db, "table", nTableRows);
        checkTable(db, "refs", nRefRows);
    }

    @Test
    public void test23_CascadingDelete() {
        Object[][] rawTableRows = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Object[][] rawRefRows = { { 0, new ReferenceValue("table", 0) },
                { 1, new ReferenceValue("table", 1) } };
        Value[][] tableRows = toValues(rawTableRows);
        Value[][] refRows = toValues(rawRefRows);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        createTable(db, "refs", FIELDS_2);
        addRow(db, "table", tableRows[0]);
        addRow(db, "table", tableRows[1]);
        addRow(db, "refs", refRows[0]);
        addRow(db, "refs", refRows[1]);
        removeRow2(db, "table", new IntegerValue(0));

        // mirror update
        Object[][] nRawTableRows = { { 1, "Blah" } };
        Value[][] nTableRows = toValues(nRawTableRows);
        Object[][] nRawRefRows = { { 1, new ReferenceValue("table", 1) } };
        Value[][] nRefRows = toValues(nRawRefRows);

        checkTable(db, "table", nTableRows);
        checkTable(db, "refs", nRefRows);
    }

    @Test
    public void test24_CascadingDelete() {
        Object[][] rawTableRows = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Object[][] rawRefRows = { { 0, new ReferenceValue("table", 0) },
                { 1, new ReferenceValue("table", 1) }, { 2, new ReferenceValue("table", 0) } };
        Value[][] tableRows = toValues(rawTableRows);
        Value[][] refRows = toValues(rawRefRows);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        createTable(db, "refs", FIELDS_2);
        addRow(db, "table", tableRows[0]);
        addRow(db, "table", tableRows[1]);
        addRow(db, "refs", refRows[0]);
        addRow(db, "refs", refRows[1]);
        addRow(db, "refs", refRows[2]);
        removeRow2(db, "table", new IntegerValue(0));

        // mirror update
        Object[][] nRawTableRows = { { 1, "Blah" } };
        Value[][] nTableRows = toValues(nRawTableRows);
        Object[][] nRawRefRows = { { 1, new ReferenceValue("table", 1) } };
        Value[][] nRefRows = toValues(nRawRefRows);

        checkTable(db, "table", nTableRows);
        checkTable(db, "refs", nRefRows);
    }

    // ===============Tests from Hammid========================

    @Test
    public void test25_CascadingDelete() {
        Object[][] rawTableRows = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Object[][] rawRefRows = { { 0, new ReferenceValue("table", 0) },
                { 1, new ReferenceValue("table", 1) }, { 2, new ReferenceValue("table", 0) } };
        Value[][] tableRows = toValues(rawTableRows);
        Value[][] refRows = toValues(rawRefRows);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        createTable(db, "refs", FIELDS_2);
        addRow(db, "table", tableRows[0]);
        addRow(db, "table", tableRows[1]);
        addRow(db, "refs", refRows[0]);
        addRow(db, "refs", refRows[1]);
        addRow(db, "refs", refRows[2]);
        removeRow2(db, "table", new IntegerValue(1));

        // mirror update
        Object[][] nRawTableRows = { { 0, "Hello WOrld" } };
        Value[][] nTableRows = toValues(nRawTableRows);
        Object[][] nRawRefRows = { { 0, new ReferenceValue("table", 0) },
                { 2, new ReferenceValue("table", 0) } };
        Value[][] nRefRows = toValues(nRawRefRows);

        checkTable(db, "table", nTableRows);
        checkTable(db, "refs", nRefRows);
    }

    @Test
    public void test26_CascadingDelete() {
        Object[][] rawTableRows = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Object[][] rawRefRows = { { 0, new ReferenceValue("table", 0) } };
        Value[][] tableRows = toValues(rawTableRows);
        Value[][] refRows = toValues(rawRefRows);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        createTable(db, "refs", FIELDS_2);
        addRow(db, "table", tableRows[0]);
        addRow(db, "table", tableRows[1]);
        addRow(db, "refs", refRows[0]);
        // removeRow2(db, "table", new IntegerValue(0));
        db.deleteTable("table");
        // mirror update
        Object[][] nRawTableRows = { {} };
        Value[][] nTableRows = toValues(nRawTableRows);
        Object[][] nRawRefRows = {};
        Value[][] nRefRows = toValues(nRawRefRows);

        checkTable(db, "refs", nRefRows);
    }

    @Test
    public void test27_CascadingDelete() {
        Object[][] rawTableRows = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Object[][] rawRefRows = { { 0, new ReferenceValue("table", 0) },
                { 1, new ReferenceValue("table", 1) }, { 2, new ReferenceValue("table", 0) } };
        Object[][] rawRefRows2 = { { 0, new ReferenceValue("refs", 1) },
                { 1, new ReferenceValue("refs", 0) } };
        Value[][] tableRows = toValues(rawTableRows);
        Value[][] refRows = toValues(rawRefRows);
        Value[][] refRows2 = toValues(rawRefRows2);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_1);
        createTable(db, "refs", FIELDS_2);
        createTable(db, "refs2", FIELDS_2);
        addRow(db, "table", tableRows[0]);
        addRow(db, "table", tableRows[1]);
        addRow(db, "refs", refRows[0]);
        addRow(db, "refs", refRows[1]);
        addRow(db, "refs", refRows[2]);
        addRow(db, "refs2", refRows2[0]);
        addRow(db, "refs2", refRows2[1]);
        removeRow2(db, "table", new IntegerValue(1));

        // mirror update
        Object[][] nRawTableRows = { { 0, "Hello WOrld" } };
        Value[][] nTableRows = toValues(nRawTableRows);
        Object[][] nRawRefRows = { { 0, new ReferenceValue("table", 0) },
                { 2, new ReferenceValue("table", 0) } };
        Value[][] nRefRows = toValues(nRawRefRows);
        Object[][] nRawRefRows2 = { { 1, new ReferenceValue("refs", 0) } };
        Value[][] nRefRows2 = toValues(nRawRefRows2);

        checkTable(db, "table", nTableRows);
        checkTable(db, "refs", nRefRows);
        checkTable(db, "refs2", nRefRows2);
    }

    @Test
    public void test28_SetRow() {
        Field[] FIELDS_3 = { new Field("ID", Field.Type.INTEGER, false),
                new Field("TEXT", Field.Type.TEXT, true) };

        Object[][] rawData = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Value[][] rows = toValues(rawData);

        // key here is string, eg "Blah"
        Database db = createDatabase();
        createTable(db, "table", FIELDS_3);
        addRow(db, "table", rows[0]);
        addRow(db, "table", rows[1]);
        modifyRow2(db, "table", 0, 45, "Hello WOrld");

        rows[0][0] = new IntegerValue(45); // mirror update

        checkTable(db, "table", rows);
    }

    @Test
    public void test29_CascadingDelete() {
        Field[] FIELDS_4 = { new Field("ID", Field.Type.INTEGER, false),
                new Field("TEXT", Field.Type.TEXT, true) };

        Object[][] rawTableRows = { { 0, "Hello WOrld" }, { 1, "Blah" } };
        Object[][] rawRefRows = { { 0, new ReferenceValue("table", "Hello WOrld") },
                { 1, new ReferenceValue("table", "Blah") },
                { 2, new ReferenceValue("table", "Hello WOrld") } };
        Object[][] rawRefRows2 = { { 0, new ReferenceValue("refs", 1) },
                { 1, new ReferenceValue("refs", 0) } };
        Value[][] tableRows = toValues(rawTableRows);
        Value[][] refRows = toValues(rawRefRows);
        Value[][] refRows2 = toValues(rawRefRows2);

        // key here is string, eg "Blah"
        Database db = createDatabase();
        createTable(db, "table", FIELDS_4);
        createTable(db, "refs", FIELDS_2);
        createTable(db, "refs2", FIELDS_2);
        addRow(db, "table", tableRows[0]);
        addRow(db, "table", tableRows[1]);
        addRow(db, "refs", refRows[0]);
        addRow(db, "refs", refRows[1]);
        addRow(db, "refs", refRows[2]);
        addRow(db, "refs2", refRows2[0]);
        addRow(db, "refs2", refRows2[1]);
        removeRow2(db, "table", new StringValue("Blah"));

        // mirror update
        Object[][] nRawTableRows = { { 0, "Hello WOrld" } };
        Value[][] nTableRows = toValues(nRawTableRows);
        Object[][] nRawRefRows = { { 0, new ReferenceValue("table", "Hello WOrld") },
                { 2, new ReferenceValue("table", "Hello WOrld") } };
        Value[][] nRefRows = toValues(nRawRefRows);
        Object[][] nRawRefRows2 = { { 1, new ReferenceValue("refs", 0) } };
        Value[][] nRefRows2 = toValues(nRawRefRows2);

        checkTable(db, "table", nTableRows);
        checkTable(db, "refs", nRefRows);
        checkTable(db, "refs2", nRefRows2);
    }

    // Tests from Jeremy
    @Test
    public void test30_Unkown() {

        // Tests from Jeremy
        Field[] FIELDS_ONE = { new Field("ID", Field.Type.INTEGER, true),
                new Field("TEXT", Field.Type.TEXTAREA, true), new Field("BOOLEAN", Field.Type.BOOLEAN, false),
                new Field("TEXT", Field.Type.TEXT, false) };
        Field[] FIELDS_TWO = { new Field("ID", Field.Type.INTEGER, true),
                new Field("TEXT", Field.Type.TEXTAREA, true) };
        Field[] FIELDS_THREE = { new Field("ID", Field.Type.INTEGER, true),
                new Field("TEXT", Field.Type.TEXT, true), new Field("REF", "table", false) };

        Object[][] rawData = { { 0, "Hello WOrld\n", true, "something" },
                { 1, "Blah", false, "something else" } };
        Object[][] rawData2 = { { 0, "Hello Sir" }, { 0, "Blah" } };
        Object[][] rawData3 = { { 0, "Hello Friend", new ReferenceValue("table2", 0) } };
        // Object[][] rawData4 = {{0, "Hello Friend", new ReferenceValue("table",0)}, {0, "Blah",
        // new ReferenceValue("table2",0)}};

        Value[][] rows = toValues(rawData);
        Value[][] rows2 = toValues(rawData2);
        Value[][] rows3 = toValues(rawData3);
        // Value[][] rows4 = toValues(rawData4);

        Database db = createDatabase();
        // createTable(db, "table4", FIELDS_THREE);

        // creating the first table should pass because it has a textarea field and not a text field
        createTable(db, "table", FIELDS_ONE);
        addRow(db, "table", rows[0]);
        addRow(db, "table", rows[1]);

        checkTable(db, "table", rows);

        // this should pass as there are two key values in the schema and there is at least one
        // difference between them
        createTable(db, "table2", FIELDS_TWO);
        addRow(db, "table2", rows2[0]);
        addRow(db, "table2", rows2[1]);

        checkTable(db, "table2", rows2);

        // now this one should fail because the reference value is only ever referencing one of the
        // key values
        // in order for this to pass it would need to specify the exact key values in table2
        createTable(db, "table3", FIELDS_THREE);
        try {
            addRow(db, "table3", rows3[0]);
            fail("invalid Reference");
        } catch (InvalidOperation e) {

        }
    }

    @Test
    public void test31_Unkown() {

        Field[] FIELDS_FIVE = { new Field("ID", Field.Type.INTEGER, true),
                new Field("TEXT", Field.Type.TEXTAREA, true), new Field("BOOLEAN", Field.Type.BOOLEAN, true),
                new Field("TEXT", Field.Type.TEXT, false) };

        Object[][] rawData = { { 0, "Hello WOrld\n", true, "something" },
                { 0, "Blah", false, "something else" } };

        Value[][] rows = toValues(rawData);

        Database db = createDatabase();
        createTable(db, "table", FIELDS_FIVE);

        // add two rows, then delete the second row with three fields as key.
        addRow(db, "table", rows[0]);
        addRow(db, "table", rows[1]);
        removeRow2(db, "table", 0, "Blah", false);
        Value[][] nrows = toValues(new Object[][] { { 0, "Hello WOrld\n", true, "something" } });
        checkTable(db, "table", nrows);
    }

    // My experimenting field
    @Test
    public void test32_Extra() {
        Field[] FIELDS_1 = { new Field("ID", Field.Type.INTEGER, true),
                new Field("TEXT", Field.Type.TEXT, false) };
        Field[] FIELDS_2 = { new Field("ID", Field.Type.INTEGER, true), new Field("REF", "table", false) };
        Field[] FIELDS_3 = { new Field("ID", Field.Type.INTEGER, true), new Field("REF", "table", false) };

        Database db = createDatabase();

        Object[][] rawData_1 = { { 1, "Hello" }, { 2, "World" } };
        Value[][] rows_1 = toValues(rawData_1);
        createTable(db, "table", FIELDS_1);

        Object[][] rawData_2 = { { 1, new ReferenceValue("table", 1) },
                { 2, new ReferenceValue("table", 2) } };
        Value[][] rows_2 = toValues(rawData_2);
        createTable(db, "refs", FIELDS_2);

        Object[][] rawData_3 = { { 1, new ReferenceValue("refs", 1) }, { 2, new ReferenceValue("refs", 2) } };
        Value[][] rows_3 = toValues(rawData_3);
        createTable(db, "refs2", FIELDS_3);

        addRow(db, "table", rows_1[0]);
        addRow(db, "table", rows_1[1]);

        addRow(db, "refs", rows_2[0]);
        addRow(db, "refs", rows_2[1]);

        addRow(db, "refs2", rows_3[0]);
        addRow(db, "refs2", rows_3[1]);

        // removeRow1(db, "table", 0);
        // removeRow2(db, "table", 1);
        db.deleteTable("table");

        // Object[][] rawData_1_after = { { 2, "World" } };
        // Value[][] rows_1_after = toValues(rawData_1_after);
        // checkTable(db, "table", rows_1_after);

        assertTrue(db.table("table") == null);

        Object[][] rawData_2_after = {};
        Value[][] rows_2_after = toValues(rawData_2_after);
        checkTable(db, "refs", rows_2_after);

        Object[][] rawData_3_after = {};
        Value[][] rows_3_after = toValues(rawData_3_after);
        checkTable(db, "refs2", rows_3_after);

    }

    // =====================================================================================================
    // Helper Methods!
    // =====================================================================================================

    /**
     * Check that a given table matches the given set of values.
     */
    public static void checkTable(Database db, String table, Value[][] values) {
        Table dbtab = db.table(table);

        List<List<Value>> dbrows = dbtab.rows();

        if (dbrows.size() != values.length) {
            fail("Table " + table + " has " + dbrows.size() + " rows, should have " + values.length);
        }

        for (int i = 0; i != values.length; ++i) {
            List<Value> dbrow = dbrows.get(i);
            Value[] valuerow = values[i];
            if (dbrow.size() != valuerow.length) {
                fail("Table " + table + ", row " + i + " has " + dbrow.size() + " entries, should have "
                        + valuerow.length);
            }
            for (int j = 0; j != dbrow.size(); ++j) {
                Value dbv = dbrow.get(j);
                Value ddbv = valuerow[j];
                if (dbv == null || !dbv.equals(ddbv)) {
                    fail("Table " + table + ", row " + i + ", entry " + j + " is " + dbv + ", should be "
                            + ddbv);
                }
            }
        }
    }

    /**
     * Create a new database.
     * 
     * @return
     */
    private static Database createDatabase() {
        System.out.println("> CREATING DATABASE");
        return new MyDatabase();
    }

    /**
     * Create a table in the given database with the given name containing the given fields.
     * 
     * @param db
     *            --- database to create table in.
     * @param name
     *            --- name of table to create.
     * @param fields
     *            --- fields to be created in table.
     */
    private static void createTable(Database db, String name, Field... fields) {
        String r = "";
        ArrayList<Field> fs = new ArrayList<Field>();
        boolean firstTime = true;
        for (Field f : fields) {
            fs.add(f);
            if (!firstTime) {
                r += ", ";
            }
            firstTime = false;
            r += "\"" + f.title() + "\"" + (f.isKey() ? "*:" : ":") + f.type();
        }
        System.out.println("> CREATING TABLE: NAME=\"" + name + "\", SCHEMA=[" + r + "]");
        db.createTable(name, fs);
    }

    /**
     * Add a given row to a given table in a given database.
     * 
     * @param db
     * @param table
     * @param fields
     */
    private static void addRow(Database db, String table, Object... fields) {
        System.out.println("> ADDING ROW TO \"" + table + "\": " + Arrays.toString(fields));
        ArrayList<Value> vals = new ArrayList<Value>();
        for (Object o : fields) {
            vals.add(toValue(o));
        }
        db.table(table).rows().add(vals);
    }

    /**
     * Remove a given row from the database table at a given index.
     * 
     * @param log
     * @param db
     * @param ddb
     * @param table
     * @param index
     */
    public static void removeRow1(Database db, String table, int index) {
        System.out.println("> REMOVING ROW AT INDEX " + index + " FROM \"" + table + "\"");
        db.table(table).rows().remove(index);
    }

    /**
     * Remove a given row from the database table which matches the given keys.
     * 
     * @param log
     * @param db
     * @param ddb
     * @param table
     * @param index
     */
    public static void removeRow2(Database db, String table, Object... keys) {
        System.out.println("> REMOVING ROW FROM \"" + table + "\" WITH KEYS " + Arrays.toString(keys));
        db.table(table).delete(toValues(keys));
    }

    /**
     * Update a given row from the database table at the given index
     * 
     * @param log
     * @param db
     * @param ddb
     * @param table
     * @param index
     */
    public static void modifyRow1(Database db, String table, int index, int entry, Object value) {
        System.out.println(
                "> MODIFYING ROW AT INDEX " + index + ", " + entry + " IN \"" + table + "\" : " + value);
        Value val = toValue(value);
        db.table(table).rows().get(index).set(entry, val);

    }

    /**
     * Remove a given row from the database table which matches the given keys.
     * 
     * @param log
     * @param db
     * @param ddb
     * @param table
     * @param index
     */
    public static void modifyRow2(Database db, String table, int entry, Object value, Object... keys) {
        System.out.println("> MODIFYING ROW IN \"" + table + "\" WITH KEYS " + Arrays.toString(keys) + " : "
                + entry + " => " + value);
        Value[] vals = toValues(keys);
        Value val = toValue(value);
        db.table(table).row(vals).set(entry, val);
    }

    /**
     * Convert a 2d array of objects into a 2d array of Value objects. The reason for this method is
     * that it simplifies the construction of test databases.
     * 
     * @param rawData
     * @return
     */
    private static Value[][] toValues(Object[][] rawData) {
        Value[][] values = new Value[rawData.length][];

        for (int i = 0; i != values.length; ++i) {
            values[i] = toValues(rawData[i]);
        }

        return values;
    }

    /**
     * Convert a 1d array of objects into a 1d array of Value objects. The reason for this method is
     * that it simplifies the construction of test databases.
     * 
     * @param rawData
     * @return
     */
    public static Value[] toValues(Object[] obs) {
        Value[] vals = new Value[obs.length];
        for (int i = 0; i != obs.length; ++i) {
            vals[i] = toValue(obs[i]);
        }
        return vals;
    }

    /**
     * Turn a given arbitrary object into the corresponding instance of Value.
     * 
     * @param o
     * @return
     */
    public static Value toValue(Object o) {
        if (o instanceof Value) {
            return (Value) o;
        } else if (o instanceof Integer) {
            Integer i = (Integer) o;
            return new IntegerValue(i);
        } else if (o instanceof Boolean) {
            Boolean i = (Boolean) o;
            return new BooleanValue(i);
        } else if (o instanceof String) {
            String s = (String) o;
            return new StringValue(s);
        } else {
            return null;
        }
    }

}
