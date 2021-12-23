package com.nitin.flikerbrowser;

import android.os.Bundle;

import com.nitin.flikerbrowser.databinding.ActivitySearchBinding;

public class SearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySearchBinding binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activateToolBar(true);


    }

}