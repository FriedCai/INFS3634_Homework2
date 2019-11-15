package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity  {
    private HomeFragment homeFragment;
    private StarFragment starFragment;
    private BottomNavigationView llBottom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llBottom = findViewById(R.id.ll_bottom);
        llBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId()==R.id.menu_search){
                    switchFragment(0);
                    return true;
                }else  if (menuItem.getItemId()==R.id.menu_favourite){
                    switchFragment(1);
                    return true;
                }
                return false;
            }
        });
        switchFragment(0);
    }



    private void switchFragment(int i) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            transaction.add(R.id.fl_contain, homeFragment);
        }
        if (starFragment == null) {
            starFragment = new StarFragment();
            transaction.add(R.id.fl_contain, starFragment);

        }
        transaction.hide(starFragment);
        transaction.hide(homeFragment);
        if (i==0) {

            transaction.show(homeFragment);

        }else{

            transaction.show(starFragment);

        }
        transaction.commit();
    }
}
