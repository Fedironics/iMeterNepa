package com.fedironics.imeter.imetercustomer;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by davidity on 5/15/17.
 */

public class iMeterApp extends Application {
    public static final String TAG = "customtag";
    String rtlink = "https://ekedp.com/ajax/accountDetail/";


    public JSONObject EEDCInfo ;
    public JSONObject userInfo;
    public String SEEDCInfo ;
    public APIManager imeterapi;


    @Override
    public void onCreate() {
        super.onCreate();
        ConnectivityManager cm = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        imeterapi = new APIManager(cm);
        Log.e(TAG, "application started");
        getNepaInfo("somethin imaterial");
    }

    public JSONObject simpleApiGet(String link) {
        Log.d(TAG, "trying to get " + link);
        imeterapi.setLink(link);
        try {
            return imeterapi.execute("GET");

        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.d(TAG, "empty result");
        }
        return imeterapi.execute("GET");
    }

    public void getNepaInfo(String cust_acc_no) {
        SharedPreferences sharedPref = getSharedPreferences(getResources().getString(R.string.sharedPref),0);
        String qlink =sharedPref.getString("customer_number","");
        Log.e(iMeterApp.TAG,"customer number:"+qlink);
        new Thread() {
            public void run() {
                try {
                    String cust_acc_no = "0349051136-01";
                    EEDCInfo = simpleApiGet(rtlink + cust_acc_no);
                    SEEDCInfo = EEDCInfo.toString();
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                    Log.d(iMeterApp.TAG,"EEDCINfo is empty");
                }
            }
        }.start();

    }
    public void newPostRequest(String request){

        imeterapi.addServerCredentials(request);
        imeterapi.addDefaultPostValues();
    }
    public APIManager addPostParam(String key, String value){
        imeterapi.addPostValue(key,value);
        return imeterapi;
    }



    public boolean saveUser(JSONObject recievedObject) {
        try {
            userInfo = recievedObject;
            SharedPreferences sharedPref = getSharedPreferences(getResources().getString(R.string.sharedPref), 0);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("name", recievedObject.getString("name"));
            editor.putInt(getResources().getString(R.string.userid_tag), recievedObject.getInt("id"));
            editor.putString("phone", recievedObject.getString("phone"));
            editor.putString("email", recievedObject.getString("email"));
            editor.putString("password", recievedObject.getString("password"));
            //Intent toMain = new Intent(this,MainActivity.class);
            //startActivity(toMain);
            editor.apply();
            return true;
        } catch (JSONException e) {
            Log.e(iMeterApp.TAG, "unable to parse JSON");
            e.printStackTrace();
        }
        return false;
    }

}
