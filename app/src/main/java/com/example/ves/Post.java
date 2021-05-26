package com.example.ves;

import com.google.gson.annotations.SerializedName;

public class Post {
    //@SerializedName("body")
    private String userid;
    //@SerializedName("pw")
    private String pw;
    // @SerializedName("username")
    private String username;
    // @SerializedName("usertype")
    private int usertype;

    public String getUserid(){
        return userid;
    }
    /*public void setUserid(String userid){
        this.userid = userid;
    }*/

    public String getPw(){
        return pw;
    }
   /* public void setPw(String pw){
        this.pw = pw;
    }*/

    public String getUsername(){
        return username;
    }
    /*public void setUsername(String username){
        this.username = username;
    }*/

    public int getUsertype(){
        return usertype;
    }
   /* public void setUsertype(int usertype){
        this.usertype = usertype;
    }*/

}
