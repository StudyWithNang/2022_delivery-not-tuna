package com.example.dnt;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class ChatInfo {
    public  int postnum;

    public int users = 1;
    public String orderer;

    public String message = "";
    public String time = "";
    public String userName = "";


    public ChatInfo() {
    }

    // 주문 글이 작성될 때 채팅방 생성
    public ChatInfo(String orderer, int users, int postnum) {
        this.orderer = orderer;
        this.users = users;
        this.postnum = postnum;
    }

    // 다른 사람이 채팅방에 들어올 때
    public ChatInfo(String message, String time, String userName) {
        this.users++;
        this.message = message;
        this.time = time;
        this.userName = userName;
    };


    public int getUsers() {
        return users;
    }

    public void setUsers(int users) {
        this.users = users;
    }

    public String getOrderer() {
        return orderer;
    }

    public void setOrderer(String orderer) {
        this.orderer = orderer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPostnum() {
        return postnum;
    }

    public void setPostnum(int postnum) {
        this.postnum = postnum;
    }
/*

    @Override
    public String toString() {
        return "post{" +
                "restaurant='" + restaurant + '\'' +
                ", deadline_HH='" + deadline_HH + '\'' +
                ", deadline_mm='" + deadline_mm + '\'' +
                ", pickup='" + pickup + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

 */
}