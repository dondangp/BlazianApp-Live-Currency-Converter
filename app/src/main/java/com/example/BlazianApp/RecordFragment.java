package com.example.BlazianApp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.BlazianApp.databinding.FragmentRecordBinding;



public class RecordFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //bindings
        FragmentRecordBinding binding = FragmentRecordBinding.inflate(getLayoutInflater());
        //initialize
        Button addRecord = binding.newRecord;
        String[] transactionRecords;
        AddRecordFragment addRecordFragment = new AddRecordFragment();

        //Button Action
        addRecord.setOnClickListener(v -> ((MainActivity) requireActivity()).replaceFragment(addRecordFragment));

        return binding.getRoot();
    }
}