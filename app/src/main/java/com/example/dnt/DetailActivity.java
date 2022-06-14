package com.example.dnt;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity{
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    TextView restaurant_name, deadline_HH, deadline_mm, pickup, errand_price, errand_description;
    String getRestaurant, getDeadline_HH, getDeadline_mm, getPickup, getPrice, getDescription;
    Button back, delete_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();

        delete_btn = findViewById(R.id.detail_delete_btn);
        back = findViewById(R.id.detail_back);
        restaurant_name = findViewById(R.id.restaurant_name);
        deadline_HH = findViewById(R.id.deadline_HH);
        deadline_mm = findViewById(R.id.deadline_mm);
        pickup = findViewById(R.id.pickup);
        errand_price = findViewById(R.id.errand_price);
        errand_description = findViewById(R.id.errand_description);


        String getRestaurant = intent.getStringExtra("restaurant_name");
        String getDeadline_HH = intent.getStringExtra("deadline_HH");
        String getDeadline_mm = intent.getStringExtra("deadline_mm");
        String getPickup = intent.getStringExtra("pickup");
        String getPrice = intent.getStringExtra("errand_price");
        String getDescription = intent.getStringExtra("errand_description");

//        restaurant_name.setText(getRestaurant);
//        deadline_HH.setText(getDeadline_HH);
//        deadline_mm.setText(getDeadline_mm);
//        pickup.setText(getPickup);
//        errand_price.setText(getPrice);
//        errand_description.setText(getDescription);

        back.setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.detail_back:
                    finish();
                    DetailActivity.this.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                    break;
            }
        }
    };
}