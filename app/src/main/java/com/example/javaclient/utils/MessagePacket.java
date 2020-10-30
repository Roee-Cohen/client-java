package com.example.javaclient.utils;

public class MessagePacket {

    public int roomID;
    public String dest;
    public String sender;
    public String content;

    public MessagePacket(String sender, String content, String dest){
        this.sender = sender;
        this.content = content;
        this.dest = dest;
    }

    public MessagePacket(String sender, String content, int roomID){
        this.sender = sender;
        this.content = content;
        this.roomID = roomID;
    }

}
