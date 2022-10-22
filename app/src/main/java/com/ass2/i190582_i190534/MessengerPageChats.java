package com.ass2.i190582_i190534;

public class MessengerPageChats {
    String profile_pic, firstName, lastName, last_message, user_id;

    public MessengerPageChats(){

    }

    public MessengerPageChats(String profile_pic, String firstName, String lastName, String last_message, String user_id) {
        this.profile_pic = profile_pic;
        this.firstName = firstName;
        this.lastName = lastName;
        this.last_message = last_message;
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }
}
