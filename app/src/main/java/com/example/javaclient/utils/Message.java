package com.example.javaclient.utils;

public class Message {

    public MessagePacket msg;
    public String date;

    public Message(MessagePacket msg, String date){
        this.msg = msg;
        this.date = date;
    }

    public MessagePacket getMsg() {
        return msg;
    }

    public void setMsg(MessagePacket msg) {
        this.msg = msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
