package com.fedironics.imeter.imetercustomer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by davidity on 5/15/17.
 */

public class DownloaderService extends Service {
    private boolean runFlag = false;
    private final int DELAY = 5000;
    public final String TAG = "com.fedironics";
    @Override
    public IBinder onBind(Intent intent) { //
        return null;
    }
    @Override
    public void onCreate() { //
        super.onCreate();
        Log.d(TAG, "onCreated");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) { //
        super.onStartCommand(intent, flags, startId);
        Log.d(TAG, "onStarted");
        return START_STICKY;
    }
    @Override
    public void onDestroy() { //
        super.onDestroy();
        Log.d(TAG, "onDestroyed");
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
            Log.d(TAG, "Updater running");
            try {
// Some work goes here...
                Log.d(TAG, "Updater ran");
                Thread.sleep(DELAY); //
            } catch (InterruptedException e) { //
                updaterService.runFlag = false;
            }
        }
    }
} // Updater


}
