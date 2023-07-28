package com.example.leachpeach;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.util.Log;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("STARTING NEW COMPILATION");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // For some reason this block of code causes the app to crash when trying to switch fragments
        // Without it, the app doesnt crash, but it also doesn't switch fragments either
        // This code could very well not be the issue, but blocking it out was a band-aid

        /*
        if (savedInstanceState == null) {
            System.out.println("DINK");

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MainFragment())
                    .commit();
        }
         */

        // 1. Nav bar is linking to the wrong fragments
        // 2. Nav bar is not switching
        // 3. Middle item doesn't hover at all

        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(navView, navController);
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("MainActivity", "onBackPressed() called, back stack count: " + getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
