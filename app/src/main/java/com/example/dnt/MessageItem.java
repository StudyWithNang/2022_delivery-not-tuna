package com.example.dnt;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class MessageItem {
    public  int postnum;

    public String userName = "";
    public String message = "";
    public String time = "";


    public MessageItem() {
    }


    public MessageItem(String message, String time, String userName) {
        this.message = message;
        this.time = time;
        this.userName = userName;
        this.postnum = 1;
    };


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

}