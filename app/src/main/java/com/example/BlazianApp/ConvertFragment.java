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

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //binding for user interface
        binding = FragmentConvertBinding.inflate(getLayoutInflater());
        userConvert = binding.userConverted;
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
                Log.e(MainActivity.class.getSimpleName(), response.body().toString());

                JsonObject json = response.body();
                JsonObject rates = json.getAsJsonObject("conversion_rates");

                String str_conversionValue = rates.get(to).toString();

                DecimalFormat formatter = new DecimalFormat("#.##");
                double conversionValue = Double.parseDouble(str_conversionValue);
                double numberToConvert = Double.parseDouble(userAmount);
                double result = numberToConvert * conversionValue;
                userConvert.setText(""+formatter.format(result));
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t)
            {}
        });
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