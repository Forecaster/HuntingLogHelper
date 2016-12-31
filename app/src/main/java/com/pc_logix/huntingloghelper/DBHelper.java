package com.pc_logix.huntingloghelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Michi on 12/27/2016.
 */

public class DBHelper extends SQLiteAssetHelper {

    public SQLiteDatabase DB;
    public String DBPath;
    public static String DBName = "huntinglogs.db";
    public static final int version = '2';
    public static Context currentContext;
    public static String tableName = "logs";

    public DBHelper(Context context) {
        super(context, DBName, null, version);
    }
}