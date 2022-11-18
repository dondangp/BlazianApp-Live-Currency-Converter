package com.example.BlazianApp;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;


import android.view.View;


import com.example.BlazianApp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize

        MapFragment mapFragment = new MapFragment();
        RecordFragment recordFragment = new RecordFragment();
        ConvertFragment convertFragment = new ConvertFragment();
        InflationFragment inflationFragment = new InflationFragment();
        CreditsFragment creditsFragment = new CreditsFragment();


        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Change FrameLayout to Default
        replaceFragment(convertFragment);

        // Bottom Navigation View Section //
        BottomNavigationView bnv = binding.bottomNavMenu;
        bnv.setSelectedItemId(R.id.miPlaceholder);
        bnv.setBackground(null);
        bnv.setHapticFeedbackEnabled(false);
        bnv.setSoundEffectsEnabled(false);

        // BNV selector, Gradle Plugin hates switch cases, so this will look ugly.
        bnv.setOnItemSelectedListener(item->{
            if (item.getItemId() == R.id.miMap) {replaceFragment(mapFragment);}
            if (item.getItemId() == R.id.miRecords) {replaceFragment(recordFragment);}
            if (item.getItemId() == R.id.miInflationCalc) {replaceFragment(inflationFragment);}
            if (item.getItemId() == R.id.miCredits) {replaceFragment(creditsFragment);}
            if (item.getItemId() == R.id.miPlaceholder) {replaceFragment(convertFragment);}
            return true;
        });

        // Floating Action Button Section //
        FloatingActionButton fab = binding.mainfab;
        fab.setOnClickListener(view -> {bnv.setSelectedItemId(R.id.miPlaceholder); replaceFragment(convertFragment);});
    }

    // Replace Fragment function
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}