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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by madunaguekenedavid on 19/07/2017.
 */

public class ComplaintFragment extends Fragment {
    View myview;
    Button submit;
    Spinner titles, districts, durations, frequencies;
    EditText occurenceTime, Description,addressE;
    SeekBar Severity;
    BarChart chart;
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
        titles = (Spinner)myview.findViewById(R.id.complaint_titles);
        districts = (Spinner)myview.findViewById(R.id.district);
        durations = (Spinner)myview.findViewById(R.id.duration);
        frequencies = (Spinner)myview.findViewById(R.id.frequency);
        addressE = (EditText)myview.findViewById(R.id.addressdesc);
        occurenceTime = (EditText)myview.findViewById(R.id.occurence_time);
        Severity = (SeekBar)myview.findViewById(R.id.severity);
        Description = (EditText)myview.findViewById(R.id.decription);
        setDefaults();
        return myview;
    }
    public void setDefaults(){
            addressE.setText(myApp.user.address1);

    }



    public void complain(){
        String title = titles.getSelectedItem().toString();
        String district = districts.getSelectedItem().toString();
        String duration = durations.getSelectedItem().toString();
        String frequency = frequencies.getSelectedItem().toString();
        String address = addressE.getText().toString();
        String occurence_time = occurenceTime.getText().toString();
        String description = Description.getText().toString();
        int severity = Severity.getProgress();
        iMeterApp myApp = (iMeterApp)getActivity().getApplicationContext();
        final APIManager api = myApp.getAPIManager();
        api.addServerCredentials("complaint");
        api.addDefaultPostValues();
        api.addPostValue("title",title);
        api.addPostValue("district",district);
        api.addPostValue("duration",duration);
        api.addPostValue("frequency",frequency);
        api.addPostValue("address",address);
        api.addPostValue("occurence_time",occurence_time);
        api.addPostValue("decription",description);
        api.addPostValue("severity",String.valueOf(severity));
        api.addPostValue("method","create");
        new Thread(){
            @Override
            public void run() {
                api.execute("POST");
            }
        }.start();

        Log.d(iMeterApp.TAG,"complaining");
    }
}
