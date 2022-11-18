package com.example.BlazianApp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
    LineChart linechart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInflationBinding.inflate(getLayoutInflater());
        Spinner userYear = binding.userYear, selectedYear = binding.selectedYear;
        EditText editText = binding.userAmount;

        // when user clicks/edits
        editText.setOnEditorActionListener((v, actionId, event) -> {
            switch (actionId){
                case EditorInfo.IME_ACTION_DONE:
                case EditorInfo.IME_ACTION_NEXT:
                case EditorInfo.IME_ACTION_PREVIOUS:
                    startGraph(editText, userYear, selectedYear);
                    return true;
            }
            return false;
        });

        editText.setOnFocusChangeListener((v, hasFocus) -> startGraph(editText, userYear, selectedYear));

        userYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startGraph(editText, userYear, selectedYear);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        selectedYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startGraph(editText, userYear, selectedYear);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
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
        boolean flipped;
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
        lineDataSet1.setCircleColor(Color.rgb(75,75,75));
        lineDataSet1.setValueTextSize(15f);

        lineDataSets.add(lineDataSet1);

        linechart.getDescription().setEnabled(false);
        linechart.getXAxis().setPosition(XAxis.XAxisPosition.TOP);
        linechart.getAxisRight().setEnabled(false);
        linechart.setDragEnabled(true);
        linechart.setScaleEnabled(true);
        linechart.setData(new LineData(lineDataSets));
        linechart.animateY(500);

        // change resultText
        double roundOff = Math.round(userAmount * 100.0) / 100.0;
        binding.resultAmount.setText(Double.toString(roundOff));
    }
}