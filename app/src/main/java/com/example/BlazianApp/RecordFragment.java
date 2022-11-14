package com.example.BlazianApp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.BlazianApp.databinding.FragmentRecordBinding;

public class RecordFragment extends Fragment {
    private FragmentRecordBinding binding;
    private Button addRecord;


    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //bindings
        binding = FragmentRecordBinding.inflate(getLayoutInflater());
        //initialize
        addRecord = binding.newRecord;
        String[] transactionRecords;

        //Button Action
        addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddRecordActivity.class));
            }
        });


        return binding.getRoot();
    }
}