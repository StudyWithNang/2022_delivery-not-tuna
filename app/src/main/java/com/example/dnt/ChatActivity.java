package com.example.dnt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class ChatActivity extends AppCompatActivity {

    EditText input_message;
    ListView listView;
    String userName;

    ArrayList<MessageItem> messageItems = new ArrayList<>();
    ChatAdapter adapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_chats = database.getReference("chats");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");

        input_message = findViewById(R.id.input_message);
        listView = findViewById(R.id.listview);
        adapter = new ChatAdapter(messageItems, getLayoutInflater());
        listView.setAdapter(adapter);


        table_chats.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String s) {

                //새로 추가된 데이터(값 : MessageItem) 가져오기
                MessageItem chat= snapshot.getValue(MessageItem.class);
                messageItems.add(chat);

                //리스트뷰를 갱신
                adapter.notifyDataSetChanged();
                listView.setSelection(messageItems.size()-1); //리스트뷰의 마지막 위치로 스크롤 위치 이동
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // 상단의 뒤로가기 버튼 누르면 채팅방 입장 화면으로 전환
        Button back_btn = (Button) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void clickSend(View view) {
        //DB에 저장할 값들(닉네임, 메세지, 시간)
        String nickName= userName;
        String message= input_message.getText().toString();

        Calendar calendar= Calendar.getInstance(); //현재 시간을 가지고 있는 객체
        String time=calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE); //14:16

        MessageItem chatInfo= new MessageItem(message, time, nickName);

        table_chats.push().setValue(chatInfo);
        //table_chats.child("chatroom").push().setValue(chatInfo); //-> DB엔 올라가지만 화면에는 안보임


        //전송 후 메시지 지우기
        input_message.setText("");

        //소프트키패드를 안보이도록..
        InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

    }
}