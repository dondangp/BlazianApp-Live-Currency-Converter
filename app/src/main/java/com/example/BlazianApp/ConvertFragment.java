package com.example.BlazianApp;



import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.BlazianApp.databinding.FragmentConvertBinding;
import com.google.gson.JsonObject;

import java.text.DecimalFormat;
import java.util.Currency;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;


public class ConvertFragment extends Fragment {

    private FragmentConvertBinding binding;
    private static String BASE_URL = "https://v6.exchangerate-api.com/v6/c23a1af81c0b35138e9d1190/latest/";
    private EditText userConvert;
    private TextView userCurrency, resultCurrency, userEmoji, resultEmoji;

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //binding for user interface
        binding = FragmentConvertBinding.inflate(getLayoutInflater());
        userConvert = binding.userConverted;
        userCurrency = binding.userCurrencyText;
        resultCurrency = binding.resultCurrencyText;
        userEmoji = binding.userFlag;
        resultEmoji = binding.resultFlag;
        EditText editText = binding.userAmount;
        Spinner userSpinner = binding.userSpinner, convertSpinner = binding.convertSpinner;

        //default value or it'll error
        editText.setText("1.00");

        // User edits/clicks onto EditText.
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId){
                    case EditorInfo.IME_ACTION_DONE:
                        convert(convertSpinner.getSelectedItem().toString(),
                        userSpinner.getSelectedItem().toString(),
                        editText.getText().toString());
                        return true;
                }
                return false;
            }
        });
        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convert(convertSpinner.getSelectedItem().toString(),
                        userSpinner.getSelectedItem().toString(),
                        editText.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        convertSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convert(convertSpinner.getSelectedItem().toString(),
                        userSpinner.getSelectedItem().toString(),
                        editText.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        return binding.getRoot();
    }

    private void convert(final String to, final String from, String userAmount) {
        CurrencyAPI api = getRetrofit().create(CurrencyAPI.class);
        Call<JsonObject> call = api.getRate(from);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response)
            {

                JsonObject json = response.body();
                JsonObject rates = json.getAsJsonObject("conversion_rates");

                String str_conversionValue = rates.get(to).toString();

                DecimalFormat formatter = new DecimalFormat("#.##");
                double conversionValue = Double.parseDouble(str_conversionValue);
                double numberToConvert = Double.parseDouble(userAmount);
                double result = numberToConvert * conversionValue;
                userConvert.setText(""+formatter.format(result));
                String userC = Currency.getInstance(from).getSymbol();
                String resultC = Currency.getInstance(to).getSymbol();

                // change currency symbol
                userCurrency.setText(""+userC+"  -");
                resultCurrency.setText(""+resultC+"  -");

                // parse given strings to ISO-3166
                String toISO = to.substring(0,2);
                String fromISO = from.substring(0,2);

                // convert to unicode and turn into emoji.
                userEmoji.setText(""+countryCodeToEmoji(fromISO));
                resultEmoji.setText(""+countryCodeToEmoji(toISO));

            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t)
            {}
        });
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

    private Retrofit getRetrofit() {
        return (new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://v6.exchangerate-api.com/v6/c23a1af81c0b35138e9d1190/latest/")
                .build());
    }

    private interface CurrencyAPI {
        @GET("{currency}")
        Call<JsonObject> getRate(@Path("currency") String currency);
    }
}