package com.example.dnt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ModifyProfileActivity extends AppCompatActivity {
    EditText nickname, new_email, password;
    Button back_btn, complete_btn;
    String userName;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_users = database.getReference("users");
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");

        nickname = findViewById(R.id.modify_profile_nickname);
        new_email = findViewById(R.id.modify_profile_email);
        password = findViewById(R.id.modify_profile_password);
        back_btn = findViewById(R.id.modify_profile_back);
        complete_btn = findViewById(R.id.modify_profile_complete_btn);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyProfileActivity.this, MypageActivity.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
                finish();
            }
        });


        complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nickname.getText().toString();
                String new_Email = new_email.getText().toString();
                String chk_password = password.getText().toString();

                // 검증
                table_users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child(name).exists()) {
                            // Get user information
                            UserInfo user = snapshot.child(name).getValue(UserInfo.class);
                            Toast.makeText(ModifyProfileActivity.this, "why", Toast.LENGTH_LONG).show();

                            // 아무것도 입력하지 않았을 때
                            if (name.equals("") && new_Email.equals("") && chk_password.equals("")) {
                                Toast.makeText(ModifyProfileActivity.this, "빈칸이 없도록 입력해주세요.", Toast.LENGTH_LONG).show();
                            }

                            // 비밀번호가 일치할 때
                            if (user.getPassword().equals(chk_password)) {
                                ChangeProfile(name, new_Email);
                                Toast.makeText(ModifyProfileActivity.this, "프로필이 변경되었습니다.", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(ModifyProfileActivity.this, MypageActivity.class);
                                intent.putExtra("userName", userName);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            }
        });


    }

    public void ChangeProfile(String name, String new_email){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("email", new_email);
        mDatabase.child("users").child(String.valueOf(name)).updateChildren(map);
    }

}
