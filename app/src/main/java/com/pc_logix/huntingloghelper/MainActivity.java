package com.pc_logix.huntingloghelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static String myClass;
    public static DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new DBHelper(this.getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent myIntent = new Intent(this, SettingsActivity.class);
            startActivity(myIntent);
        } else if (id == R.id.action_Gladiator) {
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
        } else if (id == R.id.action_Welcome) {
            TextView t=(TextView)findViewById(R.id.content);
            t.setText(getResources().getText(R.string.welcome_text));
        }
        return super.onOptionsItemSelected(item);
    }
}
