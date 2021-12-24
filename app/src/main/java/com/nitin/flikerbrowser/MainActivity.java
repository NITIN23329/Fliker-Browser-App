package com.nitin.flikerbrowser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nitin.flikerbrowser.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends BaseActivity implements GetFlikerJsonData.OnDataAvailable, RecyclerOnItemClickListener.OnRecyclerClickListener{
    private static final String TAG = "MainActivity";
    private final String link  = "https://www.flickr.com/services/feeds/photos_public.gne";
    private FlikerRecyclerViewAdapter flikerRecyclerViewAdapter;

    @Override   // onResume() called when activity comes to foreground
    protected void onResume() {
        Log.d(TAG, "onResume: starts");
        super.onResume();
        GetFlikerJsonData getFlikerJsonData = new GetFlikerJsonData(link, this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String query = sharedPreferences.getString(FLIKER_QUERY,"").trim();
        if(query.length() > 0) getFlikerJsonData.execute(query);
        Log.d(TAG, "onResume: ends");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activateToolBar(false); // present in BaseActivity

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        // give recyclerView a new Layout manager to handle views
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        flikerRecyclerViewAdapter = new FlikerRecyclerViewAdapter(this);
        // associate custom adapter to recycle view
        recyclerView.setAdapter(flikerRecyclerViewAdapter);
        // we need to link our onItemClickListener to the recyclerview for which it is responsible for
        recyclerView.addOnItemTouchListener(new RecyclerOnItemClickListener(this, recyclerView, this));

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
        // if you click something on the action bar at top right, this method will get called
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.close_app) {
            // this will close the app
            finish();
            return true;    // return true as it been handled
        }
        else if(id == R.id.search_tag){
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);

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

    @Override
    public void onItemClick(View view, int position) {
        // launch the photo_detail activity when a View is being pressed
        // Intent will make us launch another activity from existing activity
        Intent intent = new Intent(this,PhotoDetailActivity.class);
        // putExtra() used to send data across activities, that is, it will send the details of our photo from main_activity to photo_detail_activity
        // PHOTO_TRANSFER act as a key here to retrieve the data being passed
        intent.putExtra(PHOTO_TRANSFER,flikerRecyclerViewAdapter.getPhotoAtIndex(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        // TODO: launch a browser with link to fliker for the given view.
        Log.d(TAG, "onItemLongClick: called");
        Toast.makeText(this,"Long Click at position : "+position,Toast.LENGTH_SHORT).show();

    }
}