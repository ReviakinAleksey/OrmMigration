package com.ancode.ormmigration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseProvider {
    public static final int DATABASE_VERSION = 123;
    private static final String DB_NAME = "DB_ORM";

    private static final String CREATE_PERSONS_TABLE = "create table persons (id integer primary key autoincrement, "
            + "name text not null);";


    private static final String CREATE_ADDRESSES_TABLE = "create table addresses (id integer primary key autoincrement, "
            + "id_person integer not null, address text not null);";

    private static final String DB_PERSONS_TABLE = "persons";
    public static final String KEY_PERSON_ID = "id";
    public static final String KEY_PERSON_NAME = "name";

    private static final String DB_ADDRESSES_TABLE = "addresses";

    public static final String KEY_ADDRESS_ID = "id";
    public static final String KEY_ADDRESS_PERSON_ID = "id_person";
    public static final String KEY_ADDRESS_ADDR = "address";

    private final Context context;

    private DbHelper DBHelper;
    private SQLiteDatabase db;

    public DataBaseProvider(Context ctx) {
        this.context = ctx;
        DBHelper = new DbHelper(context);
    }

    // ---opens the database---
    public DataBaseProvider open() throws SQLException {
        try {
            db = DBHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    // ---closes the database---
    public void close() {
        DBHelper.close();
    }


    public ArrayList<Person> getPersons() {
        String sql = "select * from persons;";
        ArrayList<Person> obj = new ArrayList<>();
        Cursor c = db.query(DB_PERSONS_TABLE, new String[]{KEY_PERSON_ID, KEY_PERSON_NAME}, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            int num = c.getCount();

            for (int i = 0; i < num; i++) {
                Person person = new Person();
                person.id = c.getInt(0);
                person.name = c.getString(1);
                obj.add(person);
                c.moveToNext();
            }
        }
        return obj;
    }

    public List<Address> getAddresses(int personId) {
        List<Address> result = new ArrayList<>();
        try (Cursor c = db.query(DB_ADDRESSES_TABLE, new String[]{KEY_ADDRESS_ID, KEY_ADDRESS_PERSON_ID, KEY_ADDRESS_ADDR},
                KEY_ADDRESS_PERSON_ID + " = ?", new String[]{String.valueOf(personId)}, null, null, null)) {
            while (c.moveToNext()){
                Address address = new Address();
                address.id = c.getInt(0);
                address.personId = c.getInt(1);
                address.address = c.getString(2);
                result.add(address);
            }
        }
        return result;
    }

    public long insertPerson(String name) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PERSON_NAME, name);

        return db.insert(DB_PERSONS_TABLE, null, initialValues);
    }

    public long insertAddress(int personId, String address) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ADDRESS_PERSON_ID, personId);
        initialValues.put(KEY_ADDRESS_ADDR, address);

        return db.insert(DB_ADDRESSES_TABLE, null, initialValues);
    }


    public class DbHelper extends SQLiteOpenHelper {

        public DbHelper(@Nullable Context context) {
            super(context, DB_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_PERSONS_TABLE);
            db.execSQL(CREATE_ADDRESSES_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS CREATE_PERSONS_TABLE");
            db.execSQL("DROP TABLE IF EXISTS CREATE_ADDRESSES_TABLE");
            onCreate(db);
        }
    }

    public static class Person {
        public int id;
        public String name;

        @Override
        public String toString() {
            return name;
        }
    }


    public static class Address {
        public int id;
        public int personId;
        public String address;

        @Override
        public String toString() {
            return address;
        }
    }

}
