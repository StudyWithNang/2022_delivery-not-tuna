package com.example.dnt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    public static Context mContext;
    ArrayList<ChatInfo> chatInfos=new ArrayList<>();
    ChatAdapter adapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_chats = database.getReference("chats");

    DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        Toast.makeText(ChatActivity.this, "ChatActivity! " + userName + "님!", Toast.LENGTH_SHORT).show();

        //mContext = this;

        //제목줄 제목글시를 닉네임으로(또는 채팅방)
        //getSupportActionBar().setTitle(userName);

        input_message = findViewById(R.id.input_message);
        listView = findViewById(R.id.listview);
        adapter = new ChatAdapter(chatInfos,getLayoutInflater());
        listView.setAdapter(adapter);


        //Firebase DB관리 객체와 'caht'노드 참조객체 얻어오기
        database= FirebaseDatabase.getInstance();

        //firebaseDB에서 채팅 메세지들 실시간 읽어오기..
        //'chats'노드에 저장되어 있는 데이터들을 읽어오기
        //table_chats에 데이터가 변경되는 것으 듣는 리스너 추가
        table_chats.addChildEventListener(new ChildEventListener() {
            //새로 추가된 것만 줌 ValueListener는 하나의 값만 바뀌어도 처음부터 다시 값을 줌
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String s) {

                //새로 추가된 데이터(값 : ChatInfo) 가져오기
                ChatInfo chat= snapshot.getValue(ChatInfo.class);

                //새로운 메세지를 리스뷰에 추가하기 위해 ArrayList에 추가
                chatInfos.add(chat);

                //리스트뷰를 갱신
                adapter.notifyDataSetChanged();
                listView.setSelection(chatInfos.size()-1); //리스트뷰의 마지막 위치로 스크롤 위치 이동
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

        //firebase DB에 저장할 값들( 닉네임, 메세지, 프로필 이미지URL, 시간)
        String nickName= userName;
        String message= input_message.getText().toString();
        //String pofileUrl= G.porfileUrl;

        //메세지 작성 시간 문자열로..
        Calendar calendar= Calendar.getInstance(); //현재 시간을 가지고 있는 객체
        String time=calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE); //14:16

        //firebase DB에 저장할 값(MessageItem객체) 설정
        ChatInfo chatInfo= new ChatInfo(message, time, nickName);

        table_chats.push().setValue(chatInfo);
        //table_chats.child("chatroom").push().setValue(chatInfo); -> DB엔 올라가지만 화면에는 안보임


        //EditText에 있는 글씨 지우기
        input_message.setText("");

        //소프트키패드를 안보이도록..
        InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

    }
}

