/*  created by: nitin23329 
    created on 22/12/21 
    inside the package - com.nitin.flikerbrowser 
*/
package com.nitin.flikerbrowser;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerOnItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecyclerItmClkListner";

    // recyclerview click listener must use a call back mechanism
    interface OnRecyclerClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private final OnRecyclerClickListener listener;
    private final GestureDetectorCompat gestureDetector;    // tell which type of gesture is happening in RecyclerView

    public RecyclerOnItemClickListener(Context context, final RecyclerView recyclerView, OnRecyclerClickListener listener) {
        this.listener = listener;
        // gesture detecotor allows us to handle event handler of some of the gestures
        gestureDetector = new GestureDetectorCompat(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public void onLongPress(MotionEvent e) {
                // called when a long press gesture is detected
                View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                boolean result = false;
                if(childView!=null && listener!=null){
                    //callback
                    listener.onItemLongClick(childView,recyclerView.getChildAdapterPosition(childView));
                    result = true;
                }
                Log.d(TAG, "onLongPress: isHandled? " + result);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                // called when a single tap is detected
                View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                boolean result = false;
                if(childView!=null && listener!=null){
                    // callback
                    listener.onItemClick(childView,recyclerView.getChildAdapterPosition(childView));
                    result =  true;
                }
                Log.d(TAG, "onSingleTapConfirmed: return: "+ result);
                return true;
            }
        });

    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        // this method gets called even before the Recyclerview on Item click listener get
        // called when some gesture in Recycler view is detected.
        // if some listeners are handled by us, then return true otherwise false.
        boolean result = false;
        if(gestureDetector != null) result =  gestureDetector.onTouchEvent(e);
        Log.d(TAG, "onInterceptTouchEvent: returned : " + result);
        return result;
    }
}
