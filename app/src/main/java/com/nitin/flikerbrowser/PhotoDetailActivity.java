package com.nitin.flikerbrowser;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nitin.flikerbrowser.databinding.ActivityPhotoDetailBinding;

public class PhotoDetailActivity extends AppCompatActivity {

    private ActivityPhotoDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPhotoDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

    }

}