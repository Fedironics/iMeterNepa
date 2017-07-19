package com.fedironics.imeter.imetercustomer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by madunaguekenedavid on 19/07/2017.
 */

public class DashboardFragment extends Fragment {
   View myFrag ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFrag = inflater.inflate(R.layout.dashboard,container,false);
        return myFrag;
    }
}
