package com.pc_logix.huntingloghelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

/**
 * Created by Michi on 12/30/2016.
 */

public class MySettings extends AppCompatActivity {

    protected static SQLiteDatabase newDB;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                alertMessage();
            }
        });
        setupActionBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
                        Toast.makeText(MySettings.this, "Resetting",
                                Toast.LENGTH_LONG).show();
                        DBHelper dbHelper = new DBHelper(getApplicationContext());
                        newDB = dbHelper.getWritableDatabase();
                        newDB.beginTransaction();
                        Cursor cursor = newDB.rawQuery("UPDATE " +
                                LogViewActivity.tableName +
                                " SET done = 0", null);
                        if (cursor != null) {
                            if (cursor.moveToFirst()) {
                                Toast.makeText(MySettings.this, "Done",
                                        Toast.LENGTH_LONG).show();
                            }
                            cursor.close();
                        }
                        newDB.setTransactionSuccessful();
                        newDB.endTransaction();
                        newDB.close();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // No button clicked
                        // do nothing
                        Toast.makeText(MySettings.this, "Canceled",
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
