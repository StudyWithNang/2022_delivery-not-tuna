package com.example.dnt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends BaseAdapter {
    ArrayList<ChatInfo> chatInfos;
    LayoutInflater layoutInflater;
    String userName = "alice"; //activity -> adapter로 데이터 가져오기?

    //String userName = ((ChatActivity)ChatActivity.mContext).userName;

    public ChatAdapter(ArrayList<ChatInfo> chatInfos, LayoutInflater layoutInflater) {
        this.chatInfos = chatInfos;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return chatInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return chatInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        //현재 보여줄 번째의(position)의 데이터로 뷰를 생성
        ChatInfo item = chatInfos.get(position);

        //재활용할 뷰는 사용하지 않음!!
        View itemView = null;


        //메세지가 내 메세지인지??
        if(userName == "alice") {
        //if(item.getUsers().equals(userName)){
            itemView= layoutInflater.inflate(R.layout.msgbox_my,viewGroup,false);
        }else{
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