package com.example.dnt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginActivity extends AppCompatActivity {
    Intent intent;
    EditText login_email, login_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


<<<<<<< HEAD
        Button login_btn = findViewById(R.id.login_btn);
        TextView sign_up = findViewById(R.id.sign_up);
//        TextView find = findViewById(R.id.find);

        login_btn.setOnClickListener(OnClickListener);
        sign_up.setOnClickListener(OnClickListener);
//        find.setOnClickListener(onClickListener);
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);

    }
=======
        //로그인 버튼 -> list(home)
        Button login_b = (Button) findViewById(R.id.login_btn);
        login_b.setOnClickListener(new View.OnClickListener() {
>>>>>>> a817659d64414e2c1244ac0097d9fce6f635df99

    View.OnClickListener OnClickListener = new View.OnClickListener() {
        // do something when the button is clicked
        // Yes we will handle click here but which button clicked??? We don't know

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.login_btn:
                    intent = new Intent(getApplicationContext(),MainActivity.class);
//                    intent = new Intent(getApplicationContext(),AddPostActivity.class);
                    startActivity(intent);
                    break;
                case R.id.sign_up:
                    intent = new Intent(getApplicationContext(),SignUpActivity.class);
                    startActivity(intent);
                    break;
//                    case R.id.find:
//                        intent = new Intent(getApplicationContext(), findPopup.class);
//                        startActivity(intent);
//                        break;
            }
        }
    };

<<<<<<< HEAD

=======
    }
>>>>>>> a817659d64414e2c1244ac0097d9fce6f635df99
}




