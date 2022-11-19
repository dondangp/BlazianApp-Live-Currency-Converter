package com.example.BlazianApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Transaction_RecyclerViewAdapter extends RecyclerView.Adapter<Transaction_RecyclerViewAdapter.MyViewHolder> {
    RecordFragment context;
    ArrayList<TransactionModel> transactionModels;

    public Transaction_RecyclerViewAdapter(RecordFragment context, ArrayList<TransactionModel> transactionModels){
        this.context = context;
        this.transactionModels = transactionModels;
    }

    @NonNull
    @Override
    public Transaction_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context.getContext());
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new Transaction_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Transaction_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.fromAmount.setText(transactionModels.get(position).getFromCurrency());
        holder.toAmount.setText(transactionModels.get(position).getToCurrency());
        holder.fees.setText(transactionModels.get(position).getFees());
        holder.date.setText(transactionModels.get(position).getTransactionDate());
        holder.location.setText(transactionModels.get(position).getTransactionLocation());
        holder.fromFlag.setText(transactionModels.get(position).getFromFlag());
        holder.toFlag.setText(transactionModels.get(position).getToFlag());
    }

    @Override
    public int getItemCount() {
        return transactionModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView fromAmount, toAmount, fees, date, location, fromFlag, toFlag;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fromAmount = itemView.findViewById(R.id.FromCash);
            toAmount = itemView.findViewById(R.id.ToCash);
            fees = itemView.findViewById(R.id.convertFees);
            date = itemView.findViewById(R.id.Date);
            location = itemView.findViewById(R.id.Location);
            fromFlag = itemView.findViewById(R.id.FromFlag);
            toFlag = itemView.findViewById(R.id.ToFlag);

        }
    }
}
