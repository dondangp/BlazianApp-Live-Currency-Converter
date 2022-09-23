package com.example.BlazianApp;

import android.os.Bundle;

import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;

import com.example.BlazianApp.ui.main.SectionsPagerAdapter;
import com.example.BlazianApp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize
        MapFragment mapFragment = new MapFragment();
        RecordFragment recordFragment = new RecordFragment();
        ConvertFragment convertFragment = new ConvertFragment();
        InflationFragment inflationFragment = new InflationFragment();
        CreditsFragment creditsFragment = new CreditsFragment();


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Change FrameLayout to Default
        replaceFragment(convertFragment);

        // Bottom Navigation View Section //
        BottomNavigationView bnv = binding.bottomNavMenu;
        bnv.setSelectedItemId(R.id.miPlaceholder);
        bnv.setBackground(null);
        bnv.setHapticFeedbackEnabled(false);
        bnv.setSoundEffectsEnabled(false);

        // BNV selector
        bnv.setOnItemSelectedListener(item->{
            switch(item.getItemId()){
                    case R.id.miMap: replaceFragment(mapFragment); break;
                    case R.id.miRecords: replaceFragment(recordFragment); break;
                    case R.id.miInflationCalc: replaceFragment(inflationFragment); break;
                    case R.id.miCredits: replaceFragment(creditsFragment); break;
                    case R.id.miPlaceholder: replaceFragment(convertFragment);
            }
            return true;
        });

        // Floating Action Button Section //
        FloatingActionButton fab = binding.mainfab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {bnv.setSelectedItemId(R.id.miPlaceholder); replaceFragment(convertFragment);}
        });
    }

    // Replace Fragment function
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}