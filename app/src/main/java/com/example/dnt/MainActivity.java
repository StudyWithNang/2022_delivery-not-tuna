package com.example.dnt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    homeFragment homeFragment = new homeFragment();
    String userName;

    public static Context context_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");

        context_main = this;

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, homeFragment).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavi);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch(item.getItemId()){
                    case R.id.home:
                        if(homeFragment == null){
                            homeFragment = new homeFragment();
                            fragmentManager.beginTransaction().add(R.id.framelayout, homeFragment).commit();

                        }else{
                            fragmentManager.beginTransaction().show(homeFragment).commit();
                        }
                        break;
                    case R.id.chat:
                        break;
                    case R.id.mypage:
                        Intent homeIntent = new Intent(MainActivity.this, MypageActivity.class);
                        homeIntent.putExtra("userName", userName);
                        startActivity(homeIntent);
                        finish();
                        break;

                }
                return true;
            }
        });

    }


}