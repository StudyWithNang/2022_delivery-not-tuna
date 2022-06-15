package com.example.dnt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {
    public String userName;
    Button add_btn;
    FragmentManager fragmentManager = getSupportFragmentManager();
    //homeFragment homeFragment = new homeFragment();
    //mypageFragment mypageFragment;
    //ModifyingProfileFragment modifyingProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        add_btn = findViewById(R.id.home_add_post_btn);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        Toast.makeText(HomeActivity.this, "HomeActivity 입성!! " + userName + "님!", Toast.LENGTH_SHORT).show();

        //RecyclerView recyclerView = findViewById(R.id.homeRecyclerView);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavi);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch(item.getItemId()){
                    case R.id.home:
                        break;
                    case R.id.chat:
                        break;
                    case R.id.mypage:
                        Intent homeIntent = new Intent(HomeActivity.this, MypageActivity.class);
                        homeIntent.putExtra("userName", userName);
                        startActivity(homeIntent);
                        finish();
                        break;
                }
                return true;
            }
        });


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextintent = new Intent(getApplicationContext(), AddPostActivity.class);
                startActivity(nextintent);
                nextintent.putExtra("userName", nextintent);
                finish();

            }
        });
    }
}