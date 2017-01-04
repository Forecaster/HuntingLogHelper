package com.pc_logix.huntingloghelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.pc_logix.huntingloghelper.LogViews.CraftingLogViewActivity;
import com.pc_logix.huntingloghelper.LogViews.HuntingLogViewActivity;
import com.pc_logix.huntingloghelper.util.DBHelper;

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
