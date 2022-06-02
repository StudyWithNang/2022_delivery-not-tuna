package com.example.dnt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    EditText signup_email, signup_nickname, signup_password;
    Button signup_btn;
    int userId = 1;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup_email = findViewById(R.id.signup_email);
        signup_nickname = findViewById(R.id.signup_nickname);
        signup_password = findViewById(R.id.signup_password);
        signup_btn = findViewById(R.id.signup_btn);

        //firebase 정의
        mDatabase = FirebaseDatabase.getInstance().getReference();

        readUser();

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getUserEmail = signup_email.getText().toString();
                String getUserName = signup_nickname.getText().toString();
                String getUserPW = signup_password.getText().toString();

                //hashmap 만들기
                HashMap result = new HashMap<>();
                result.put("email", getUserEmail);
                result.put("nickname", getUserName);
                result.put("password", getUserPW);

                writeNewUser(userId, getUserEmail, getUserName, getUserPW);
                userId++;
            }
        });
    }

    private void writeNewUser(int userId, String email, String name, String pw) {
        UserInfo user = new UserInfo(email, name, pw);

        mDatabase.child("users").child(String.valueOf(userId)).setValue(user) //db에 순차적으로 users - 1 - email, name, pw 들어감
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Toast.makeText(SignUpActivity.this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(SignUpActivity.this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void readUser(){
        mDatabase.child("users").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.getValue(UserInfo.class) != null){
                    UserInfo post = dataSnapshot.getValue(UserInfo.class);
                    //Log.w("FireBaseData", "getData" + post.toString());
                } else {
                    Toast.makeText(SignUpActivity.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
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