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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity{
    private DatabaseReference databaseReference;
    TextView restaurant_name, deadline_HH, deadline_mm, pickup, delivery_price, delivery_description, signup_nickname;
    String detail_restaurant_name, detail_deadline_HH, detail_deadline_mm, detail_pickup, detail_delivery_price, detail_delivery_description;
    Button back, delete_btn, secret_btn, chat_btn, detail_edit_btn;
    String userName;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_posts = database.getReference("posts");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //객체 생성
        signup_nickname = findViewById(R.id.signup_nickname);
        secret_btn = findViewById(R.id.secret_btn);
        delete_btn = findViewById(R.id.detail_delete_btn);
        back = findViewById(R.id.detail_back);
        chat_btn = findViewById(R.id.chat_btn);
        restaurant_name = findViewById(R.id.detail_restaurant_name);
        deadline_HH = findViewById(R.id.detail_deadline_HH);
        deadline_mm = findViewById(R.id.detail_deadline_mm);
        pickup = findViewById(R.id.detail_pickup);
        delivery_price = findViewById(R.id.detail_delivery_price);
        delivery_description = findViewById(R.id.detail_delivery_description);
        detail_edit_btn = findViewById(R.id.detail_edit_btn);
        //객체 가져오기
        Intent intent = getIntent();


        userName = intent.getStringExtra("userName");
        //userName = "alice"; //userName null로 나옴

        detail_restaurant_name = intent.getStringExtra("restaurant_name");
        detail_deadline_HH = intent.getStringExtra("deadline_HH");
        detail_deadline_mm = intent.getStringExtra("deadline_mm");
        detail_pickup = intent.getStringExtra("pickup");
        detail_delivery_price = intent.getStringExtra("delivery_price");
        detail_delivery_description = intent.getStringExtra("delivery_description");

        secret_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table_posts.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        PostInfo post = snapshot.child("1").getValue(PostInfo.class);
                        restaurant_name.setText(post.getRestaurant());
                        detail_restaurant_name = post.getRestaurant();
                        deadline_HH.setText(post.getDeadline_HH());
                        detail_deadline_HH = post.getDeadline_HH();
                        deadline_mm.setText(post.getDeadline_mm());
                        detail_deadline_mm = post.getDeadline_mm();
                        pickup.setText(post.getPickup());

                        delivery_price.setText(post.getPrice());
                        delivery_description.setText(post.getDescription());

                        signup_nickname.setText("nh");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        chat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(DetailActivity.this, ChatBeforeActivity.class);
                nextIntent.putExtra("userName", userName);
                nextIntent.putExtra("restaurant", detail_restaurant_name);
                nextIntent.putExtra("HH", detail_deadline_HH);
                nextIntent.putExtra("mm", detail_deadline_mm);
                nextIntent.putExtra("pickup", detail_pickup);


                startActivity(nextIntent);
                finish();
            }
        });

        back.setOnClickListener(onClickListener);
        detail_edit_btn.setOnClickListener(onClickListener);
        delete_btn.setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.detail_back:
                    finish();
                    DetailActivity.this.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                    break;
                case R.id.detail_edit_btn:
                    Intent intent1 = new Intent(getApplicationContext(), EditPostActivity.class);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                    finish();
                    break;
                case R.id.detail_delete_btn:
//                    table_posts.child("restaurant").removeValue();
//                    table_posts.child("deadline_HH").removeValue();
//                    table_posts.child("deadline_mm").removeValue();
//                    table_posts.child("pickup").removeValue();
//                    table_posts.child("price").removeValue();
//                    table_posts.child("description").removeValue();
//                    Toast.makeText(DetailActivity.this, "삭제 완료", Toast.LENGTH_LONG).show();
                    finish();
                    break;
            }
        }
    };



}