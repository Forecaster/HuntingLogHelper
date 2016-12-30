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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        Log.e("Hunting Log", "Selected " + id);
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
            }
            return super.onOptionsItemSelected(item);
        }
    }

    private void displayResultList() {
        ListView listView = (ListView) findViewById(R.id.loglist);
        //listView.addHeaderView(tView);

        //listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results));
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, results));
        listView.setTextFilterEnabled(true);
        listView.setItemsCanFocus(false);
        // we want multiple clicks
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

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
                        int done = c.getInt(c.getColumnIndex("done"));
                        results.add("Rank: " + rank + " Area: " + region + " - " + area + "\n" +
                        enemy + " X" + num + "\n" +
                        "Location X:" + x_loc + " Y:" + y_loc);
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
