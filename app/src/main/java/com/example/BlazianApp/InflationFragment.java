package com.example.BlazianApp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.BlazianApp.databinding.FragmentInflationBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;


public class InflationFragment extends Fragment {

    private FragmentInflationBinding binding;
    ArrayList barArraylist;
    LineChart linechart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInflationBinding.inflate(inflater,container,false);
        Spinner uY = binding.userYear, sY = binding.selectedYear;
        EditText et = binding.userAmount;

        // when user clicks/edits
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGraph(et, uY, sY);
            }
        });
        et.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                startGraph(et, uY, sY);
            }
        });
        uY.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startGraph(et, uY, sY);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //nothing
            }
        });
        sY.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startGraph(et, uY, sY);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //nothing
            }
        });
        return binding.getRoot();
    }

    public float getData(EditText et){
        float enterData = 0;
        String temp = et.getText().toString();
        if(!temp.isEmpty()){
            enterData = Float.parseFloat(temp);
        }
        return enterData;

    }

    public ArrayList<Entry> reverseArrayList(ArrayList<Entry> alist) {
        // start reversing arrayList
        for (int i = 0; i < alist.size() / 2; i++) {
            Entry temp = alist.get(i);
            alist.set(i, alist.get(alist.size() - i - 1));
            alist.set(alist.size() - i - 1, temp);
        }
        // Return the reversed arraylist
        return alist;
    }

    public void startGraph(EditText et, Spinner uY, Spinner sY){
        float userAmount = getData(et);
        boolean flipped = false;
        int userYear, selectedYear, amountYears,startingIndex;

        //get data
        String temp = uY.getSelectedItem().toString();
        userYear = Integer.parseInt(temp);
        temp = sY.getSelectedItem().toString();
        selectedYear = Integer.parseInt(temp);

        //get years to cycle through
        amountYears = (userYear < selectedYear) ?  selectedYear-userYear : userYear-selectedYear;

        // initialize graph
        linechart = binding.idGraphView;
        ArrayList<String> xAxes = new ArrayList<>();
        ArrayList<Entry> yAxes = new ArrayList<>();
        String[] yrArray = getResources().getStringArray(R.array.InflationYears);
        String[] rateArray = getResources().getStringArray(R.array.InflationPercentage);

        startingIndex = 2021-userYear;
        // add first point
        yAxes.add(new Entry(Float.parseFloat(yrArray[startingIndex]), userAmount));

        XAxis xAxis = linechart.getXAxis();
        xAxis.setGranularity(1f);

        if(userYear < selectedYear){
            for(int i = startingIndex-1; i >= (startingIndex-amountYears); i--){
                // Calculate inflation rate
                float rate = Float.parseFloat(rateArray[i]);
                float tmp = userAmount*rate;
                userAmount = userAmount+tmp;

                // put into graph
                yAxes.add(new Entry(Float.parseFloat(yrArray[i]),userAmount));
            }
            flipped = false;
        }else{
            for(int i = startingIndex+1; i <= (startingIndex+amountYears); i++){
                // Calculate inflation rate
                float rate = Float.parseFloat(rateArray[i]);
                float tmp = userAmount*rate;
                userAmount = userAmount-tmp;

                // put into graph
                yAxes.add(new Entry(Float.parseFloat(yrArray[i]),userAmount));
            }
            flipped = true;
        }
        if(flipped){yAxes = reverseArrayList(yAxes); }

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        LineDataSet lineDataSet1 = new LineDataSet(yAxes, "Purchasing Power");
        lineDataSet1.setColor(Color.rgb(75,75,75));

        lineDataSets.add(lineDataSet1);

        linechart.getDescription().setEnabled(false);
        linechart.getXAxis().setPosition(XAxis.XAxisPosition.TOP);
        linechart.getAxisRight().setEnabled(false);
        linechart.setDragEnabled(true);
        linechart.setScaleEnabled(false);
        linechart.setData(new LineData(lineDataSets));
        linechart.animateY(500);

        // change resultText
        double roundOff = Math.round(userAmount * 100.0) / 100.0;
        binding.resultAmount.setText(Double.toString(roundOff));
    }
}