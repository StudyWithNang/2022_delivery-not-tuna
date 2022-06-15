package com.example.dnt;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.hanium.activities.ReviewPopup;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<PostInfo> itemList;
    private Context context;
    public class ViewHolder extends RecyclerView.ViewHolder{
        int id;
        TextView restaurant_name, pickup, deadline_HH, deadline_mm, errand_price, errand_description;

        ViewHolder(View itemview){
            super(itemview);
            restaurant_name = itemview.findViewById(R.id.restaurant_name);
            pickup = itemview.findViewById(R.id.pickup);
            deadline_HH = itemview.findViewById(R.id.deadline_HH);
            deadline_mm = itemview.findViewById(R.id.deadline_mm);
            errand_price = itemview.findViewById(R.id.errand_price);
            errand_description = itemview.findViewById(R.id.errand_description);
        }
    }
    public RecyclerAdapter(ArrayList<PostInfo> itemList, Context context){
        this.itemList = itemList;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        RecyclerAdapter.ViewHolder viewHolder = new RecyclerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewholder, int position) {
        ViewHolder holder = (ViewHolder) viewholder;
        holder.restaurant_name.setText(itemList.get(position).getRestaurant());
        holder.deadline_HH.setText(itemList.get(position).getDeadline_HH());
        holder.deadline_mm.setText(itemList.get(position).getDeadline_mm());
        holder.pickup.setText(itemList.get(position).getPickup());
        holder.errand_price.setText(itemList.get(position).getPrice());
        holder.errand_description.setText(itemList.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(RecyclerAdapter.this, String.valueOf(holder.restaurant_name), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("restaurant_name", String.valueOf(holder.restaurant_name));
                intent.putExtra("deadline_HH", String.valueOf(holder.deadline_HH));
                intent.putExtra("deadline_mm", String.valueOf(holder.deadline_mm));
                intent.putExtra("pickup", String.valueOf(holder.pickup));
                intent.putExtra("errand_price", String.valueOf(holder.errand_price));
                intent.putExtra("errand_description", String.valueOf(holder.errand_description));
                ContextCompat.startActivity(holder.itemView.getContext(), intent, null);
                ((MainActivity)holder.itemView.getContext()).overridePendingTransition(R.anim.enter_from_right, R.anim.enter_from_left);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}


