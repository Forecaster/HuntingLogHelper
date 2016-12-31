package com.pc_logix.huntingloghelper;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.Toast;

/**
 * Created by Michi on 12/30/2016.
 */

public class CheckHandler implements AdapterView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        CheckedTextView ctv = (CheckedTextView)arg1;
        if(ctv.isChecked()) {
            //ctv.getId();
            LogViewActivity.newDB.rawQuery("UPDATE " +
                    LogViewActivity.tableName +
                    "done = 1 where class='" + LogViewActivity.myClass + " AND _id = " + ctv.getTag(ctv.getId()), null);
            //Toast.makeText(MainActivity.this, "now it is unchecked", Toast.LENGTH_SHORT).show();
        } else {
            LogViewActivity.newDB.rawQuery("UPDATE " +
                    LogViewActivity.tableName +
                    "done = 0 where class='" + LogViewActivity.myClass + " AND _id = " + ctv.getTag(ctv.getId()), null);
            //Toast.makeText(MainActivity.this, "now it is checked", Toast.LENGTH_SHORT).show();
        }
    }
}
