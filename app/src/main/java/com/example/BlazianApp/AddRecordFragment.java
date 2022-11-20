package com.example.BlazianApp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.BlazianApp.databinding.FragmentAddRecordBinding;

import java.util.Currency;
import java.util.Objects;


public class AddRecordFragment extends Fragment {
    private TextView fromTextSymbol, fromEmoji, toTextSymbol, toEmoji;

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // binding
        FragmentAddRecordBinding binding = FragmentAddRecordBinding.inflate(getLayoutInflater());
        // initialize
        ImageButton backButton = binding.back;
        Button confirmButton = binding.confirm;
        Spinner toCurrency = binding.toCurrency, fromCurrency = binding.fromCurrency;
        EditText userAmount = binding.userAmount, convertedAmount = binding.convertedAmount,
                userFees = binding.userFees, enteredDate = binding.enteredDate, givenLocation = binding.enteredLocation;


        fromTextSymbol = binding.fromCurrencyText;
        toTextSymbol = binding.toCurrencyText;
        fromEmoji = binding.fromFlag;
        toEmoji = binding.toFlag;


        // listeners
        toCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                symbol(toCurrency.getSelectedItem().toString(), fromCurrency.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        fromCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                symbol(toCurrency.getSelectedItem().toString(), fromCurrency.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        // Buttons to confirm/back out
        backButton.setOnClickListener(v -> {
            FragmentManager fm = requireActivity().getSupportFragmentManager();
            fm.popBackStack();
        });

        confirmButton.setOnClickListener(v -> {
            //add information to model
            FragmentManager fm = getParentFragmentManager();
            RecordFragment fragment = (RecordFragment)fm.findFragmentByTag("fragRecord");
            Objects.requireNonNull(fragment).setUpTransactions(
                    enteredDate.getText().toString(),
                    givenLocation.getText().toString(),
                    userAmount.getText().toString(),
                    convertedAmount.getText().toString(),
                    userFees.getText().toString(),
                    fromEmoji.getText().toString(),
                    toEmoji.getText().toString());

            fm.popBackStack();
        });

        return binding.getRoot();
    }


    private void symbol(final String to, final String from) {
        String resultC = Currency.getInstance(to).getSymbol();
        String userC = Currency.getInstance(from).getSymbol();

        // change currency symbol
        fromTextSymbol.setText(""+userC);
        toTextSymbol.setText(""+resultC);

        // parse given strings to ISO-3166
        String toISO = to.substring(0,2);
        String fromISO = from.substring(0,2);

        // convert to unicode and turn into emoji.
        FragmentManager fm = getParentFragmentManager();
        ConvertFragment fragment = (ConvertFragment)fm.findFragmentByTag("fragConvert");

        fromEmoji.setText(""+ Objects.requireNonNull(fragment).countryCodeToEmoji(fromISO));
        toEmoji.setText(""+Objects.requireNonNull(fragment).countryCodeToEmoji(toISO));
    }

}