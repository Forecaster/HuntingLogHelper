package com.pc_logix.huntingloghelper.LogViews;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Spinner;

import com.pc_logix.huntingloghelper.util.DBHelper;
import com.pc_logix.huntingloghelper.MainActivity;
import com.pc_logix.huntingloghelper.MySettings;
import com.pc_logix.huntingloghelper.R;
import com.pc_logix.huntingloghelper.util.Helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CraftingLogViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static String myClass;
    private ArrayList<String> results = new ArrayList<String>();
    private ArrayList<Integer> done = new ArrayList<Integer>();
    private ArrayList<String> ranks = new ArrayList<String>();
    private LinkedHashMap<Integer, Integer> ids = new LinkedHashMap<Integer, Integer>();
    private String selectedRank;
    protected static String tableName = DBHelper.craftingLogsTable;
    protected static SQLiteDatabase newDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //myClass = MainActivity.myClass;
        this.setTitle(Helper.getCompletionAmount(myClass,this.getApplicationContext(),tableName));
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
        getMenuInflater().inflate(R.menu.crafting_log_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            menu.findItem(R.id.action_Alchemist).setTitle(Helper.getCompletionAmount("Alchemist",this.getApplicationContext(),tableName));
            menu.findItem(R.id.action_Armorer).setTitle(Helper.getCompletionAmount("Armorer",this.getApplicationContext(),tableName));
            menu.findItem(R.id.action_Blacksmith).setTitle(Helper.getCompletionAmount("Blacksmith",this.getApplicationContext(),tableName));
            menu.findItem(R.id.action_Carpenter).setTitle(Helper.getCompletionAmount("Carpenter",this.getApplicationContext(),tableName));
            menu.findItem(R.id.action_Culinarian).setTitle(Helper.getCompletionAmount("Culinarian",this.getApplicationContext(),tableName));
            menu.findItem(R.id.action_Goldsmith).setTitle(Helper.getCompletionAmount("Goldsmith",this.getApplicationContext(),tableName));
            menu.findItem(R.id.action_Leathercraft).setTitle(Helper.getCompletionAmount("Leathercraft",this.getApplicationContext(),tableName));
            menu.findItem(R.id.action_Weaver).setTitle(Helper.getCompletionAmount("Weaver",this.getApplicationContext(),tableName));
        }
        return super.onMenuOpened(featureId, menu);
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
            if (id == R.id.action_Alchemist) {
                CraftingLogViewActivity.myClass = "Alchemist";
                Intent myIntent = new Intent(this, CraftingLogViewActivity.class);
                startActivity(myIntent);
            } else if (id == R.id.action_Armorer) {
                CraftingLogViewActivity.myClass = "Armorer";
                Intent myIntent = new Intent(this, CraftingLogViewActivity.class);
                startActivity(myIntent);
            } else if (id == R.id.action_Blacksmith) {
                CraftingLogViewActivity.myClass = "Blacksmith";
                Intent myIntent = new Intent(this, CraftingLogViewActivity.class);
                startActivity(myIntent);
            } else if (id == R.id.action_Carpenter) {
                CraftingLogViewActivity.myClass = "Carpenter";
                Intent myIntent = new Intent(this, CraftingLogViewActivity.class);
                startActivity(myIntent);
            } else if (id == R.id.action_Culinarian) {
                CraftingLogViewActivity.myClass = "Culinarian";
                Intent myIntent = new Intent(this, CraftingLogViewActivity.class);
                startActivity(myIntent);
            } else if (id == R.id.action_Goldsmith) {
                CraftingLogViewActivity.myClass = "Goldsmith";
                Intent myIntent = new Intent(this, CraftingLogViewActivity.class);
                startActivity(myIntent);
            } else if (id == R.id.action_Leathercraft) {
                CraftingLogViewActivity.myClass = "Leathercraft";
                Intent myIntent = new Intent(this, CraftingLogViewActivity.class);
                startActivity(myIntent);
            } else if (id == R.id.action_Weaver) {
                CraftingLogViewActivity.myClass = "Weaver";
                Intent myIntent = new Intent(this, CraftingLogViewActivity.class);
                startActivity(myIntent);
            } else if (id == R.id.action_settings) {
                Intent myIntent = new Intent(this, MySettings.class);
                startActivity(myIntent);
            } else if (id == R.id.action_markAll) {
                ListView listView = (ListView) findViewById(R.id.loglist);
                View v;
                DBHelper dbHelper = new DBHelper(this.getApplicationContext());
                newDB = dbHelper.getWritableDatabase();
                newDB.beginTransaction();
                for (int i = 0; i < listView.getCount(); i++) {
                    v = listView.getChildAt(i);
                    listView.setItemChecked(i, true);
                    Cursor cursor = newDB.rawQuery("UPDATE " +
                            CraftingLogViewActivity.tableName +
                            " SET done = 1 where class='" + myClass + "' AND _id = " + ids.get(i), null);
                    if (cursor != null) {
                        if (cursor.moveToFirst()) {

                        }
                        cursor.close();
                    }
                }
                newDB.setTransactionSuccessful();
                newDB.endTransaction();
                newDB.close();
                this.setTitle(Helper.getCompletionAmount(myClass,this.getApplicationContext(),tableName));
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
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.id.checkedTextView1, results) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null) {
                    LayoutInflater lInflater = LayoutInflater.from(CraftingLogViewActivity.this);
                    convertView = lInflater.inflate(R.layout.list_item, null);
                }
                CheckedTextView tv = (CheckedTextView) convertView.findViewById(R.id.checkedTextView1);
                tv.setText(Html.fromHtml(results.get(position)));
                if(position %2 == 1)
                    convertView.setBackgroundColor(Color.parseColor("#FFDCDCDC"));
                else
                    convertView.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                return convertView;
            }
        });
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
        int loop = 0;
        results.clear();
        done.clear();
        ids.clear();


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
            } else {
                c = newDB.rawQuery("SELECT * FROM " + tableName +
                        " where class='" + myClass + "' AND rank='" + rankIn + "' ORDER BY rank, region, area, y_loc, x_loc", null);
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
                        results.add(
                                "Rank: " + rank + "<br>" +
                                "Area: " + region + " - " + area + "<br>" +
                                "Enemy: <b>"+enemy+"</b>" + " X" + num + "<br>" +
                                "Location: X:" + x_loc + " Y:" + y_loc);
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
        CheckedTextView ctv = (CheckedTextView)view.findViewById(R.id.checkedTextView1);
        if(ctv.isChecked()) {
            newDB.beginTransaction();
            Cursor cursor = newDB.rawQuery("UPDATE " +
                    CraftingLogViewActivity.tableName +
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
                    CraftingLogViewActivity.tableName +
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
        this.setTitle(Helper.getCompletionAmount(myClass,this.getApplicationContext(),tableName));
    }
}