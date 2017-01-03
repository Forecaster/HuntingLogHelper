package com.pc_logix.huntingloghelper;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.pc_logix.huntingloghelper.LogViews.CraftingLogViewActivity;
import com.pc_logix.huntingloghelper.LogViews.HuntingLogViewActivity;
import com.pc_logix.huntingloghelper.util.DBHelper;

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
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_HuntingLogs) {
            HuntingLogViewActivity.myClass = "Arcanist";
            Intent myIntent = new Intent(this, HuntingLogViewActivity.class);
            startActivity(myIntent);
        } else if (id == R.id.action_CraftingLogs) {
            CraftingLogViewActivity.myClass = "Alchemist";
            Intent myIntent = new Intent(this, CraftingLogViewActivity.class);
            startActivity(myIntent);
        } else if (id == R.id.action_settings) {
            Intent myIntent = new Intent(this, MySettings.class);
            startActivity(myIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
