/*  created by: nitin23329 
    created on 20/12/21 
    inside the package - com.nitin.flikerbrowser 
*/
package com.nitin.flikerbrowser;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetFlikerJsonData extends AsyncTask<String,Void,List<Photo>> implements GetRawData.OnDownloadComplete {
    /*
        --> The MainActivity will use this Class for getting list of Photos from Fliker
        --> This class will then use GetRawData to get raw data in json format
        --> Then this call will parse the data and create a list of photos out of it.
        --> then this list will be passed to our MainActivity
        --> We make this class AsyncTask as parsing JSON may take longer time apart from downloading
     */
    private static final String TAG = "GetFlikerJsonData";
    private List<Photo> photoList;
    private String baseUrl;
    private String language;
    private boolean matchAll;
    private OnDataAvailable callBack;
    private DownloadStatus status;
    private boolean isRunningOnSameThread;



    /*
    --> Any class using this class needs to have a callback method onDataAvailable()
            so that we can send them list of photos
     */
    interface OnDataAvailable{
        public void onDataAvailable(List<Photo> photoList, DownloadStatus status);
    }

    public GetFlikerJsonData(String baseUrl, String language, boolean matchAll, OnDataAvailable callBack) {
        this.baseUrl = baseUrl;
        this.language = language;
        this.matchAll = matchAll;
        this.callBack = callBack;
        status = DownloadStatus.IDLE;
        isRunningOnSameThread = false; // by default this class will run asynchronously
    }

    public GetFlikerJsonData(String baseUrl,  OnDataAvailable callBack) {
        this(baseUrl,"en-us",true, callBack);
    }

    public void runningOnSameThread(String searchCriteria){
            Log.d(TAG, "runningOnSameThread: starts");
            // searchCriteria are the tags added by user
            // this function will be used by our MainActivity to get the List of photos
            isRunningOnSameThread = true;
            String destinationUri = createURI(searchCriteria);
            GetRawData getRawData = new GetRawData(this);
            // since this function will run on main thread, we need to create another thread for
            // downloading raw data
            getRawData.execute(destinationUri);

            Log.d(TAG, "runningOnSameThread: ends");
    }

    @Override
    protected List<Photo> doInBackground(String... params) {
        Log.d(TAG, "doInBackground: starts");
        String searchCriteria = params[0];
        // searchCriteria are the tags added by user
        // this function will be used by our MainActivity to get the List of photos

        String destinationUri = createURI(searchCriteria);
        GetRawData getRawData = new GetRawData(this);
        // since this function will run on separate thread, it will not let us create another
        // thread for GetRawData so we have do stuffs without creating another thread in GetRawData.
        getRawData.runInSameThread(destinationUri);

        Log.d(TAG, "doInBackground: ends");
        return photoList;
    }

    @Override
    protected void onPostExecute(List<Photo> photoList) {
        // since we are doing stuff on another thread, onPostExecute can only communicate with background thread.
        Log.d(TAG, "onPostExecute: got the photoList and going back to MainActivity");
        if(!isRunningOnSameThread)  // if this is running in separate thread
            this.callBack.onDataAvailable(photoList,this.status);
    }


    private String createURI(String searchCriteria){
        /*
            --> create an object of StringUri using Uri.parse() and put in the base line url
            --> then buildUpon() will return a Builder object upon this stringUri object which will facilitate manipulating the url
            --> appendQueryParameter() adds the parameter and its value to the URI and separate them appropriately
            --> After adding all parameters, build() will return the final URI and then we will get the final link
         */
        String uri = Uri.parse(baseUrl) . buildUpon()
                .appendQueryParameter("format","json")
                .appendQueryParameter("tags",searchCriteria)
                .appendQueryParameter("tagmode",matchAll?"all":"any")
                .appendQueryParameter("lang",language)
                .appendQueryParameter("nojsoncallback","1")
                .build().toString();

        Log.d(TAG, "createURI() returned: " + uri);
        return uri;
    }

    // this is a callback for the GetRawData functionality to get the downloaded data\
    // after downloading Raw json data, parse it and create list of photos
    // then give this list to callback(MainActivity)
    @Override
    public void onDownloadComplete(String data, DownloadStatus downloadStatus){
        Log.d(TAG, "onDownloadComplete: Raw data is downloaded with status : " + downloadStatus);
        if(downloadStatus == DownloadStatus.OK){
            photoList = new ArrayList<>();
            try{
                JSONObject jsonObject = new JSONObject(data);
                JSONArray itemsArray = jsonObject.getJSONArray("items");
                for(int i=0;i<itemsArray.length();i++){
                    JSONObject entry = itemsArray.getJSONObject(i);
                    String imageUrl  = entry.getJSONObject("media").getString("m");
                    photoList.add(new Photo(entry.getString("title"), entry.getString("author"),
                            entry.getString("author_id"),imageUrl.replace("_m.","_b."),
                            entry.getString("tags"),imageUrl));
                }
            }catch (JSONException e){
                Log.e(TAG, "onDownloadComplete: error while parsing Json" + e.getMessage());
            }
        }
        this.status = downloadStatus;
        Log.d(TAG, "onDownloadComplete: returning back to main activity with list of photos");
        if(isRunningOnSameThread)   // if we are not making any other thread and running is same thread
            this.callBack.onDataAvailable(photoList,status);
    }
}
