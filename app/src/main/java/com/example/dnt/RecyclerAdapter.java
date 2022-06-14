package com.example.dnt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnt.MainActivity;
import com.example.dnt.DetailActivity;
import com.example.dnt.R;
//import com.example.hanium.activities.ReviewPopup;
import com.example.dnt.PostInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<PostInfo> itemList;
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
    public RecyclerAdapter(ArrayList<PostInfo> itemList){
        this.itemList = itemList;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Etc/GMT"));
//        holder.id = itemList.get(position).getId();
        holder.restaurant_name.setText(itemList.get(position).getRestaurant());
        holder.deadline_HH.setText(itemList.get(position).getDeadline_HH());
        holder.deadline_mm.setText(itemList.get(position).getDeadline_mm());
        holder.pickup.setText(itemList.get(position).getPickup());
        holder.errand_price.setText(itemList.get(position).getPrice());
        holder.errand_description.setText(itemList.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("id", String.valueOf(holder.id));
                ContextCompat.startActivity(holder.itemView.getContext(), intent, null);
                ((MainActivity)holder.itemView.getContext()).overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });
//        String status = itemList.get(position).getStatus();
//        if(status.equals("end")) {
//            holder.review.setVisibility(View.VISIBLE);
//            holder.review.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(holder.itemView.getContext(), ReviewPopup.class);
//                    intent.putExtra("id", holder.id + "");
//                    ContextCompat.startActivity(holder.itemView.getContext(), intent, null);
//                }
//            });
//        }
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


