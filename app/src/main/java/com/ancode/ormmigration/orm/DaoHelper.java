package com.ancode.ormmigration.orm;

import android.content.Context;
import com.ancode.ormmigration.DataBaseProvider;
import com.ancode.ormmigration.model.DaoMaster;
import org.greenrobot.greendao.database.Database;

public class DaoHelper extends DaoMaster.OpenHelper {
    private static final String CREATE_PERSONS_TABLE = "create table persons (id integer primary key autoincrement, "
            + "name text not null);";


    private static final String CREATE_ADDRESSES_TABLE = "create table addresses (id integer primary key autoincrement, "
            + "id_person integer not null, address text not null);";

    public DaoHelper(Context context, String name) {
        super(context, name);
    }


    @Override
    public void onCreate(Database db) {
        super.onCreate(db);
        db.execSQL(CREATE_PERSONS_TABLE);
        db.execSQL(CREATE_ADDRESSES_TABLE);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", DataBaseProvider.DB_PERSONS_TABLE));
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseProvider.DB_ADDRESSES_TABLE);
        onCreate(db);
    }
}
