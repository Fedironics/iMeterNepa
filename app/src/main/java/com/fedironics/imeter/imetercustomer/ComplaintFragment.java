package com.fedironics.imeter.imetercustomer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

/**
 * Created by madunaguekenedavid on 19/07/2017.
 */

public class ComplaintFragment extends Fragment {
    View myview;
    Button submit;
    public iMeterApp myApp;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.fragment_complaint,container,false);
        submit = (Button)myview.findViewById(R.id.submit_complaint);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complain();
            }
        });
        myApp = (iMeterApp)getActivity().getApplicationContext();
        return myview;
    }
    public void complain(){
        Spinner titles = (Spinner)myview.findViewById(R.id.complaint_titles);
        String title = titles.getSelectedItem().toString();
        Spinner districts = (Spinner)myview.findViewById(R.id.district);
        String district = districts.getSelectedItem().toString();
        Spinner durations = (Spinner)myview.findViewById(R.id.duration);
        String duration = durations.getSelectedItem().toString();
        Spinner frequencies = (Spinner)myview.findViewById(R.id.frequency);
        String frequency = frequencies.getSelectedItem().toString();
        EditText addressE = (EditText)myview.findViewById(R.id.addressdesc);
        String address = addressE.getText().toString();
        EditText occurenceTime = (EditText)myview.findViewById(R.id.occurence_time);
        String occurence_time = occurenceTime.getText().toString();
        EditText Description = (EditText)myview.findViewById(R.id.decription);
        String description = Description.getText().toString();
        SeekBar Severity = (SeekBar)myview.findViewById(R.id.severity);
        int severity = Severity.getProgress();
        myApp.imeterapi.addServerCredentials("complaint");
        myApp.imeterapi.addDefaultPostValues();
        myApp.imeterapi.addPostValue("title",title);
        myApp.imeterapi.addPostValue("district",district);
        myApp.imeterapi.addPostValue("duration",duration);
        myApp.imeterapi.addPostValue("frequency",frequency);
        myApp.imeterapi.addPostValue("address",address);
        myApp.imeterapi.addPostValue("occurence_time",occurence_time);
        myApp.imeterapi.addPostValue("decription",description);
        myApp.imeterapi.addPostValue("severity",String.valueOf(severity));
        myApp.imeterapi.addPostValue("method","create");
        new Thread(){
            @Override
            public void run() {
                myApp.imeterapi.execute("POST");
            }
        }.start();



        Log.d(iMeterApp.TAG,"complaining");
    }
}
