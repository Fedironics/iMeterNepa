package com.fedironics.imeter.imetercustomer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by davidity on 11/05/17.
 * execution of this class has to be performed in a new thread as it is a network task
 */

public class APIManager {

    public ConnectivityManager cm;
    public JSONObject result;
    private URL url;
    public int paramCount = 0;
    private URLConnection urlc;
    public String query="";
    public String responseText;

    public APIManager(ConnectivityManager cm){
        this.cm = cm;
    }
    public boolean isConnected(){
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        boolean state= false;
        if(netInfo!= null) {
            state = netInfo.isConnected();
        }
        else {
            Log.e(iMeterApp.TAG,"tested connection and there is none");
        }
        return state;
    }


    public void setLink(String link){
        try {
            url = new URL(link);
            Log.d(iMeterApp.TAG,"link has been set to: "+ link);
        }
        catch (MalformedURLException e){
            Log.e(iMeterApp.TAG,"the inputed url is not well formatted");
            e.printStackTrace();
        }
    }

    public void addPostValue(String key, String value){
        Log.d(iMeterApp.TAG, " post value added key :"+ key + "value "+ value);
        try {
            if(paramCount>1){
                this.query += "&";
            }
            this.query+= URLEncoder.encode(key,"UTF-8")
                    + "=" + URLEncoder.encode(value, "UTF-8");
            paramCount++;
        }
        catch (UnsupportedEncodingException e3){
            e3.printStackTrace();
        }
    }
    public void emptyQuery(){
        this.query = "";
        paramCount = 0;
    }

    public void addServerCredentials(){
        Log.d(iMeterApp.TAG," default api credentials added");
        emptyQuery();
        setLink("http://inkanimation.com/imeterapi");
        addPostValue("platform_id","slkfjdsldjfl");
        addPostValue("secret","slkfjosfjos");
    }

    public JSONObject execute(){
        if(isConnected()) {
            try {
                Log.d(iMeterApp.TAG,"started execution");

                HttpURLConnection urlConnection = (HttpURLConnection)
                        url.openConnection();
                if(!query.isEmpty()) {
                    OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                    Log.d(iMeterApp.TAG,"starting to query :"+query);
                    wr.write(query);
                    wr.flush();
                }
                BufferedReader reader = new
                        BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = "";
                while((line = reader.readLine()) != null){
                    sb.append(line);
                }
                responseText = sb.toString();
                Log.e(iMeterApp.TAG,responseText);
                result = new JSONObject(responseText);
            } catch (IOException e4) {
                e4.printStackTrace();
            } catch (JSONException e) {
                Log.e(iMeterApp.TAG,"trying to convert unformatted json string ");
                e.printStackTrace();
            }

            return result;
        }
        else {
            //if the internet is not connected
            Log.e(iMeterApp.TAG,"internet is not connected");
            JSONObject emptyResult = new JSONObject();
            return  emptyResult;
        }
    }



}


