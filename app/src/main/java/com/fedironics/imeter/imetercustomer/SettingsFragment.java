package com.fedironics.imeter.imetercustomer;


import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

/**
 * Created by madunaguekenedavid on 19/07/2017.
 */


public class SettingsFragment extends Fragment{
    public     View settingsFragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        settingsFragment = inflater.inflate(R.layout.fragment_profile,container,false);
        return settingsFragment;
    }



}