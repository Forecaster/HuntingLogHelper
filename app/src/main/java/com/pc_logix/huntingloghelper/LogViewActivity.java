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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class LogViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    protected static String myClass;
    private ArrayList<String> results = new ArrayList<String>();
    private ArrayList<Integer> done = new ArrayList<Integer>();
    private LinkedHashMap<Integer, Integer> ids = new LinkedHashMap<Integer, Integer>();
    protected static String tableName = DBHelper.tableName;
    protected static SQLiteDatabase newDB;
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == 16908332) {
            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(myIntent, 0);
            return true;
        } else {
            //noinspection SimplifiableIfStatement
            if (id == R.id.action_Gladiator) {
                LogViewActivity.myClass = "Gladiator";
                Intent myIntent = new Intent(this, LogViewActivity.class);
                startActivity(myIntent);
            } else if (id == R.id.action_Arcanist) {
                LogViewActivity.myClass = "Arcanist";
                Intent myIntent = new Intent(this, LogViewActivity.class);
                startActivity(myIntent);
            } else if (id == R.id.action_Archer) {
                LogViewActivity.myClass = "Archer";
                Intent myIntent = new Intent(this, LogViewActivity.class);
                startActivity(myIntent);
            } else if (id == R.id.action_Conjurer) {
                LogViewActivity.myClass = "Conjurer";
                Intent myIntent = new Intent(this, LogViewActivity.class);
                startActivity(myIntent);
            } else if (id == R.id.action_Immortal_Flames) {
                LogViewActivity.myClass = "Immortal Flames";
                Intent myIntent = new Intent(this, LogViewActivity.class);
                startActivity(myIntent);
            } else if (id == R.id.action_Lancer) {
                LogViewActivity.myClass = "Lancer";
                Intent myIntent = new Intent(this, LogViewActivity.class);
                startActivity(myIntent);
            } else if (id == R.id.action_Maelstrom) {
                LogViewActivity.myClass = "Maelstrom";
                Intent myIntent = new Intent(this, LogViewActivity.class);
                startActivity(myIntent);
            } else if (id == R.id.action_Marauder) {
                LogViewActivity.myClass = "Marauder";
                Intent myIntent = new Intent(this, LogViewActivity.class);
                startActivity(myIntent);
            } else if (id == R.id.action_Pugilist) {
                LogViewActivity.myClass = "Pugilist";
                Intent myIntent = new Intent(this, LogViewActivity.class);
                startActivity(myIntent);
            } else if (id == R.id.action_Rogue) {
                LogViewActivity.myClass = "Rogue";
                Intent myIntent = new Intent(this, LogViewActivity.class);
                startActivity(myIntent);
            } else if (id == R.id.action_Twin_Adder) {
                LogViewActivity.myClass = "Order of the Twin Adder";
                Intent myIntent = new Intent(this, LogViewActivity.class);
                startActivity(myIntent);
            } else if (id == R.id.action_Thaumaturge) {
                LogViewActivity.myClass = "Thaumaturge";
                Intent myIntent = new Intent(this, LogViewActivity.class);
                startActivity(myIntent);
            } else if (id == R.id.action_settings) {
                Intent myIntent = new Intent(this, MySettings.class);
                startActivity(myIntent);
            }
            return super.onOptionsItemSelected(item);
        }
    }

    private void displayResultList() {
        ListView listView = (ListView) findViewById(R.id.loglist);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, results));
        listView.setTextFilterEnabled(true);
        listView.setItemsCanFocus(false);
        listView.setOnItemClickListener(this);
        // we want multiple clicks
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        for (Integer pos : done) {
            listView.setItemChecked(pos, true);
        }
    }

    private void openAndQueryDatabase() {
        int loop = 0;
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
                        int id = c.getInt(c.getColumnIndex("_id"));
                        int rank = c.getInt(c.getColumnIndex("rank"));
                        int num = c.getInt(c.getColumnIndex("num"));
                        int x_loc = c.getInt(c.getColumnIndex("x_loc"));
                        int y_loc = c.getInt(c.getColumnIndex("y_loc"));
                        int isDone = c.getInt(c.getColumnIndex("done"));
                        ids.put(loop, id);
                        results.add("Rank: " + rank + "\nArea: " + region + " - " + area + "\n" +
                        enemy + " X" + num + "\n" +
                        "Location X:" + x_loc + " Y:" + y_loc);
                        if (isDone == 1)
                            done.add(loop);
                        loop++;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DBHelper dbHelper = new DBHelper(this.getApplicationContext());
        newDB = dbHelper.getWritableDatabase();
        Log.e("Hunting Log", "ID: " + ids.get(position));
        CheckedTextView ctv = (CheckedTextView)view;
        if(ctv.isChecked()) {
            newDB.beginTransaction();
            Cursor cursor = newDB.rawQuery("UPDATE " +
                    LogViewActivity.tableName +
                    " SET done = 1 where class='" + myClass + "' AND _id = " + ids.get(position), null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {

                }
                cursor.close();
            }
            newDB.setTransactionSuccessful();
            newDB.endTransaction();
            newDB.close();
        } else {
            newDB.beginTransaction();
            Cursor cursor = newDB.rawQuery("UPDATE " +
                    LogViewActivity.tableName +
                    " SET done = 0 where class='" + myClass + "' AND _id = " + ids.get(position), null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {

                }
                cursor.close();
            }
            newDB.setTransactionSuccessful();
            newDB.endTransaction();
            newDB.close();
        }
    }
}
