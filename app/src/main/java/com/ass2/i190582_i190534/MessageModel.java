package com.ass2.i190582_i190534;

public class MessageModel {
    String uID, message;
    Long timestamp;

    public MessageModel(){

    }

    public MessageModel(String uID, String message, Long timestamp) {
        this.uID = uID;
        this.message = message;
        this.timestamp = timestamp;
    }

    public MessageModel(String uID, String message) {
        this.uID = uID;
        this.message = message;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
