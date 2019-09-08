package com.ancode.ormmigration;

import android.database.sqlite.SQLiteDatabase;
import com.ancode.ormmigration.model.Address;
import com.ancode.ormmigration.model.AddressDao;
import com.ancode.ormmigration.model.Person;
import com.ancode.ormmigration.model.PersonDao;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class DataBaseProvider {
    public static final String DB_NAME = "DB_ORM";

    public static final String DB_PERSONS_TABLE = "persons";
    public static final String DB_ADDRESSES_TABLE = "addresses";

    private PersonDao personDao;
    private AddressDao addressDao;


    public DataBaseProvider(PersonDao personDao, AddressDao addressDao) {
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
        Person entity = new Person();
        entity.name = name;
        return personDao.insert(entity);
    }

    public long insertAddress(long personId, String address) {
        Address entity = new Address();
        entity.personId = personId;
        entity.address = address;
        return addressDao.insert(entity);
    }
}
