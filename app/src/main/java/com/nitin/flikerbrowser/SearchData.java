/*  created by: nitin23329 
    created on 24/12/21 
    inside the package - com.nitin.flikerbrowser 
*/
package com.nitin.flikerbrowser;

public class SearchData {
    // this call will pass the query from search_activity to main_activity
    private static String query = "";
    private SearchData(){ }
    public static void setData(String str){
        query = str;
    }
    public static String getData(){return query;};
}
