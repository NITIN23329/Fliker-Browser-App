package com.nitin.flikerbrowser;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nitin.flikerbrowser.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetFlikerJsonData.OnDataAvailable{
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private final String link  = "https://www.flickr.com/services/feeds/photos_public.gne";
    private FlikerRecyclerViewAdapter flikerRecyclerViewAdapter;

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: starts");
        super.onResume();
        GetFlikerJsonData getFlikerJsonData = new GetFlikerJsonData(link, this);
        getFlikerJsonData.execute("house,black");
//        getFlikerJsonData.runningOnSameThread("house,black");
        Log.d(TAG, "onResume: ends");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        // give recyclerView a new Layout manager to handle views
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        flikerRecyclerViewAdapter = new FlikerRecyclerViewAdapter(new ArrayList<>(),this);
        // associate custom adapter to recycle view
        recyclerView.setAdapter(flikerRecyclerViewAdapter);


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

    @Override
    public void onDataAvailable(List<Photo> photoList, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: download status : "+status);
        if(status == DownloadStatus.OK){
            flikerRecyclerViewAdapter.loadNewData(photoList);
        }
    }
}