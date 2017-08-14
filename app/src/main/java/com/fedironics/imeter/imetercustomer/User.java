package com.fedironics.imeter.imetercustomer;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by madunaguekenedavid on 13/08/2017.
 */

public class User {
    public String accountNumber;
    public String address1;
    public String businessDistrict;
    public int customerId;
    public String customerName;
    public String user_id;
    public String phone;
    public String email;
    private String password;
    public String name;
    private boolean isUserGotten = false;
    private boolean isEEDCGotten = false;

public boolean isUserGotten(){
    return isUserGotten ;
}


    public void setEEDC(JSONObject EEDCInfo) {
        //Parse the EEDC gotten data
        try {
            String result = EEDCInfo.getString("result");
            JSONObject resultObject = new JSONObject(result);
            JSONArray accountDetailArray = resultObject.getJSONArray("accounts");
            JSONObject accountDetail = accountDetailArray.getJSONObject(0);


            this.accountNumber = accountDetail.getString("accountNumber");
            this.address1 = accountDetail.getString("address1");
            this.businessDistrict = accountDetail.getString("businessDistrict");
            this.customerId = accountDetail.getInt("customerId");
            this.customerName = accountDetail.getString("name");

        } catch (JSONException e) {
            Log.e(iMeterApp.TAG, "field doesnt exist in json object");
            e.printStackTrace();
        }
    }
    public String setUser(JSONObject UserInfo){
        try {
            this.name = UserInfo.getString("name");
            this.email = UserInfo.getString("email");
            this.password= UserInfo.getString("password");
            this.phone= UserInfo.getString("phone");
            this.user_id = UserInfo.getString("id");
            this.isUserGotten = true;
            return "User Successfully Validated";
        }
        catch (JSONException e){
            Log.e(iMeterApp.TAG,"Json exception parsing userinfo");
            try{
                e.printStackTrace();
                return UserInfo.getString("error");
            }
            catch (JSONException e1){
                Log.e(iMeterApp.TAG,UserInfo.toString());
                e1.printStackTrace();
                return "strange json format";
            }
        }
    }
}
