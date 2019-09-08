package com.ancode.ormmigration;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.ancode.ormmigration.model.DaoMaster;
import com.ancode.ormmigration.model.DaoSession;
import com.ancode.ormmigration.orm.DaoHelper;
import org.greenrobot.greendao.query.QueryBuilder;

public class DaoApp extends Application implements Application.ActivityLifecycleCallbacks {

    private DaoHelper daoHelper;
    private DaoSession session;
    private SQLiteDatabase fallbackDb;

    @Override
    public void onCreate() {
        super.onCreate();
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
        registerActivityLifecycleCallbacks(this);
    }

    public DaoSession getSession() {
        return session;
    }

    public SQLiteDatabase getFallbackDb() {
        return fallbackDb;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        daoHelper = new DaoHelper(this, DataBaseProvider.DB_NAME);
        SQLiteDatabase database = daoHelper.getReadableDatabase();
        DaoMaster master = new DaoMaster(database);
        session = master.newSession();
        fallbackDb = (SQLiteDatabase) session.getDatabase().getRawDatabase();
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        session.clear();
        daoHelper.close();
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
