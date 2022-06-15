package com.example.dnt;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class MypageActivity extends AppCompatActivity {
    public String getUsername;
    TextView userName;
    Button modify_profile_btn;
    View view;
    FragmentManager fragmentManager = getSupportFragmentManager();




    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);


        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.framelayout, homeFragment).commitAllowingStateLoss();
        Intent intent = getIntent();
        getUsername = intent.getStringExtra("userName");
        Toast.makeText(MypageActivity.this, "MypageActivity 들어왔다~ " + getUsername + "님!", Toast.LENGTH_SHORT).show();

        modify_profile_btn = findViewById(R.id.modify_profile_btn);
        userName = findViewById(R.id.username);
        userName.setText(getUsername);  // 사용자 닉네임 보이기!

        modify_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextintent = new Intent(getApplicationContext(), ModifyProfileActivity.class);
                startActivity(nextintent);
                nextintent.putExtra("userName", getUsername);
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
                        Intent homeIntent = new Intent(MypageActivity.this, MainActivity.class);
                        homeIntent.putExtra("userName", getUsername);
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