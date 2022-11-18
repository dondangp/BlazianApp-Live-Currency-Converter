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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.BlazianApp.databinding.FragmentAddRecordBinding;
import com.example.BlazianApp.databinding.FragmentConvertBinding;

import java.util.Currency;
import java.util.Objects;


public class AddRecordFragment extends Fragment {
    private TextView userTextSymbol, userEmoji, resultEmoji;

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // binding
        FragmentAddRecordBinding binding = FragmentAddRecordBinding.inflate(getLayoutInflater());
        // initialize
        ImageButton backButton = binding.back;
        Button confirmButton = binding.confirm;
        userTextSymbol = binding.userCurrencyText;
        userEmoji = binding.fromFlag;
        resultEmoji = binding.toFlag;
        Spinner toCurrency = binding.toCurrency, fromCurrency = binding.fromCurrency;


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

        backButton.setOnClickListener(v -> {
            FragmentManager fm = requireActivity().getSupportFragmentManager();
            fm.popBackStack();
        });

        return binding.getRoot();
    }

    private void symbol(final String to, final String from) {
        String resultC = Currency.getInstance(from).getSymbol();

        // change currency symbol
        userTextSymbol.setText(""+resultC);

        // parse given strings to ISO-3166
        String toISO = to.substring(0,2);
        String fromISO = from.substring(0,2);

        // convert to unicode and turn into emoji.

        userEmoji.setText(""+countryCodeToEmoji(fromISO));
        resultEmoji.setText(""+countryCodeToEmoji(toISO));
    }

    public String countryCodeToEmoji(String code) {
        // offset between uppercase ASCII and regional indicator symbols
        int OFFSET = 127397;

        // validate code
        if(code == null || code.length() != 2) {
            return "";
        }
        //fix for uk -> gb
        if (code.equalsIgnoreCase("uk")) {
            code = "gb";
        }
        // convert code to uppercase
        code = code.toUpperCase();
        StringBuilder emojiStr = new StringBuilder();
        // loop all characters
        for (int i = 0; i < code.length(); i++) {
            emojiStr.appendCodePoint(code.charAt(i) + OFFSET);
        }
        // return emoji
        return emojiStr.toString();
    }
}