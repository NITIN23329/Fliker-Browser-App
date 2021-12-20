package com.nitin.flikerbrowser;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.nitin.flikerbrowser.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private final String link  = "https://www.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(link);

        Log.d(TAG, "onCreate: ends");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(TAG, "onCreateOptionsMenu() returned: " + true);
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
        }

        return super.onOptionsItemSelected(item);
    }
    // this is a callback for the GetRawData functionality
    public void onDownloadComplete(String data, DownloadStatus downloadStatus){
        Log.d(TAG, "onDownloadComplete: Now back to MainActivity with downloaded data");
        if(downloadStatus == DownloadStatus.OK){
            Log.d(TAG, "onDownloadComplete: data is : " + data);
        }
        else {
            Log.e(TAG, "onDownloadComplete: downloading is failed with status: " + downloadStatus);
        }
    }

}