/*  created by: nitin23329 
    created on 22/12/21 
    inside the package - com.nitin.flikerbrowser 
*/
package com.nitin.flikerbrowser;

import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BaseActivity extends AppCompatActivity {
    // this will be base class for all activity class in our app instead of AppCompactActivity
    private static final String FLIKER_QUERY = "FLIKER_QUERY";
    public static final String PHOTO_TRANSFER = "PHOTO_TRANSFER";
    private static final String TAG = "BaseActivity";

    void activateToolBar(boolean enableHome){
        Log.d(TAG, "activateToolBar: gets called ");
        ActionBar actionBar = getSupportActionBar();
        if(actionBar==null){
            Toolbar toolbar = findViewById(R.id.toolbar);
            if(toolbar != null) {
                setSupportActionBar(toolbar);
                actionBar = getSupportActionBar();
            }
        }
        if(actionBar!=null)
            actionBar.setDisplayHomeAsUpEnabled(enableHome);


    }


}
