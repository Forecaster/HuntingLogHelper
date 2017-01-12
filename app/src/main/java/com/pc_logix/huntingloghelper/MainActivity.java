package com.pc_logix.huntingloghelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

    ProgressDialog mDownloadProgressDialog;
    ProgressDialog mUnzipProgressDialog;
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
        mDownloadProgressDialog = new ProgressDialog(MainActivity.this);
        mDownloadProgressDialog.setMessage("Downloading Icon Pack");
        mDownloadProgressDialog.setIndeterminate(true);
        mDownloadProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDownloadProgressDialog.setCancelable(true);

        mUnzipProgressDialog = new ProgressDialog(MainActivity.this);
        mUnzipProgressDialog.setMessage("Decompressing Icon Pack");
        mUnzipProgressDialog.setIndeterminate(true);
        mUnzipProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mUnzipProgressDialog.setCancelable(true);
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
        } else if (id == R.id.action_DownloadIconPack) {
            final DownloadTask downloadTask = new DownloadTask(MainActivity.this);
            downloadTask.execute("https://pc-logix.com/ffxiv/ffxiv-icons.zip");
            mDownloadProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    downloadTask.cancel(true);
                }
            });

            mUnzipProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    //unzipTask.cancel(true);
                }
            });
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



    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mDownloadProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mDownloadProgressDialog.setIndeterminate(false);
            mDownloadProgressDialog.setMax(100);
            mDownloadProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mDownloadProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context,"Download error: "+result, Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
                final Decompress unzipTask = new Decompress(MainActivity.this, Environment.getExternalStorageDirectory().toString() + File.separator + "ffxiv-icons.zip", Environment.getExternalStorageDirectory().toString() + File.separator);
                unzipTask.execute();
            }
        }


        /* Checks if external storage is available for read and write */
        public boolean isExternalStorageWritable() {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                return true;
            }
            return false;
        }

        /* Checks if external storage is available to at least read */
        public boolean isExternalStorageReadable() {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state) ||
                    Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                return true;
            }
            return false;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                if (isExternalStorageWritable())
                    output = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + File.separator + sUrl[0].substring(sUrl[0].lastIndexOf('/') + 1));
                else
                    output = new FileOutputStream(context.getFilesDir() + File.separator + sUrl[0].substring(sUrl[0].lastIndexOf('/') + 1));

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }
    }

    private class Decompress extends AsyncTask<Void, Integer, Integer> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        private String _zipFile;
        private String _location;
        private int per = 0;
        private ZipFile zip;
        private ZipEntry ze = null;

        public Decompress(Context context, String zipFile, String location) {
            this.context = context;
            this.context = context;
            _zipFile = zipFile;
            _location = location;
            _dirChecker("ffxiv-icons");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mUnzipProgressDialog.show();
        }

        private void _dirChecker(String dir) {
            File f = new File(_location + dir);
            if(!f.isDirectory()) {
                f.mkdirs();
            }
        }

        @Override
        protected Integer doInBackground(Void... params) {
                InputStream is;
                ZipInputStream zis;
                try {
                    String filename;
                    is = new FileInputStream(_zipFile);
                    zip = new ZipFile(_zipFile);
                    zis = new ZipInputStream(new BufferedInputStream(is));
                    ZipEntry ze;
                    byte[] buffer = new byte[1024];
                    int count;

                    while ((ze = zis.getNextEntry()) != null) {
                        filename = ze.getName();
                        if (ze.isDirectory()) {
                            File fmd = new File(_location + filename);
                            fmd.mkdirs();
                            continue;
                        }
                        per++;
                        publishProgress(per);
                        FileOutputStream fout = new FileOutputStream(_location + filename);
                        while ((count = zis.read(buffer)) != -1) {
                            fout.write(buffer, 0, count);
                        }
                        fout.close();
                        zis.closeEntry();
                    }
                    zis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return 0;
                }
                return 1;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            mUnzipProgressDialog.setMax(zip.size());
            mUnzipProgressDialog.setIndeterminate(false);
            mUnzipProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            mWakeLock.release();
            mUnzipProgressDialog.dismiss();
            if (result != 1)
                Toast.makeText(context,"Decompression error", Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(context, "File Decompressed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
