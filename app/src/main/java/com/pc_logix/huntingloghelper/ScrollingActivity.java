package com.pc_logix.huntingloghelper;

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

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
            return true;
        } else if (id == R.id.action_Gladiator) {
            TextView t=(TextView)findViewById(R.id.content);
            t.setText(getResources().getText(R.string.gladiator));
        } else if (id == R.id.action_Arcanist) {
            TextView t=(TextView)findViewById(R.id.content);
            t.setText(getResources().getText(R.string.arcanist));
        } else if (id == R.id.action_Archer) {
            TextView t=(TextView)findViewById(R.id.content);
            t.setText(getResources().getText(R.string.archer));
        } else if (id == R.id.action_Conjurer) {
            TextView t=(TextView)findViewById(R.id.content);
            t.setText(getResources().getText(R.string.conjurer));
        } else if (id == R.id.action_Immortal_Flames) {
            TextView t=(TextView)findViewById(R.id.content);
            t.setText(getResources().getText(R.string.immortal_flames));
        } else if (id == R.id.action_Lancer) {
            TextView t=(TextView)findViewById(R.id.content);
            t.setText(getResources().getText(R.string.lancer));
        } else if (id == R.id.action_Maelstrom) {
            TextView t=(TextView)findViewById(R.id.content);
            t.setText(getResources().getText(R.string.maelstrom));
        } else if (id == R.id.action_Marauder) {
            TextView t=(TextView)findViewById(R.id.content);
            t.setText(getResources().getText(R.string.marauder));
        } else if (id == R.id.action_Pugilist) {
            TextView t=(TextView)findViewById(R.id.content);
            t.setText(getResources().getText(R.string.pugilist));
        } else if (id == R.id.action_Rogue) {
            TextView t=(TextView)findViewById(R.id.content);
            t.setText(getResources().getText(R.string.rogue));
        } else if (id == R.id.action_Twin_Adder) {
            TextView t=(TextView)findViewById(R.id.content);
            t.setText(getResources().getText(R.string.twin_adder));
        } else if (id == R.id.action_Thaumaturge) {
            TextView t=(TextView)findViewById(R.id.content);
            t.setText(getResources().getText(R.string.thaumaturge));
        } else if (id == R.id.action_Welcome) {
            TextView t=(TextView)findViewById(R.id.content);
            t.setText(getResources().getText(R.string.welcome_text));
        }
        return super.onOptionsItemSelected(item);
    }
}
