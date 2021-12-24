package com.nitin.flikerbrowser;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;

import com.nitin.flikerbrowser.databinding.ActivitySearchBinding;

public class SearchActivity extends BaseActivity {
    private static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySearchBinding binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activateToolBar(true);
        Log.d(TAG, "onCreate: ends");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: is called");
        // this function will add menus if we have a action bar
        getMenuInflater().inflate(R.menu.menu_search,menu);
        // code is taken from : https://developer.android.com/training/search/setup
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);
        Log.d(TAG, "onCreateOptionsMenu: searchable info : " +searchableInfo.toString());
        // setIconified() will either put a editable text view to write or a icon of search
        // when set to false, the former is used.
        searchView.setIconified(false); // will give search bar as soon as menu is created
        // now after typing the query, we need to respond to  the input using an eventHandler
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // return true if you have handled it or return false.
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: query: " + query);
                //SharedPreferences used to share small amount of data across different activity of same app
                // since the data are shared among different independent activity, getAppContext() is used
                SearchData.setData(query.trim());
                searchView.clearFocus();    // remove the focus from the textview
                finish();   // goback to parent activity
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                finish();
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}