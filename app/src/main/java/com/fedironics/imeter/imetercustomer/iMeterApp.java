package com.fedironics.imeter.imetercustomer;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by davidity on 5/15/17.
 */

public class iMeterApp extends Application {
    public static final String TAG = "customtag";
    String rtlink = "https://ekedp.com/ajax/accountDetail/";

    private NotificationManager mNotificationManager;
    private int notificationID = 100;
    private int numMessages = 0;



    public JSONObject EEDCInfo ;
    public JSONObject userInfo;
    public String suserInfo;
    public String SEEDCInfo ;
    public JSONObject EUserInfo;
    public APIManager imeterapi;
    public static iMeterApp sInstance;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        ConnectivityManager cm = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        imeterapi = new APIManager(cm);
        Log.e(TAG, "application started");
        EUserInfo = new JSONObject();
        mRequestQueue = Volley.newRequestQueue(this);
        sInstance = this;
        JsonObjectRequest request = new JsonObjectRequest(rtlink+"0349051136-01", null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG,response.toString());
                        Log.d(TAG,"volley is bae");
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG,"error using volley to get results");
                    }
                }
        );
        mRequestQueue.add(request);
        //  getNepaInfo("somethin imaterial");
    }
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
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
                    saveEEDCInfo(EEDCInfo);
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

    public String getStringInfo(String key){
        if(EEDCInfo!=null) {

            Log.d(iMeterApp.TAG,"EEDC info is not null");
            try {
                return EUserInfo.getString(key);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(iMeterApp.TAG, "Trying to get a non existent string");
            }
        }
        return "";
    }
    public void saveEEDCInfo(JSONObject recievedData){
        //TODO: save all EEDC data linearly here
        try {
            String result =recievedData.getString("result");
            JSONObject resultObject = new JSONObject(result);
            JSONArray accountDetailArray = resultObject.getJSONArray("accounts");
            JSONObject accountDetail = accountDetailArray.getJSONObject(0);
            String accountNumber = accountDetail.getString("accountNumber");
            String address1 = accountDetail.getString("address1");
            String businessDistrict = accountDetail.getString("businessDistrict");
            int customerId = accountDetail.getInt("customerId");
            String name = accountDetail.getString("name");

            EUserInfo.put("accountNumber",accountNumber);
            EUserInfo.put("address1",address1);
            EUserInfo.put("businessDistrict",businessDistrict);
            EUserInfo.put("customerId",customerId);
            EUserInfo.put("name",name);
        }
        catch (JSONException e){
            Log.e(iMeterApp.TAG,"field doesnt exist in json object");
            e.printStackTrace();
        }

    }


    public void addDefaultParams(){
        SharedPreferences sharedPref = getSharedPreferences(getResources().getString(R.string.sharedPref), 0);
        //  addPostParam("user_id",sharedPref.getInt("user_id",0));

        addPostParam("name",getStringInfo("name"));
        addPostParam("phone",getStringInfo("phone"));
        addPostParam("email",getStringInfo("email"));
        addPostParam("accountNumber",getStringInfo("accountNumber"));

    }
    public boolean saveUser(JSONObject recievedObject) {
        //TODO : saving user data should come after the eedc data in order to overwrite the name
        try {
            userInfo = recievedObject;
            SharedPreferences sharedPref = getSharedPreferences(getResources().getString(R.string.sharedPref), 0);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("name", recievedObject.getString("name"));
            editor.putInt("user_id", recievedObject.getInt("id"));
            editor.putString("phone", recievedObject.getString("phone"));
            editor.putString("email", recievedObject.getString("email"));
            editor.putString("password", recievedObject.getString("password"));

            EUserInfo.put("name",recievedObject.getString("name"));
            EUserInfo.put("phone",recievedObject.getString("phone"));
            EUserInfo.put("email",recievedObject.getString("email"));
            EUserInfo.put("password",recievedObject.getString("password"));
            EUserInfo.put("user_id",recievedObject.getInt("id"));


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

    public void displayNotification() {
        Log.i("Start", "notification");
      /* Invoking the default notification service */
        NotificationCompat.Builder  mBuilder =
                new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("New Message");
        mBuilder.setContentText("You've received new message.");
        mBuilder.setTicker("New Message Alert!");
        mBuilder.setSmallIcon(R.drawable.a_avator);
      /* Increase notification number every time a new notification arrives */
        mBuilder.setNumber(++numMessages);
      /* Creates an explicit intent for an Activity in your app */
        Intent resultIntent = new Intent(this, NotificationView.class);
      TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationView.class);
      /* Adds the Intent that starts the Activity to the top of the stack */
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
      /* notificationID allows you to update the notification later on. */
        mNotificationManager.notify(notificationID, mBuilder.build());
    }






}
