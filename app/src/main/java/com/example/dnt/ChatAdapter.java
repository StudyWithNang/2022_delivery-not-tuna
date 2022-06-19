package com.example.dnt;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends BaseAdapter {
    ArrayList<MessageItem> messageItems;
    LayoutInflater layoutInflater;

    String userName = ((MainActivity)MainActivity.context_main).userName;

    public ChatAdapter(ArrayList<MessageItem> chatInfos, LayoutInflater layoutInflater) {
        this.messageItems = chatInfos;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return messageItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messageItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        //현재 보여줄 번째의(position)의 데이터로 뷰를 생성
        MessageItem item = messageItems.get(position);

        View itemView = null;

        Log.w("messageItem", "userName:" + userName + " -- item Name: " + item.getUserName());

        //메세지가 내 메세지인지??
        if (item.getUserName().equals(userName)){
            itemView= layoutInflater.inflate(R.layout.msgbox_my,viewGroup,false);
        } else {
            itemView= layoutInflater.inflate(R.layout.msgbox_other,viewGroup,false);
        }

        //만들어진 itemView에 값들 설정
        CircleImageView img= itemView.findViewById(R.id.img);
        TextView name= itemView.findViewById(R.id.name);
        TextView msg= itemView.findViewById(R.id.msg);
        TextView time= itemView.findViewById(R.id.time);

        name.setText(item.getUserName());
        msg.setText(item.getMessage());
        time.setText(item.getTime());

        return itemView;
    }
}