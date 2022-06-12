package com.example.dnt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class ChangePwActivity extends AppCompatActivity {
    EditText nickname, email, new_password, new_password_chk;
    Button change_pwd_btn;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_users = database.getReference("users");
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);

        nickname = findViewById(R.id.nickname);
        email = findViewById(R.id.email);
        new_password = findViewById(R.id.new_password);
        new_password_chk = findViewById(R.id.new_password_chk);
        change_pwd_btn = (Button) findViewById(R.id.complete_change_pwd_btn);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // 상단의 뒤로가기 버튼 누르면 로그인 화면으로 전환
        Button signup_back = (Button) findViewById(R.id.back_btn);
        signup_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        change_pwd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getNickname = nickname.getText().toString();
                String getEmail = email.getText().toString();
                String getNew_pwd = new_password.getText().toString();
                String getNew_pwd_chk = new_password_chk.getText().toString();


                // 검증
                table_users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(getNickname).exists()) {
                            // Get user information
                            UserInfo user = dataSnapshot.child(getNickname).getValue(UserInfo.class);

                            // 아무것도 입력하지 않았을 때
                            if (getEmail.equals("") && getNew_pwd.equals("") && getNew_pwd_chk.equals("")) {
                                Toast.makeText(ChangePwActivity.this, "빈칸이 없도록 입력해주세요.", Toast.LENGTH_LONG).show();
                            }
                            // 새 비밀번호 입력란과 확인란의 입력이 다를 때

                            // 계정 정보가 존재할 때
                            else if (user.getEmail().equals(getEmail)) {
                                ChangePW(getNickname, getNew_pwd);

                                Toast.makeText(ChangePwActivity.this, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent homeIntent = new Intent(ChangePwActivity.this, LoginActivity.class);
                                startActivity(homeIntent);
                                //finish();
                            }
                            // 계정 정보가 없을 때
                            else {
                                Toast.makeText(ChangePwActivity.this, "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        // 계정이 없을 때
                        else
                        {
                            Toast.makeText(ChangePwActivity.this, "계정 정보가 없습니다. 회원가입을 먼저 진행해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }

                });
            }
        });

    }

    // 비밀번호 변경하는 함수
    public void ChangePW(String name, String new_password){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("password", new_password);
        mDatabase.child("users").child(String.valueOf(name)).updateChildren(map);
    }
}
