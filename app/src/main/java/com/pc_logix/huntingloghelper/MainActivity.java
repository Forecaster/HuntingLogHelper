package com.pc_logix.huntingloghelper;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.pc_logix.huntingloghelper.LogViews.CraftingLogViewActivity;
import com.pc_logix.huntingloghelper.LogViews.HuntingLogViewActivity;
import com.pc_logix.huntingloghelper.util.DBHelper;
import com.pc_logix.huntingloghelper.util.Helper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class MainActivity extends AppCompatActivity {


    public static String myClass;
    public static DBHelper dbHelper;
    protected static String tableName = DBHelper.huntingLogsTable;
    protected static SQLiteDatabase newDB;
    private AdView mAdView;
    public static final String PREFS_NAME = "HuntingHelperPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean showAd = settings.getBoolean("showAds", true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new DBHelper(this.getApplicationContext());
        newDB = dbHelper.getWritableDatabase();

        if(Helper.isTableExists("logs", true, this.getApplicationContext())) {
            Log.e("Hunting Log", "Attempting to transfer data");
            Toast.makeText(this.getApplicationContext(),"Attempting to transfer progress", Toast.LENGTH_LONG).show();
            Cursor c = newDB.rawQuery("SELECT _id, done FROM logs", null);
            if (c != null ) {
                if (c.moveToFirst()) {
                    do {
                        int isDone = c.getInt(c.getColumnIndex("done"));
                        int id = c.getInt(c.getColumnIndex("_id"));
                        if (isDone == 1) {
                            ContentValues data=new ContentValues();
                            data.put("done",1);
                            newDB.update("hunting_logs", data, "_id=" + id, null);
                        }
                    }while (c.moveToNext());
                }
            }
            newDB.execSQL("DROP TABLE logs");
        }


        TextView t=(TextView)findViewById(R.id.content);
        t.setText(getResources().getText(R.string.welcome_text));
        if (showAd) {
            mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder()
                    .build();
            mAdView.loadAd(adRequest);
        }

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
            Intent myIntent = new Intent(this, SettingsActivity.class);
            startActivity(myIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }




}
