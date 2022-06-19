package com.example.dnt;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    ArrayList<PostInfo> arrayList;
    private Context context;
    //어댑터에서 액티비티 액션을 가져올 때 context가 필요한데 어댑터에는 context가 없다.
    //선택한 액티비티에 대한 context를 가져올 때 필요하다.

    public PostAdapter(ArrayList<PostInfo> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    // 실제 리스트뷰가 어뎁터에 연결된 다음에 뷰 홀더를 최초로 만들어낸다.
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        PostAdapter.PostViewHolder postViewHolder = new PostAdapter.PostViewHolder(view);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {
        Log.e("postadapter", String.valueOf(holder.itemView.getContext()));
        PostInfo postinfo = arrayList.get(position);
        holder.restaurant_name.setText(arrayList.get(position).getRestaurant());
        holder.deadline_HH.setText(arrayList.get(position).getDeadline_HH());
        holder.deadline_mm.setText(arrayList.get(position).getDeadline_mm());
        holder.pickup.setText(arrayList.get(position).getPickup());
        holder.delivery_price.setText(arrayList.get(position).getPrice());
        holder.delivery_description.setText(arrayList.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                ContextCompat.startActivity(holder.itemView.getContext(), intent, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        // 삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView restaurant_name, deadline_HH, deadline_mm, pickup, delivery_price, delivery_description;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.e("111", ((TextView) itemView.findViewById(R.id.restaurant_name)).toString());
            restaurant_name = (TextView) itemView.findViewById(R.id.restaurant_name);
            deadline_HH = (TextView) itemView.findViewById(R.id.deadline_HH);
            deadline_mm = (TextView) itemView.findViewById(R.id.deadline_mm);
            pickup = (TextView) itemView.findViewById(R.id.pickup);
            delivery_description = (TextView) itemView.findViewById(R.id.delivery_description);
            delivery_price = (TextView) itemView.findViewById(R.id.delivery_price);
        }
    }

}


