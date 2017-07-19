package com.fedironics.imeter.imetercustomer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by davidity on 11/05/17.
 */

public class APIManager {
    public static final String platform_id = "slkfjdsldjfl";
    public static final String token = "slkfjosfjos";
    public boolean isConnected;
    public JSONObject result;
    public String link= "http://inkanimation.com";
    private URL url;
    public int paramCount = 0;
    private URLConnection urlc;
    public String query="";
    public int responseCode;
    public String responseText;

    public void setLink(String link){
        this.link = link;
    }
    public APIManager(ConnectivityManager cm,String link){
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        this.isConnected = netInfo.isConnected();
        if(link!=null) {
            this.link = link;
        }
        connect();
    }
    public void addPostValue(String key, String value){
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
    private void connect(){
        if(this.isConnected){
            try {
                this. url = new URL(this.link);
                this.addPostValue("platform_id",platform_id);
                this.addPostValue("secret",token);
            }
            catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e1){
                e1.printStackTrace();
            }

        }
    }
    public JSONObject execute(){
        try {
            this.urlc = url.openConnection();
            this.urlc.setConnectTimeout(5000);
            this.urlc.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(this.urlc.getOutputStream());
            wr.write( this.query );
            wr.flush();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(this.urlc.getInputStream())
            );
            StringBuilder sb = new StringBuilder();
            String line = null;
            // Read Server Response
            while((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            responseText = sb.toString();
            JSONObject result = new JSONObject(responseText);
            this.result = result;
            paramCount = 0;
        }
        catch (IOException e4){
            e4.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }



}


