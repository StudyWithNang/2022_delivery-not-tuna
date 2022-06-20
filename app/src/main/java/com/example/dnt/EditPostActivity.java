package com.example.dnt;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnt.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditPostActivity extends AppCompatActivity {
    Button back, edit_btn;
    EditText restaurant_name, deadline_HH, deadline_mm, pickup, delivery_price, delivery_description;
    Intent intent;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_users = database.getReference("posts");
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpost);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        back = findViewById(R.id.editPost_back);
        restaurant_name = findViewById(R.id.editPost_restaurant_name);
        deadline_HH = findViewById(R.id.editPost_deadline_HH);
        deadline_mm = findViewById(R.id.editPost_deadline_mm);
        pickup = findViewById(R.id.editPost_pickup);
        delivery_price = findViewById(R.id.editPost_delivery_price);
        delivery_description = findViewById(R.id.editPost_delivery_description);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        edit_btn =findViewById(R.id.editPost_btn);
        intent = getIntent();
        back.setOnClickListener(onClickListener);
        edit_btn.setOnClickListener(onClickListener);

    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.editPost_back:
                    Intent intent1 = new Intent(EditPostActivity.this, DetailActivity.class);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                    finish();
                    break;
                case R.id.editPost_btn:
                    String getNew_restaurant_name = restaurant_name.getText().toString();
                    String getNew_deadline_HH = deadline_HH.getText().toString();
                    String getNew_deadline_mm = deadline_mm.getText().toString();
                    String getNew_pickup = pickup.getText().toString();
                    String getNew_delivery_price = delivery_price.getText().toString();
                    String getNew_delivery_description = delivery_description.getText().toString();

                    ChangePost(getNew_restaurant_name, getNew_deadline_HH, getNew_deadline_mm,
                            getNew_pickup, getNew_delivery_price, getNew_delivery_description );
                    Toast.makeText(EditPostActivity.this, "배달내용이 변경되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent backintent = new Intent(EditPostActivity.this, DetailActivity.class);
                    startActivity(backintent);
                    overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                    finish();
                    break;
            }
        }
    };
    // 비밀번호 변경하는 함수
    public void ChangePost(String new_restaurant, String new_HH, String new_mm,
                           String new_pickup, String new_price, String new_description){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("restaurant", new_restaurant);
        map.put("deadline_HH", new_HH);
        map.put("deadline_mm", new_mm);
        map.put("pickup", new_pickup);
        map.put("price", new_price);
        map.put("description", new_description);
        mDatabase.child("posts").child("1").updateChildren(map);
    }
}