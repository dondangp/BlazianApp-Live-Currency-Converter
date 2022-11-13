package com.example.BlazianApp;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


import com.example.BlazianApp.databinding.FragmentCreditsBinding;


public class CreditsFragment extends Fragment {
    private FragmentCreditsBinding binding;
    private WebView webView;


    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // bindings
        binding = FragmentCreditsBinding.inflate(getLayoutInflater());
        webView = binding.WebCredits;

        // get html
        String content = getString(R.string.credits_txt);
        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);

        return binding.getRoot();
    }
}