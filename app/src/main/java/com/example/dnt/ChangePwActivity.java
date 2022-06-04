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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ChangePwActivity extends AppCompatActivity {
    EditText nickname, current_pwd, new_password, new_password_chk;
    Button change_pwd_btn;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);

        nickname = findViewById(R.id.nickname);
        current_pwd = findViewById(R.id.current_pwd);
        new_password = findViewById(R.id.new_password);
        new_password_chk = findViewById(R.id.new_password_chk);
        change_pwd_btn = (Button) findViewById(R.id.complete_change_pwd_btn);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        // 비밀번호 변경 후 버튼 누르면 다시 로그인 화면으로
        change_pwd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getNickname = nickname.getText().toString();
                String getCurrent_pwd = current_pwd.getText().toString();
                String getNew_pwd = new_password.getText().toString();
                String getNew_pwd_chk = new_password_chk.getText().toString();

                // 검증

                // 변경
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("password", getNew_pwd);
                mDatabase.child("users").child(String.valueOf(getNickname)).updateChildren(map);

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
