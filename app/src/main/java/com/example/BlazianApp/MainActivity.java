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
    public MapFragment mapFragment = new MapFragment();
    public ConvertFragment convertFragment = new ConvertFragment();
    public RecordFragment recordFragment = new RecordFragment();
    public InflationFragment inflationFragment = new InflationFragment();
    public CreditsFragment creditsFragment = new CreditsFragment();

    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        instance = this;


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
            if (item.getItemId() == R.id.miRecords) {
                Intent intent = new Intent(getApplicationContext(), Splash.class);
                startActivity(intent);
            }
            if (item.getItemId() == R.id.miInflationCalc) {replaceFragment(inflationFragment, "fragInflation");}
            if (item.getItemId() == R.id.miCredits) {replaceFragment(creditsFragment, "fragCredits");}
            if (item.getItemId() == R.id.miPlaceholder) {replaceFragment(convertFragment, "fragConvert");}
            return true;
        });

        // Floating Action Button Section //
        FloatingActionButton fab = binding.mainfab;
        fab.setOnClickListener(view -> {bnv.setSelectedItemId(R.id.miPlaceholder); replaceFragment(convertFragment, "fragConvert");});
    }

    public static MainActivity getInstance() {
        return instance;
    }

    // Replace Fragment function
    public void replaceFragment(Fragment fragment, String tagFrag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container, fragment, tagFrag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public int getFragId(Fragment fragment){
        return fragment.getId();
    }
}