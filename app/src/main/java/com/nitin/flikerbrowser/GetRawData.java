/*  created by: nitin23329 
    created on 20/12/21 
    inside the package - com.nitin.flikerbrowser 
*/
package com.nitin.flikerbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


class GetRawData extends AsyncTask<String,Void, String> {
    /*
        AsyncTask works on background thread and give the results to UI thread.
        --> 1st parameter is the datatype of input parameter to doInBackground.
        --> The 2nd parameter is the datatype of progress metric
        --> The 3rd parameter is the return type of our result.
     */

    private static final String TAG = "GetRawData";
    private DownloadStatus downloadStatus;
    private  final OnDownloadComplete callBack;


    /*
       Whoever gonna use the GetRawData must have a callback onDownloadComplete() implemented
       in it. So we must use an interface here.
    */
    interface OnDownloadComplete{
        void onDownloadComplete(String data, DownloadStatus status);
    }

    public GetRawData(OnDownloadComplete activity){
        this.downloadStatus = DownloadStatus.IDLE;
        this.callBack = activity;
    }


    public void runInSameThread(String url){
        // this is same as executing GetRawData in separate thread but in same thread.
        onPostExecute(doInBackground(url));
    }
    @Override
    protected String doInBackground(String... strings) {
        Log.d(TAG, "doInBackground: In background thread");
        // this will download the data from the internet.
        HttpURLConnection urlConnection = null;
        BufferedReader br = null;
        if(strings == null){
            this.downloadStatus = DownloadStatus.NOT_INITIALISED;
            return null;
        }
        try{
            /*
            1) first open a url connection
            2) get the Input stream of the url connection to buffer reader
            3)read it line by line
            4) then return the result
            */
            this.downloadStatus = DownloadStatus.PROCESSING;
            String link = strings[0];
            URL url = new URL(link);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");  // tell what do we want to do with the connection
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            Log.d(TAG, "doInBackground: response code:"+responseCode);

            StringBuilder result = new StringBuilder();
            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            // read line by line
            for(String line = br.readLine();line != null; line = br.readLine()){
               result.append(line).append('\n');
            }
            this.downloadStatus = DownloadStatus.OK;
            return result.toString();
        }catch (MalformedURLException e){
            Log.e(TAG, "doInBackground: url is invalid" + e.getMessage());
        }catch (IOException e){
            Log.e(TAG, "doInBackground: can not read data" + e.getMessage() );
        }catch (SecurityException e){
            Log.e(TAG, "doInBackground: internet access in not provided" + e.getMessage());
        }
        finally {
            // close all connection
            if(urlConnection != null)
                urlConnection.disconnect();
            if(br != null){
                try{
                    br.close();
                }
                catch (IOException e){
                    Log.e(TAG, "doInBackground: can not close the BufferedReader"+ e.getMessage() );
                }
            }
        }
        this.downloadStatus = DownloadStatus.FAILED;
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        // after downloading the data pass it to our MainActivity using a callback method
        // that is present in the MainActivity class called onDownloadComplete.
        Log.d(TAG, "onPostExecute: starts");
        this.callBack.onDownloadComplete(s,this.downloadStatus);
    }
}
