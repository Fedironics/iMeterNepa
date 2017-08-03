package com.fedironics.imeter.imetercustomer;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by madunaguekenedavid on 19/07/2017.
 */

public class DashboardFragment extends Fragment {
   View myFrag ;
    BarChart chart;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFrag = inflater.inflate(R.layout.dashboard,container,false);


        TextView tx = (TextView)myFrag.findViewById(R.id.balance_text);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),
                "raleway_thin.ttf");
        tx.setTypeface(custom_font);

        chart = (BarChart) myFrag.findViewById(R.id.bar_chart);
        initChart();

        return myFrag;
    }



    public void initChart(){
        ArrayList<BarEntry> BarEntry = new ArrayList<>();
        BarEntry.add(new BarEntry(2f, 0));
        BarEntry.add(new BarEntry(4f, 1));
        BarEntry.add(new BarEntry(6f, 2));
        BarEntry.add(new BarEntry(8f, 3));
        BarEntry.add(new BarEntry(7f, 4));
        BarEntry.add(new BarEntry(3f, 5));

        BarDataSet dataSet = new BarDataSet(BarEntry, "Useages");

        ArrayList<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        BarData data = new BarData(dataSet);

        chart.setDrawGridBackground(false);


        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f); // this replaces setStartAtZero(true)

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);

        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        chart.setData(data);
        chart.setDescription("Billing History");

    }
}
