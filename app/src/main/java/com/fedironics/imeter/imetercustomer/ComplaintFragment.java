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

public class ComplaintFragment extends Fragment {
    View myview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.fragment_complaint,container,false);
        return myview;
    }
}
