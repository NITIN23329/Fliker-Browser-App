/*  created by: nitin23329 
    created on 21/12/21 
    inside the package - com.nitin.flikerbrowser 
*/
package com.nitin.flikerbrowser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class FlikerRecyclerViewAdapter extends RecyclerView.Adapter<FlikerRecyclerViewAdapter.FlikerImageViewHolder>{
    /*
        --> This adapter will support Recycler view by providing Photo ViewHolder as and when needed
        --> ViewHolder contains all the information about a photo.
     */
    private static final String TAG = "RecyclerViewAdapter";
    private List<Photo> photoList;
    private Context context;    // will be use to download actual image at runtime by picasso.

    static class FlikerImageViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "FlikerImageViewHolder";
        ImageView thumbnail;
        TextView title;

        public FlikerImageViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "FlikerImageViewHolder: Constructor Called");
            thumbnail = itemView.findViewById(R.id.thumbNail);
            title = itemView.findViewById(R.id.titleView);
        }
    }

    public FlikerRecyclerViewAdapter(Context context) {
        this.context = context;
        this.photoList = new ArrayList<>();
    }

    @NonNull
    @Override
    public FlikerImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // called by layout manager to get a new view .
        // onCreateViewHolder() inflate a view from browse.xlm and return it

        Log.d(TAG, "onCreateViewHolder: new view requested");
        // LayoutInflater will bring the xml layout into code so that views can be added to xml.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse,parent,false);
        return new FlikerImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlikerImageViewHolder holder, int position) {
        // called by layout manager when it want to add some new data to existing view .
        if(getItemCount() == 1){
            holder.thumbnail.setImageResource(R.drawable.place_holder);
            holder.title.setText(R.string.not_found);
            return;
        }
        // This is where we will change the URL to actual image in thumbnail  using Picasso.
        // Picasso load() will take the url and put actual image using into()
        Photo photo = photoList.get(position);
        Log.d(TAG, "onBindViewHolder: current photo at position: " + position);
        Picasso.get().load(photo.getImageUrl())
                .error(R.drawable.place_holder)
                .placeholder(R.drawable.place_holder)
                .into(holder.thumbnail);
        holder.title.setText(photo.getTitle());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: size:" + photoList.size());
        // return one to show that no photos found
        return photoList.size()==0?1:photoList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void loadNewData(List<Photo> list){
        photoList  = list;
        // this will tell the Recycler view that data has been changed and reinitialize photos.
        super.notifyDataSetChanged();
    }
    public Photo getPhotoAtIndex(int index){
        return photoList.get(index);
    }
}

