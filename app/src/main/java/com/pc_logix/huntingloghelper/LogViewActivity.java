package com.pc_logix.huntingloghelper;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class LogViewActivity extends AppCompatActivity  {

    public static String myClass;
    private ArrayList<String> results = new ArrayList<String>();
    private String tableName = DBHelper.tableName;
    private SQLiteDatabase newDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //myClass = MainActivity.myClass;
        this.setTitle(myClass);
        setupActionBar();
        setContentView(R.layout.activity_log_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        openAndQueryDatabase();
        displayResultList();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }

    private void displayResultList() {
        ListView listView = (ListView) findViewById(R.id.loglist);
        //listView.addHeaderView(tView);

        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, results));
        listView.setTextFilterEnabled(true);

    }
    private void openAndQueryDatabase() {
        try {
            DBHelper dbHelper = new DBHelper(this.getApplicationContext());
            newDB = dbHelper.getWritableDatabase();
            Cursor c = newDB.rawQuery("SELECT * FROM " +
                    tableName +
                    " where class='" + myClass + "' ORDER BY rank, region, area, y_loc, x_loc", null);
            if (c != null ) {
                if (c.moveToFirst()) {
                    do {
                        String region = c.getString(c.getColumnIndex("region"));
                        String area = c.getString(c.getColumnIndex("area"));
                        String enemy = c.getString(c.getColumnIndex("enemy"));
                        int rank = c.getInt(c.getColumnIndex("rank"));
                        int num = c.getInt(c.getColumnIndex("num"));
                        int x_loc = c.getInt(c.getColumnIndex("x_loc"));
                        int y_loc = c.getInt(c.getColumnIndex("y_loc"));
                        results.add("Rank: " + rank + " Area: " + region + " - " + area + "\n" +
                        enemy + " X" + num + "\n" +
                        "Location: X" + x_loc + " Y:" + y_loc);
                    }while (c.moveToNext());
                } else {
                    results.add("No data");
                }
            }
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } finally {
            newDB.close();
        }

    }
}
