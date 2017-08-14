package com.fedironics.imeter.imetercustomer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by davidity on 5/15/17.
 */

public class DownloaderService extends Service {
    private boolean runFlag = false;
    private final int DELAY = 5000;
    @Override
    public IBinder onBind(Intent intent) { //
        return null;
    }
    @Override
    public void onCreate() { //
        super.onCreate();
        Log.d(iMeterApp.TAG, "onCreated");
        Updater myUpdater = new Updater();
        myUpdater.start();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) { //
        super.onStartCommand(intent, flags, startId);
        Log.d(iMeterApp.TAG, "onStarted");
        return START_STICKY;
    }
    @Override
    public void onDestroy() { //
        super.onDestroy();
        Log.d(iMeterApp.TAG, "onDestroyed");
    }

    /**
     * Thread that performs the actual update from the online service
     */
    private class Updater extends Thread { //
        public Updater() {
            super("UpdaterService-Updater");
        }

        public void run() { //
            DownloaderService updaterService = DownloaderService.this;
            while (updaterService.runFlag) { //
                Log.d(iMeterApp.TAG, "Updater running");
                try {
                    Log.d(iMeterApp.TAG, "Updater ran");
                    iMeterApp myApp = (iMeterApp)getApplicationContext();
                    APIManager myApi = (APIManager)myApp.getAPIManager();
                    myApi.addServerCredentials("posts");
                    JSONObject result = myApi.execute("GET");
                    Log.d(iMeterApp.TAG,result.toString());

                } catch (Exception e) { //
                    updaterService.runFlag = false;
                }
            }
        }
    }


}
