package com.example.BlazianApp;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;




import com.example.BlazianApp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    MapFragment mapFragment = new MapFragment();
    RecordFragment recordFragment = new RecordFragment();
    ConvertFragment convertFragment = new ConvertFragment();
    InflationFragment inflationFragment = new InflationFragment();
    CreditsFragment creditsFragment = new CreditsFragment();

    Setpin setpin = new Setpin();
    Splash splash = new Splash();
    Enterpin enterpin = new Enterpin();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        // Change FrameLayout to Default
        replaceFragment(convertFragment, "fragConvert");

        // Bottom Navigation View Section //
        BottomNavigationView bnv = binding.bottomNavMenu;
        bnv.setSelectedItemId(R.id.miPlaceholder);
        bnv.setBackground(null);
        bnv.setHapticFeedbackEnabled(false);
        bnv.setSoundEffectsEnabled(false);

        // BNV selector, Gradle Plugin hates switch cases, so this will look ugly.
        bnv.setOnItemSelectedListener(item->{
            if (item.getItemId() == R.id.miMap) {replaceFragment(mapFragment, "fragMap");}
            if (item.getItemId() == R.id.miRecords) {replaceFragment(recordFragment, "fragRecord");}
            if (item.getItemId() == R.id.miInflationCalc) {replaceFragment(inflationFragment, "fragInflation");}
            if (item.getItemId() == R.id.miCredits) {replaceFragment(creditsFragment, "fragCredits");}
            if (item.getItemId() == R.id.miPlaceholder) {replaceFragment(convertFragment, "fragConvert");}
            return true;
        });

        // Floating Action Button Section //
        FloatingActionButton fab = binding.mainfab;
        fab.setOnClickListener(view -> {bnv.setSelectedItemId(R.id.miPlaceholder); replaceFragment(convertFragment, "fragConvert");});
    }

    // Replace Fragment function
    public void replaceFragment(Fragment fragment, String tagFrag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container, fragment, tagFrag);
        fragmentTransaction.commit();
    }

    public int getFragId(Fragment fragment){
        return fragment.getId();
    }
}