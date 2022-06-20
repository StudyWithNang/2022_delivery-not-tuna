package com.example.dnt;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity{
    private DatabaseReference databaseReference;
    TextView writer, restaurant_name, deadline_HH, deadline_mm, pickup, delivery_price, delivery_description, user_nickname;
    String detail_writer, detail_restaurant_name, detail_deadline_HH, detail_deadline_mm, detail_pickup, detail_delivery_price, detail_delivery_description;
    Button back, delete_btn, secret_btn, chat_btn, detail_edit_btn;
    String userName;
    int postId;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_posts = database.getReference("posts");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //객체 생성
        user_nickname = findViewById(R.id.nickname);
        secret_btn = findViewById(R.id.secret_btn);
        delete_btn = findViewById(R.id.detail_delete_btn);
        back = findViewById(R.id.detail_back);
        chat_btn = findViewById(R.id.chat_btn);
        writer = findViewById(R.id.writer);
        restaurant_name = findViewById(R.id.detail_restaurant_name);
        deadline_HH = findViewById(R.id.detail_deadline_HH);
        deadline_mm = findViewById(R.id.detail_deadline_mm);
        pickup = findViewById(R.id.detail_pickup);
        delivery_price = findViewById(R.id.detail_delivery_price);
        delivery_description = findViewById(R.id.detail_delivery_description);
        detail_edit_btn = findViewById(R.id.detail_edit_btn);


        Intent intent = getIntent();
        userName = ((MainActivity)MainActivity.context_main).userName;

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
                        writer.setText(post.getWriter());
                        detail_writer = post.getWriter();
                        restaurant_name.setText(post.getRestaurant());
                        detail_restaurant_name = post.getRestaurant();
                        deadline_HH.setText(post.getDeadline_HH());
                        detail_deadline_HH = post.getDeadline_HH();
                        deadline_mm.setText(post.getDeadline_mm());
                        detail_deadline_mm = post.getDeadline_mm();
                        pickup.setText(post.getPickup());
                        detail_pickup = post.getPickup();

                        delivery_price.setText(post.getPrice());
                        delivery_description.setText(post.getDescription());

                        //signup_nickname.setText("nh");
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
                nextIntent.putExtra("writer", detail_writer);
                nextIntent.putExtra("restaurant", detail_restaurant_name);
                nextIntent.putExtra("HH", detail_deadline_HH);
                nextIntent.putExtra("mm", detail_deadline_mm);
                nextIntent.putExtra("pickup", detail_pickup);

                startActivity(nextIntent);
                //finish();
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
                    database.getReference().child("posts").child("1").removeValue();
                    Toast.makeText(DetailActivity.this, "삭제 완료", Toast.LENGTH_LONG).show();
                    DetailActivity.this.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                    finish();
                    break;
            }
        }
    };

}