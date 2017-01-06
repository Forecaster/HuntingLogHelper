package com.pc_logix.huntingloghelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.pc_logix.huntingloghelper.util.DBHelper;


/**
 * Created by Michi on 12/30/2016.
 */

public class SettingsActivity extends AppCompatActivity {

    protected static SQLiteDatabase newDB;
    String strVersion;
    String strVersionCode;
    private Switch showAds;
    public static final String PREFS_NAME = "HuntingHelperPrefs";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PackageManager manager = this.getPackageManager();
        PackageInfo info;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
            strVersion = "Version: " + info.versionName;
            strVersionCode = " Build: "+ info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        setContentView(R.layout.settings_activity);
        Button btn = (Button) findViewById(R.id.button);
        TextView versionText = (TextView) findViewById(R.id.versionText);
        versionText.setText(strVersion + strVersionCode + " DB Version " + DBHelper.version);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                alertMessage();
            }
        });
        setupActionBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        showAds = (Switch) findViewById(R.id.showAds);
        //attach a listener to check for changes in state
        showAds.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("showAds", isChecked);
                editor.apply();
            }
        });
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean showAdsBool = settings.getBoolean("showAds", true);
        showAds.setChecked(showAdsBool);
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
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == 16908332) {
            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(myIntent, 0);
            return true;
        }
        return false;
    }
    public void alertMessage() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        // Yes button clicked
                        Toast.makeText(SettingsActivity.this, "Resetting",
                                Toast.LENGTH_LONG).show();
                        DBHelper dbHelper = new DBHelper(getApplicationContext());
                        newDB = dbHelper.getWritableDatabase();
                        newDB.beginTransaction();
                        Cursor cursor = newDB.rawQuery("UPDATE " +
                                DBHelper.huntingLogsTable +
                                " SET done = 0", null);
                        Cursor cursor2 = newDB.rawQuery("UPDATE " +
                                DBHelper.craftingLogsTable +
                                " SET done = 0", null);
                        if (cursor != null) {
                            if (cursor.moveToFirst()) {
                                Toast.makeText(SettingsActivity.this, "Hunting Logs reset done",
                                        Toast.LENGTH_LONG).show();
                            }
                            cursor.close();
                        }
                        if (cursor2 != null) {
                            if (cursor2.moveToFirst()) {
                                Toast.makeText(SettingsActivity.this, "Crafting Logs reset done",
                                        Toast.LENGTH_LONG).show();
                            }
                            cursor2.close();
                        }
                        newDB.setTransactionSuccessful();
                        newDB.endTransaction();
                        newDB.close();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // No button clicked
                        // do nothing
                        Toast.makeText(SettingsActivity.this, "Canceled",
                                Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}
