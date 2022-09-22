package com.example.BlazianApp;

import android.os.Bundle;

import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.BlazianApp.ui.main.SectionsPagerAdapter;
import com.example.BlazianApp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ViewerPage Section //
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);

        // Bottom Navigation View Section //
        BottomNavigationView bnv = binding.bottomNavMenu;
        bnv.setSelectedItemId(R.id.miPlaceholder);
        bnv.setBackground(null);
        bnv.setHapticFeedbackEnabled(false);
        bnv.setSoundEffectsEnabled(false);

        // Floating Action Button Section //
        FloatingActionButton fab = binding.mainfab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: insert code later
            }
        });
        //LabLayout tabs = binding.tabs;
        //tabs.setupWithViewPager(viewPager);
    }
}