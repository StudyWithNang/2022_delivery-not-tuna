package com.example.dnt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Date;

public class ChatBeforeActivity extends AppCompatActivity {
    TextView detail_info;
    Button enter_btn;
    String userName, detail_restaurant_name, detail_deadline_HH, detail_deadline_mm, detail_pickup;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;
    final DatabaseReference table_chats = database.getReference("chats");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_chat);

        detail_info = findViewById(R.id.detail_info);
        enter_btn = findViewById(R.id.enter_btn);

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        detail_restaurant_name = intent.getStringExtra("restaurant");
        detail_deadline_HH = intent.getStringExtra("HH");
        detail_deadline_mm = intent.getStringExtra("mm");
        detail_pickup = intent.getStringExtra("pickup");

        //Toast.makeText(ChatBeforeActivity.this, "ChatBeforeActivity!  " + userName + "님!", Toast.LENGTH_SHORT).show();

        // 주문 정보 화면에 표시
        String info = "주문자 : "+userName
                        + "\n가게 이름 : " + detail_restaurant_name
                        + "\n주문 시간 : " + detail_deadline_HH + "시 " + detail_deadline_mm + "분"
                        + "\n픽업 장소 : " + detail_pickup;
        detail_info.setText(info);

    }

    public void enterChatroom(View view) {
        //db 저장


        Intent nextIntent = new Intent(ChatBeforeActivity.this, ChatActivity.class);
        nextIntent.putExtra("userName", userName);
        startActivity(nextIntent);
        //finish();
    }


}