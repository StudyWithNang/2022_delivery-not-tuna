package com.example.dnt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AddPostActivity extends AppCompatActivity {

    Intent intent;
    EditText restaurant_name, deadline_HH, deadline_mm, pickup, errand_price, errand_description, userId;
    Button back, post_btn;
    int postId = 1;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost);

        restaurant_name = findViewById(R.id.restaurant_name);
        deadline_HH = findViewById(R.id.deadline_HH);
        deadline_mm = findViewById(R.id.deadline_mm);
        pickup = findViewById(R.id.pickup);
        errand_price = findViewById(R.id.errand_price);
        errand_description = findViewById(R.id.errand_description);
        post_btn = findViewById(R.id.post_btn);
        userId = findViewById(R.id.signup_nickname);
        back = findViewById(R.id.post_back);

        //firebase 정의
        mDatabase = FirebaseDatabase.getInstance().getReference();

        readUser();

        back.setOnClickListener(OnClickListener);
        post_btn.setOnClickListener(OnClickListener);
    }
    View.OnClickListener OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.post_back:
                    finish();
                    break;
                case R.id.post_btn:
                    String getRestaurant = restaurant_name.getText().toString();
                    String getDeadline_HH = deadline_HH.getText().toString();
                    String getDeadline_mm = deadline_mm.getText().toString();
                    String getPickup = pickup.getText().toString();
                    String getPrice = errand_price.getText().toString();
                    String getDescription = errand_description.getText().toString();

                    //hashmap 만들기
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("restaurant", getRestaurant);
                    result.put("deadline_HH", getDeadline_HH);
                    result.put("deadline_mm", getDeadline_mm);
                    result.put("pickup", getPickup);
                    result.put("errand_price", getPrice);
                    result.put("errand_description", getDescription);

                    writeNewUser(postId, getRestaurant, getDeadline_HH, getDeadline_mm, getPickup, getPrice, getDescription);
                    postId++;
                    break;
            }
        }
    };

    private void writeNewUser(int postId, String restaurant, String deadline_HH, String deadline_mm, String pickup, String price, String description) {

        String key = mDatabase.child("posts").push().getKey();
        PostInfo post = new PostInfo(restaurant, deadline_HH, deadline_mm, pickup, price, description);

        mDatabase.child("posts").child(String.valueOf(postId)).setValue(post) //db에 순차적으로 posts - 1 - email, name, pw 들어감
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Toast.makeText(AddPostActivity.this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(AddPostActivity.this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void readUser(){
        mDatabase.child("posts").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.getValue(PostInfo.class) != null){
                    PostInfo post = dataSnapshot.getValue(PostInfo.class);
                    Log.w("FireBaseData", "getData" + post.toString());
                } else {
                    Toast.makeText(AddPostActivity.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}