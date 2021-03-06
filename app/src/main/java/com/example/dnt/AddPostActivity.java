package com.example.dnt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
    String userName;
    EditText restaurant_name, deadline_HH, deadline_mm, pickup, price, description, userId;
    Button back, post_btn;
    int postId = 1;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;
    final DatabaseReference table_users = database.getReference("users");
    final DatabaseReference table_chats = database.getReference("chats");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost);

        Intent intent = getIntent();
        userName = ((MainActivity)MainActivity.context_main).userName;

        restaurant_name = findViewById(R.id.restaurant_name);
        deadline_HH = findViewById(R.id.deadline_HH);
        deadline_mm = findViewById(R.id.deadline_mm);
        pickup = findViewById(R.id.pickup);
        price = findViewById(R.id.delivery_price);
        description = findViewById(R.id.delivery_description);

        post_btn = findViewById(R.id.post_btn);
        userId = findViewById(R.id.signup_nickname);
        back = findViewById(R.id.post_back);

        //firebase ??????
        mDatabase = FirebaseDatabase.getInstance().getReference();


        readUser();
        readChat();

        back.setOnClickListener(OnClickListener);
        post_btn.setOnClickListener(OnClickListener);
    }
    View.OnClickListener OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.post_back:
                    Intent homeIntent = new Intent(AddPostActivity.this, MainActivity.class);
                    homeIntent.putExtra("userName", userName);
                    startActivity(homeIntent);
                    finish();
                    break;
                case R.id.post_btn:
                    String getRestaurant = restaurant_name.getText().toString();
                    String getDeadline_HH = deadline_HH.getText().toString();
                    String getDeadline_mm = deadline_mm.getText().toString();
                    String getPickup = pickup.getText().toString();
                    String getPrice = price.getText().toString();
                    String getDescription = description.getText().toString();
                    String writer = userName;

                    //hashmap ?????????
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("restaurant", getRestaurant);
                    result.put("deadline_HH", getDeadline_HH);
                    result.put("deadline_mm", getDeadline_mm);
                    result.put("pickup", getPickup);
                    result.put("price", getPrice);
                    result.put("description", getDescription);
                    writeNewUser(writer, getRestaurant, getDeadline_HH, getDeadline_mm, getPickup, getPrice, getDescription);

                    // ????????? ?????????
                    writeNewChat(1, userName, 1);

                    postId++;
                    break;
            }
        }
    };

    private void writeNewUser(String writer, String restaurant, String deadline_HH, String deadline_mm, String pickup, String price, String description) {

        String key = mDatabase.child("posts").push().getKey();
        PostInfo post = new PostInfo(writer, restaurant, deadline_HH, deadline_mm, pickup, price, description);

        mDatabase.child("posts").child("1").setValue(post) //db??? ??????????????? posts - 1 - email, name, pw ?????????
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Toast.makeText(AddPostActivity.this, "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(AddPostActivity.this, "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(AddPostActivity.this, "????????? ??????...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    private void writeNewChat(int postId, String userName, int count) {
        Toast.makeText(AddPostActivity.this, "writeNewChat!"+userName, Toast.LENGTH_SHORT).show();

        table_chats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(AddPostActivity.this, "table_chats!", Toast.LENGTH_SHORT).show();
                MessageItem chat = snapshot.child("chatroom1").getValue(MessageItem.class);

                Map<String, Object> mapChat = new HashMap<String, Object>();
                mapChat.put("users", count);
                mapChat.put("postnum", postId);
                // ?????? postId
                mDatabase.child("chats").child("chatroom1").updateChildren(mapChat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddPostActivity.this, "error1111111!", Toast.LENGTH_SHORT).show();
            }
        });
        table_users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Toast.makeText(AddPostActivity.this, "table_users!", Toast.LENGTH_SHORT).show();
                UserInfo user = snapshot.child(userName).getValue(UserInfo.class);

                Map<String, Object> mapUser = new HashMap<String, Object>();
                mapUser.put("chatting", postId);
                mDatabase.child("users").child(userName).updateChildren(mapUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(AddPostActivity.this, "error2222222!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void readChat(){
        mDatabase.child("chatting").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.getValue(MessageItem.class) != null){
                    MessageItem chat = dataSnapshot.getValue(MessageItem.class);
                    Log.w("FireBaseData", "getData" + chat.toString());
                } else {
                    Toast.makeText(AddPostActivity.this, "????????? ??????...", Toast.LENGTH_SHORT).show();
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