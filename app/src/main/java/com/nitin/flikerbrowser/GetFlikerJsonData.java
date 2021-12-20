/*  created by: nitin23329 
    created on 20/12/21 
    inside the package - com.nitin.flikerbrowser 
*/
package com.nitin.flikerbrowser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetFlikerJsonData implements GetRawData.OnDownloadComplete {
    /*
        --> The MainActivity will use this Class for getting list of Photos from Fliker
        --> This class will then use GetRawData to get raw data in json format
        --> Then this call will parse the data and create a list of photos out of it.
        --> then this list will be passed to our MainActivity
     */
    private static final String TAG = "GetFlikerJsonData";
    private List<Photo> photoList;
    private String baseUrl;
    private String language;
    private boolean matchAll;
    private OnDataAvailable callBack;

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
    }

    public GetFlikerJsonData(String baseUrl,  OnDataAvailable callBack) {
        this(baseUrl,"en-us",true, callBack);
    }

    public void executeOnSameThread(String searchCriteria){
        // searchCriteria are the tags added by user
        // this function will be used by our MainActivity to get the List of photos 
        Log.d(TAG, "executeOnSameThread: starts");

        String destinationUri = createURI(searchCriteria);
        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationUri);

        Log.d(TAG, "executeOnSameThread: ends");
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
                downloadStatus = DownloadStatus.FAILED;
            }
            this.callBack.onDataAvailable(photoList,downloadStatus);
        }
    }
}
