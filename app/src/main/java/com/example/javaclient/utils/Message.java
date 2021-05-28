package com.example.javaclient.utils;

public class Message {

    public MessagePacket message;
    public String date;

    public Message(MessagePacket message, String date){
        this.message = message;
        this.date = date;
    }

    public MessagePacket getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public void setMessage(MessagePacket message) {
        this.message = message;
    }
}
