package com.example.dnt;

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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MypageActivity extends AppCompatActivity {
    public String userName;
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        Button modify_profile_btn = findViewById(R.id.modify_profile_btn);
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.framelayout, homeFragment).commitAllowingStateLoss();
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        Toast.makeText(MypageActivity.this, "MypageActivity 들어왔다~ " + userName + "님!", Toast.LENGTH_SHORT).show();


        modify_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextintent = new Intent(getApplicationContext(), ModifyProfileActivity.class);
                startActivity(nextintent);
                nextintent.putExtra("userName", userName);
                finish();
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavi);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch(item.getItemId()){
                    case R.id.home:
                        Intent homeIntent = new Intent(MypageActivity.this, HomeActivity.class);
                        homeIntent.putExtra("userName", userName);
                        startActivity(homeIntent);
                        finish();
                        break;
                    case R.id.chat:
                        break;
                    case R.id.mypage:
                        break;
                }
                return true;
            }
        });
    }

}