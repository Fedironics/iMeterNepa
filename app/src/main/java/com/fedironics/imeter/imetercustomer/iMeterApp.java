package com.fedironics.imeter.imetercustomer;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import org.json.JSONObject;

/**
 * Created by davidity on 5/15/17.
 */

public class iMeterApp extends Application {
    public APIManager imeterapi ;

    @Override
    public void onCreate() {
        super.onCreate();
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        imeterapi = new APIManager(cm,"http://inkanimation.com");
    }
    public JSONObject simpleApiGet(String link){
        imeterapi.setLink(link);
        return  imeterapi.execute();
    }
public JSONObject nepaInfo(){
    String rtlink = "http://ekedp.com/ajax/accountDetail";
    SharedPreferences sharedPref = getSharedPreferences(getResources().getString(R.string.sharedPref),0);
    String qlink =sharedPref.getString("meter_no","");
    return simpleApiGet(rtlink + qlink);
}
}
