package com.ass2.i190582_i190534;

public class OnlineUsers {
    String user_id, status, profile_pic;

    public OnlineUsers(){

    }

    public OnlineUsers(String user_id, String status, String profile_pic) {
        this.user_id = user_id;
        this.status = status;
        this.profile_pic = profile_pic;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }
}
