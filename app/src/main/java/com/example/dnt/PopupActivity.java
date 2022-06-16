package com.example.dnt;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class PopupActivity extends AppCompatActivity {

    Button mOnClose;
    TextView txtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 상태바 제거 ( 전체화면 모드 )
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_popup);
        mOnClose = findViewById(R.id.mOnClose);
        txtText = findViewById(R.id.txtText);

        String[] randomTxt = getResources().getStringArray(R.array.randomTxt);
        Random random = new Random();
        int n = random.nextInt(randomTxt.length-1);
        txtText.setText(randomTxt[n]);

    }

    //확인 버튼 클릭,액티비티(팝업) 닫기
    public void mOnClose(View v){
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
