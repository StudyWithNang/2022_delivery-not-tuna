package com.example.dnt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {
    Intent intent;
    EditText login_nickname, login_email, login_password;
    Button login_btn;
    TextView signup, change_pw;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_users = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_nickname = findViewById(R.id.login_nickname);
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_btn = findViewById(R.id.login_btn);
        signup = findViewById(R.id.signup);
        change_pw = findViewById(R.id.change_pw);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getUserName = login_nickname.getText().toString();
                String getUserEmail = login_email.getText().toString();
                String getUserPW = login_password.getText().toString();

                table_users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child(getUserName).exists()) {
                            // Get user information
                            UserInfo user = snapshot.child(getUserName).getValue(UserInfo.class);

                            // 아무것도 입력하지 않았을 때
                            if (getUserEmail.equals("") && getUserPW.equals("")) {
                                Toast.makeText(LoginActivity.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                            }
                            // 아이디와 비밀번호를 잘 입력했을 때
                            else if (user.getEmail().equals(getUserEmail) && user.getPassword().equals(getUserPW)) {
                                Toast.makeText(LoginActivity.this, "어서오세요 " + getUserName + "님!", Toast.LENGTH_SHORT).show();
                                Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);
                                homeIntent.putExtra("userName", getUserName);
                                startActivity(homeIntent);
                                finish();
                            }
                            // 아이디와 비밀번호가 틀렸을 때
                            else {
                                Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        // 계정이 없을 때
                        else {
                            Toast.makeText(LoginActivity.this, "계정 정보가 없습니다. 회원가입을 먼저 진행해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        change_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), ChangePwActivity.class);
                startActivity(intent);
            }
        });
    }

}
