package com.example.BlazianApp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.BlazianApp.databinding.FragmentRecordBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Objects;


public class RecordFragment extends Fragment implements RecyclerViewInterface {
    ArrayList<TransactionModel> transactionModels = new ArrayList<>();
    Transaction_RecyclerViewAdapter adapter;

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
        //load data
        loadData();

        //Button Action
        addRecord.setOnClickListener(v -> ((MainActivity) requireActivity()).replaceFragment(addRecordFragment, "fragAddRecord"));

        // Adapter initialize and attach
        adapter = new Transaction_RecyclerViewAdapter(this, transactionModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return binding.getRoot();
    }

    public void setUpTransactions(String date, String location, String from, String to,
                                  String fees, String fromFlag, String toFlag){
        transactionModels.add(new TransactionModel(date, location, from, to, fees, fromFlag, toFlag));
        //save data
        saveData();
    }

    @Override
    public void onItemLongClick(int position) {
        transactionModels.remove(position);
        adapter.notifyItemRemoved(position);
        //save data
        saveData();
    }

    private void saveData(){
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("shared prefences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(transactionModels);
        editor.putString("Transaction List", json);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("shared prefences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Transaction List", "");
        Type type = new TypeToken<ArrayList<TransactionModel>>() {}.getType();
        transactionModels = gson.fromJson(json, type);
        if (transactionModels == null){
            transactionModels = new ArrayList<>();
        }
    }
}