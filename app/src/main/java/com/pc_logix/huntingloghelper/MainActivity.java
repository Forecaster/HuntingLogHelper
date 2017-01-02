package com.pc_logix.huntingloghelper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static String myClass;
    public static DBHelper dbHelper;
    protected static String tableName = DBHelper.huntingLogsTable;
    protected static SQLiteDatabase newDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new DBHelper(this.getApplicationContext());

        TextView t=(TextView)findViewById(R.id.content);
        t.setText(getResources().getText(R.string.welcome_text));
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
        menu.findItem(R.id.action_markAll).setEnabled(false);
        return true;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

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
