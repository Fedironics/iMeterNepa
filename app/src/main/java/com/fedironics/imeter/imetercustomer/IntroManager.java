package com.fedironics.imeter.imetercustomer;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by madunaguekenedavid on 08/08/2017.
 */

public class IntroManager  {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    public IntroManager(Context context){
        this.context = context;
        pref= context.getSharedPreferences("first",0);
        editor = pref.edit();
    }
    public void setFirst(boolean isFirst){
        editor.putBoolean("check",isFirst);
        editor.commit();
    }
    public boolean Check(){
        return pref.getBoolean("check",true);
    }
}
