package com.example.BlazianApp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.BlazianApp.databinding.FragmentRecordBinding;

import java.sql.Array;
import java.util.ArrayList;


public class RecordFragment extends Fragment {
    ArrayList<TransactionModel> transactionModels = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //bindings
        FragmentRecordBinding binding = FragmentRecordBinding.inflate(getLayoutInflater());
        //initialize
        Button addRecord = binding.newRecord;
        AddRecordFragment addRecordFragment = new AddRecordFragment();
        RecyclerView recyclerView = binding.mRecyclerView;

        //Button Action
        addRecord.setOnClickListener(v -> ((MainActivity) requireActivity()).replaceFragment(addRecordFragment, "fragAddRecord"));

        // Adapter initialize and attach
        Transaction_RecyclerViewAdapter adapter = new Transaction_RecyclerViewAdapter(this, transactionModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return binding.getRoot();
    }

    public void setUpTransactions(String date, String location, String from, String to,
                                  String fees, String fromFlag, String toFlag){
        transactionModels.add(new TransactionModel(date, location, from, to, fees, fromFlag, toFlag));
    }
}