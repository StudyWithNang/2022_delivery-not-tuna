package com.example.dnt;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserInfo {
    public String email;
    public String nickname;
    public String password;

    public UserInfo() {

    }

    public UserInfo(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;

    }

    public String getEmail() {return email;}

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getPassword() {return password;}

    public void setPassword(String password) { this.password = password; }


    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}