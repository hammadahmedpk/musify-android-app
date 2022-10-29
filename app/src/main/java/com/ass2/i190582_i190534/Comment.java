package com.ass2.i190582_i190534;

public class Comment {
    String user;
    String song;
    String comment;
    
    public String getUser() {
        return user;
    }

    public Comment() {
    }

    public Comment(String song, String comment, String user) {
        this.user = user;
        this.song = song;
        this.comment = comment;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
