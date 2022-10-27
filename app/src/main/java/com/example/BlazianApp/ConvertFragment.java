package com.example.BlazianApp;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.health.SystemHealthManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.BlazianApp.databinding.FragmentConvertBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ConvertFragment extends Fragment {
    private FragmentConvertBinding binding;
    private static String BASE_URL = "https://v6.exchangerate-api.com/v6/c23a1af81c0b35138e9d1190/latest/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //binding for user interface
        binding = FragmentConvertBinding.inflate(inflater,container,false);

        // Making Request and Get Rates+Currencies
        new getAPI().execute();

        // get multipliers
        //String userCurrency = binding.userSpinner.getSelectedItem().toString();
        //String userSelected = binding.convertSpinner.getSelectedItem().toString();

        return inflater.inflate(R.layout.fragment_convert, container, false);
    }

    public class getAPI extends AsyncTask<Void, Void, Void> {
        String result;

        @Override
        protected Void doInBackground(Void... voids){
            URL url = null;
            try {
                url = new URL(BASE_URL + "USD");
                HttpURLConnection request = (HttpURLConnection) url.openConnection();
                request.connect();

                // Convert to JSON
                Object results = request.getContent();
                JsonObject jsonObject = JsonParser.parseReader(new InputStreamReader((InputStream) results)).getAsJsonObject();

                // Accessing object
                JsonObject conversionRates = jsonObject.get("conversion_rates").getAsJsonObject();
                // Spinner Array
                //List<String> spinnerArray =  new ArrayList<String>();
                //spinnerArray = (List<String>) conversionRates.keySet();
            } catch (MalformedURLException e) { e.printStackTrace(); }
            catch (IOException e) { e.printStackTrace(); }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}