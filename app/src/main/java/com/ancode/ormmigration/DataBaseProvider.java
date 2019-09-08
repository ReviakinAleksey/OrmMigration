package com.ancode.ormmigration;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import com.ancode.ormmigration.model.Address;
import com.ancode.ormmigration.model.AddressDao;
import com.ancode.ormmigration.model.Person;
import com.ancode.ormmigration.model.PersonDao;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class DataBaseProvider {
    public static final int DATABASE_VERSION = 123;
    public static final String DB_NAME = "DB_ORM";

    public static final String DB_PERSONS_TABLE = "persons";
    public static final String KEY_PERSON_ID = "id";
    public static final String KEY_PERSON_NAME = "name";

    public static final String DB_ADDRESSES_TABLE = "addresses";

    public static final String KEY_ADDRESS_ID = "id";
    public static final String KEY_ADDRESS_PERSON_ID = "id_person";
    public static final String KEY_ADDRESS_ADDR = "address";

    private SQLiteDatabase db;
    private PersonDao personDao;
    private AddressDao addressDao;


    public DataBaseProvider(SQLiteDatabase db, PersonDao personDao, AddressDao addressDao) {
        this.db = db;
        this.personDao = personDao;
        this.addressDao = addressDao;
    }


    public List<Person> getPersons() {
        return personDao.loadAll();
    }

    public List<Address> getAddresses(long personId) {
        QueryBuilder<Address> qb = addressDao.queryBuilder();
        qb.where(AddressDao.Properties.PersonId.eq(personId));
        return qb.list();
    }

    public long insertPerson(String name) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PERSON_NAME, name);

        return db.insert(DB_PERSONS_TABLE, null, initialValues);
    }

    public long insertAddress(long personId, String address) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ADDRESS_PERSON_ID, personId);
        initialValues.put(KEY_ADDRESS_ADDR, address);

        return db.insert(DB_ADDRESSES_TABLE, null, initialValues);
    }


    public static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(@Nullable Context context) {
            super(context, DB_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
