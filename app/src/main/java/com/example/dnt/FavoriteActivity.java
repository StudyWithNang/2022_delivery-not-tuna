package com.example.dnt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FavoriteActivity extends AppCompatActivity {
    Button signup_btn;
    TextView showResult;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        signup_btn = findViewById(R.id.signup_btn);
        showResult = findViewById(R.id.showResult);



        //체크박스들
        CheckBox ch1 = (CheckBox)findViewById(R.id.option1);
        CheckBox ch2 = (CheckBox)findViewById(R.id.option2);
        CheckBox ch3 = (CheckBox)findViewById(R.id.option3);
        CheckBox ch4 = (CheckBox)findViewById(R.id.option4);
        CheckBox ch5 = (CheckBox)findViewById(R.id.option5);
        CheckBox ch6 = (CheckBox)findViewById(R.id.option6);
        CheckBox ch7 = (CheckBox)findViewById(R.id.option7);
        CheckBox ch8 = (CheckBox)findViewById(R.id.option8);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");

        //제출 버튼 클릭했을때
        signup_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                //새로 체크되서 받은것
                if(showResult.getText().toString().equals(sendCheck(userName, ch1,ch2,ch3,ch4,ch5,ch6,ch7,ch8))){
                    Toast.makeText(getApplicationContext(),"변함이 없습니다.",Toast.LENGTH_SHORT).show();
                }else{
                    showResult.setText(sendCheck(userName, ch1,ch2,ch3, ch4, ch5, ch6, ch7, ch8));

                }

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private String sendCheck(String name, CheckBox ch1, CheckBox ch2, CheckBox ch3, CheckBox ch4, CheckBox ch5, CheckBox ch6, CheckBox ch7, CheckBox ch8){
        //체크된것들의 값을 받기 위한 변수
        String checked = "";
        if(ch1.isChecked()){
            checked += (ch1.getText().toString()+",");
        }
        if(ch2.isChecked()){
            checked += (ch2.getText().toString()+",");
        }
        if(ch3.isChecked()){
            checked += ch3.getText().toString();
        }
        if(ch4.isChecked()){
            checked += (ch4.getText().toString()+",");
        }
        if(ch5.isChecked()){
            checked += (ch5.getText().toString()+",");
        }
        if(ch6.isChecked()){
            checked += ch6.getText().toString();
        }
        if(ch7.isChecked()){
            checked += (ch7.getText().toString()+",");
        }
        if(ch8.isChecked()) {
            checked += (ch8.getText().toString() + ",");
        }
        //마지막 부분에 , 없애기 위해서 ,로 분리
        String[] hArr = checked.split(",");

        //리턴할 결과물을 담을 변수
        String result ="";

        for(int i = 0;i<hArr.length;i++){
            if(i == hArr.length-1){ //i가 hArr 변수의 마지막 이라면
                result += hArr[i]; // , 를 안붙임
            }else{ //마지막이 아니면 , 붙이기
                result+=(hArr[i]+", ");
            }
        }
        UploadFavorite(name, result);
        return "결과 : "+result;
    }


    public void UploadFavorite(String name, String favorites){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("favorites", favorites);
        mDatabase.child("users").child(String.valueOf(name)).updateChildren(map);
    }
}