package com.ass2.i190582_i190534;

public class MessageModel {
    String uID, message;
    String timestamp;
    String messageID;

    public MessageModel(){

    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public MessageModel(String uID, String message, String timestamp, String messageID) {
        this.uID = uID;
        this.message = message;
        this.timestamp = timestamp;
        this.messageID = messageID;
    }

    public MessageModel(String uID, String message, String timestamp) {
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
