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
    TextView detail_restaurant_name, detail_deadline_HH, detail_deadline_mm, detail_pickup, detail_errand_price, detail_errand_description;
    String getRestaurant, getDeadline_HH, getDeadline_mm, getPickup, getPrice, getDescription;
    Button back, delete_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        delete_btn = findViewById(R.id.detail_delete_btn);
        back = findViewById(R.id.detail_back);
        restaurant_name = findViewById(R.id.detail_restaurant_name);
        deadline_HH = findViewById(R.id.detail_deadline_HH);
        deadline_mm = findViewById(R.id.detail_deadline_mm);
        pickup = findViewById(R.id.detail_pickup);
        errand_price = findViewById(R.id.detail_errand_price);
        errand_description = findViewById(R.id.detail_errand_description);
        //객체 가져오기기
        Intent intent = getIntent();

        String detail_restaurant_name = intent.getStringExtra("restaurant_name");
        String detail_deadline_HH = intent.getStringExtra("deadline_HH");
        String detail_deadline_mm = intent.getStringExtra("deadline_mm");
        String detail_pickup = intent.getStringExtra("pickup");
        String detail_errand_price = intent.getStringExtra("errand_price");
        String detail_errand_description = intent.getStringExtra("errand_description");

        restaurant_name.setText(detail_restaurant_name);
        deadline_HH.setText(detail_deadline_HH);
        deadline_mm.setText(detail_deadline_mm);
        pickup.setText(detail_pickup);
        errand_price.setText(detail_errand_price);
        errand_description.setText(detail_errand_description);

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