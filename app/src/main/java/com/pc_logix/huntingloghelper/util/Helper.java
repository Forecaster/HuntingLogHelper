package com.pc_logix.huntingloghelper.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Michi on 1/2/2017.
 */

public class Helper {
    public static String getCompletionAmount(String classIn, Context contextIn, String tableName) {
        DBHelper dbHelper = new DBHelper(contextIn);
        SQLiteDatabase newDB = dbHelper.getWritableDatabase();
        int total = 0;
        int done = 0;
        Cursor c;
        c = newDB.rawQuery("SELECT * FROM " +  tableName +
                " where class='" + classIn + "'", null);
        if (c != null ) {
            if (c.moveToFirst()) {
                do {
                    total++;
                    if (c.getInt(c.getColumnIndex("done")) == 1) {
                        done++;
                    }
                } while (c.moveToNext());
            }
        }
        if (total > 0) {
            int percent = (done * 100) / total;
            return classIn + " " + Integer.toString(percent) +"%";
        } else {
            return classIn + " " + Integer.toString(0) +"%";
        }
    }
}
