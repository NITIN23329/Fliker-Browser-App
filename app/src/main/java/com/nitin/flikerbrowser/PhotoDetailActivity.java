package com.nitin.flikerbrowser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.nitin.flikerbrowser.databinding.ActivityPhotoDetailBinding;
import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends BaseActivity {
    private static final String TAG = "PhotoDetail";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityPhotoDetailBinding binding = ActivityPhotoDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activateToolBar(true);

        retrievePhoto();


        }

    @SuppressLint("SetTextI18n")
    public void retrievePhoto(){
        Log.d(TAG, "retrievePhoto: is called");
        // onItemClick() in the MainActivity have generated an Intent and send the required data to this class
        // now we must retrieve the data and process it
        Intent intent = getIntent();
        Photo photo = (Photo) intent.getSerializableExtra(PHOTO_TRANSFER); // retrieve photo object
        if(photo!=null) {
            // fill in the values of views
            TextView photo_title = findViewById(R.id.photo_title);
            TextView photo_author = findViewById(R.id.photo_author);
            TextView photo_tags = findViewById(R.id.photo_tags);
            ImageView photo_image = findViewById(R.id.photo_image);

            photo_title.setText("Title: " + photo.getTitle());
            photo_author.setText("Author: " + photo.getAuthor());
            photo_tags.setText("Tags: " + photo.getTags());
            Picasso.get().load(photo.getLink())
                    .error(R.drawable.place_holder)
                    .placeholder(R.drawable.place_holder)
                    .into(photo_image);
        }
    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

}