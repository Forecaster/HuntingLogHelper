package com.pc_logix.huntingloghelper;

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
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class LogViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    protected static String myClass;
    private ArrayList<String> results = new ArrayList<String>();
    private ArrayList<Integer> done = new ArrayList<Integer>();
    private ArrayList<String> ranks = new ArrayList<String>();
    private LinkedHashMap<Integer, Integer> ids = new LinkedHashMap<Integer, Integer>();
    private String selectedRank;
    protected static String tableName = DBHelper.huntingLogsTable;
    protected static SQLiteDatabase newDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //myClass = MainActivity.myClass;
        this.setTitle(getCompletionAmount(myClass));
        selectedRank = "all";
        setupActionBar();
        setContentView(R.layout.activity_log_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        openAndQueryDatabase(selectedRank);
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
        menu.findItem(R.id.action_Arcanist).setTitle(getCompletionAmount("Arcanist"));
        menu.findItem(R.id.action_Archer).setTitle(getCompletionAmount("Archer"));
        menu.findItem(R.id.action_Conjurer).setTitle(getCompletionAmount("Conjurer"));
        menu.findItem(R.id.action_Gladiator).setTitle(getCompletionAmount("Gladiator"));
        menu.findItem(R.id.action_Lancer).setTitle(getCompletionAmount("Lancer"));
        menu.findItem(R.id.action_Marauder).setTitle(getCompletionAmount("Marauder"));
        menu.findItem(R.id.action_Pugilist).setTitle(getCompletionAmount("Pugilist"));
        menu.findItem(R.id.action_Rogue).setTitle(getCompletionAmount("Rogue"));
        menu.findItem(R.id.action_Thaumaturge).setTitle(getCompletionAmount("Thaumaturge"));
        menu.findItem(R.id.action_Immortal_Flames).setTitle(getCompletionAmount("Immortal Flames"));
        menu.findItem(R.id.action_Maelstrom).setTitle(getCompletionAmount("Maelstrom"));
        menu.findItem(R.id.action_Twin_Adder).setTitle(getCompletionAmount("Order of the Twin Adder"));
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            menu.findItem(R.id.action_Arcanist).setTitle(getCompletionAmount("Arcanist"));
            menu.findItem(R.id.action_Archer).setTitle(getCompletionAmount("Archer"));
            menu.findItem(R.id.action_Conjurer).setTitle(getCompletionAmount("Conjurer"));
            menu.findItem(R.id.action_Gladiator).setTitle(getCompletionAmount("Gladiator"));
            menu.findItem(R.id.action_Lancer).setTitle(getCompletionAmount("Lancer"));
            menu.findItem(R.id.action_Marauder).setTitle(getCompletionAmount("Marauder"));
            menu.findItem(R.id.action_Pugilist).setTitle(getCompletionAmount("Pugilist"));
            menu.findItem(R.id.action_Rogue).setTitle(getCompletionAmount("Rogue"));
            menu.findItem(R.id.action_Thaumaturge).setTitle(getCompletionAmount("Thaumaturge"));
            menu.findItem(R.id.action_Immortal_Flames).setTitle(getCompletionAmount("Immortal Flames"));
            menu.findItem(R.id.action_Maelstrom).setTitle(getCompletionAmount("Maelstrom"));
            menu.findItem(R.id.action_Twin_Adder).setTitle(getCompletionAmount("Order of the Twin Adder"));
        }
        return super.onMenuOpened(featureId, menu);
    }

    public String getCompletionAmount(String classIn) {
        DBHelper dbHelper = new DBHelper(this.getApplicationContext());
        newDB = dbHelper.getWritableDatabase();
        int total = 0;
        int done = 0;
        Cursor c;
            c = newDB.rawQuery("SELECT * FROM " +  tableName +
                    " where class='" + classIn + "'", null);
            Log.e("Hunting Helper", "SELECT * FROM " +  tableName +
                    " where class='" + classIn + "'");
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
        int percent = (done * 100) / total;
        return classIn + " " + Integer.toString(percent) +"%";
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == 16908332) {
            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(myIntent, 0);
            //this.finish();
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
        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        spinner.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ranks));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            Boolean canRun = false;
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long id) {
                if (canRun == true) {
                    selectedRank = arg0.getSelectedItem().toString();
                    Log.e("Hunting Log", "Selected rank " + selectedRank);
                    openAndQueryDatabase(selectedRank.toLowerCase());
                    displayResultList();
                    arg0.setSelection(position);
                } else {
                    canRun = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });
        ListView listView = (ListView) findViewById(R.id.loglist);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.id.checkedTextView1, results));
        listView.setTextFilterEnabled(true);
        listView.setItemsCanFocus(false);
        listView.setOnItemClickListener(this);
        // we want multiple clicks
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        for (Integer pos : done) {
            listView.setItemChecked(pos, true);
        }
    }

    private void openAndQueryDatabase(String rankIn) {
        Log.e("Hunting Log", rankIn);
        int loop = 0;
        results.clear();
        done.clear();
        ids.clear();

        ListView listView = (ListView) findViewById(R.id.loglist);
        listView.setAdapter(null);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.id.checkedTextView1, results));
        listView.setTextFilterEnabled(true);
        listView.setItemsCanFocus(false);
        listView.setOnItemClickListener(this);
        // we want multiple clicks
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        spinner.setAdapter(null);
        ranks.clear();
        spinner.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ranks));
        try {
            DBHelper dbHelper = new DBHelper(this.getApplicationContext());
            newDB = dbHelper.getWritableDatabase();
            Cursor c;
            if (rankIn.toLowerCase().equals("all")) {
                c = newDB.rawQuery("SELECT * FROM " +  tableName +
                        " where class='" + myClass + "' ORDER BY rank, region, area, y_loc, x_loc", null);
                Log.e("Hunting Helper", "SELECT * FROM " +  tableName +
                        " where class='" + myClass + "' ORDER BY rank, region, area, y_loc, x_loc");
            } else {
                c = newDB.rawQuery("SELECT * FROM " + tableName +
                        " where class='" + myClass + "' AND rank='" + rankIn + "' ORDER BY rank, region, area, y_loc, x_loc", null);
                Log.e("Hunting Helper", "SELECT * FROM " + tableName +
                        " where class='" + myClass + "' AND rank='" + rankIn + "' ORDER BY rank, region, area, y_loc, x_loc");
            }
            Cursor c2 = newDB.rawQuery("SELECT * FROM " + tableName + " where class='" + myClass + "' GROUP BY rank", null);
            ranks.add("All");
            if (c2 != null) {
                if (c2.moveToFirst()) {
                    do {
                        ranks.add(Integer.toString(c2.getInt(c.getColumnIndex("rank"))));
                    }while (c2.moveToNext());
                }
            }
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
        CheckedTextView ctv = (CheckedTextView)view.findViewById(R.id.checkedTextView1);
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
        this.setTitle(getCompletionAmount(myClass));
    }
}
