package com.example.dnt;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    ArrayList<PostInfo> arrayList;
    private Context context;
    //어댑터에서 액티비티 액션을 가져올 때 context가 필요한데 어댑터에는 context가 없다.
    //선택한 액티비티에 대한 context를 가져올 때 필요하다.

    public PostAdapter(ArrayList<PostInfo> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    // 실제 리스트뷰가 어뎁터에 연결된 다음에 뷰 홀더를 최초로 만들어낸다.
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.restaurant_name.setText(arrayList.get(position).getRestaurant());
        holder.deadline_HH.setText(arrayList.get(position).getDeadline_HH());
        holder.deadline_mm.setText(arrayList.get(position).getDeadline_mm());
        holder.pickup.setText(arrayList.get(position).getPickup());
        holder.errand_price.setText(arrayList.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        // 삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView restaurant_name, deadline_HH, deadline_mm, pickup, errand_price, errand_description;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            this.restaurant_name = itemView.findViewById(R.id.restaurant_name);
            this.deadline_HH = itemView.findViewById(R.id.deadline_HH);
            this.deadline_mm = itemView.findViewById(R.id.deadline_mm);
            this.pickup = itemView.findViewById(R.id.pickup);
            this.errand_description = itemView.findViewById(R.id.errand_description);
        }
    }

}


