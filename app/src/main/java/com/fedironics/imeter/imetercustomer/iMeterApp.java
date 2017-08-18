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


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by davidity on 5/15/17.
 */

public class iMeterApp extends Application {
    public  ConnectivityManager cm;
    public  User user;

    public static final String TAG = "customtag";
    public final String EEDCRootLink = "https://ekedp.com/ajax/accountDetail/";
    public final String serverRootLink ="http://inkanimation.com/imeterApi/";

    private NotificationManager mNotificationManager;
    private int notificationID = 100;
    private int numMessages = 0;
    public static iMeterApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        cm = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        user = new User();
        Log.e(TAG, "application started");
        instance = this;
        getNepaInfo("something");
    }

    public APIManager getAPIManager(){
        return new APIManager(cm);
    }

    public JSONObject simpleAPIGet(String link) {
        Log.d(TAG, "trying to get " + link);
        APIManager api = getAPIManager();
        api.setLink(link);
        return api.execute("GET");
    }

    public void getNepaInfo(String cust_acc_no) {
        SharedPreferences sharedPref = getSharedPreferences(getResources().getString(R.string.sharedPref),0);
        String qlink =sharedPref.getString("customer_number","");
        Log.e(iMeterApp.TAG,"customer number:"+qlink);
        new Thread() {
            public void run() {
                try {
                    //TODO : test the reception of account number
                    String cust_acc_no = "0349051136-01";
                    JSONObject EEDCInfo = simpleAPIGet(EEDCRootLink + cust_acc_no);
                    user.setEEDC(EEDCInfo);
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                    Log.d(iMeterApp.TAG,"EEDCINfo is empty");
                }

            }
        }.start();

    }

    public String getUserInfo(final String email, final String password) {
        try {
            Log.e(iMeterApp.TAG,"getting user info");
            APIManager api = getAPIManager();
            api.setLink(serverRootLink+"users");
            api.addPostValue("special","authenticate");
            api.addPostValue("email",email);
            api.addPostValue("password",password);
            JSONObject UserInfo = api.execute("POST");
            return user.setUser(UserInfo);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            Log.d(iMeterApp.TAG,"EEDCINfo is empty");
            return "Nothing was gotten from server";
        }

    }

    public void displayNotification(String title, String desc, int image,String sticker) {
        Log.i("Start", "notification");
      /* Invoking the default notification service */
        NotificationCompat.Builder  mBuilder =
                new NotificationCompat.Builder(this);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(desc);
        mBuilder.setTicker(sticker);
        mBuilder.setSmallIcon(image);
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
