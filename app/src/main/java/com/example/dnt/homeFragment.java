package com.example.dnt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dnt.PostInfo;
import com.example.dnt.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.dnt.AddPostActivity;
import com.example.dnt.R;
import com.example.dnt.PostAdapter;
import com.example.dnt.RecyclerAdapter;
import com.example.dnt.SearchActivity;
//import com.example.dnt.ImageViewPagerAdapter;
//import com.example.dnt.RecyclerAdapter;
//import com.example.dnt.SearchActivity;
import com.example.dnt.PostInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class homeFragment extends Fragment {
    private RecyclerView recyclerView;
    //private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PostInfo> arrayList;
    private List<PostInfo> saveList;//회원검색 기능용 복사본
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    RecyclerAdapter adapter;

    Button add_btn, secret_popbtn;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText search;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        recyclerView = (RecyclerView) view.findViewById(R.id.homeRecyclerView);
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // User 객체를 담을 어레이 리스트 (어댑터쪽으로)
        saveList = new ArrayList<PostInfo>();

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("posts"); // DB 테이블 연결


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                saveList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    PostInfo PostInfo = snapshot.getValue(PostInfo.class); // 만들어뒀던 User 객체에 데이터를 담는다.
                    arrayList.add(PostInfo); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                    saveList.add(PostInfo);
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침해야 반영이 됨
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("homeFragment", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });
        adapter = new RecyclerAdapter(arrayList, getContext(), saveList);
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                        arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                        saveList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                            PostInfo PostInfo = snapshot.getValue(PostInfo.class); // 만들어뒀던 User 객체에 데이터를 담는다.
                            arrayList.add(PostInfo);// 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                            saveList.add(PostInfo);
                        }
                        adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침해야 반영이 됨
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // 디비를 가져오던중 에러 발생 시
                        Log.e("homeFragment", String.valueOf(databaseError.toException())); // 에러문 출력
                    }
                });
                adapter = new RecyclerAdapter(arrayList, getContext(), saveList);
                recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        //add 버튼
        add_btn = view.findViewById(R.id.home_add_post_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddPostActivity.class);
                startActivity(intent);
            }
        });

        search = view.findViewById(R.id.home_search_edittext);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUser(s.toString());//회원 검색 기능용
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //popup 버튼
        secret_popbtn = view.findViewById(R.id.secret_popbtn);
        secret_popbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PopupActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
    public void searchUser(String search){
        arrayList.clear();
        for(int i = 0; i < saveList.size(); i++){
            if(saveList.get(i).getRestaurant().contains(search)){//contains메소드로 search 값이 있으면 true를 반환함
                arrayList.add(saveList.get(i));
            }
        }
        adapter.notifyDataSetChanged();//어댑터에 값일 바뀐것을 알려줌

    }
}