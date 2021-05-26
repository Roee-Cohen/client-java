package com.example.javaclient.utils;

public class MessagePacket {

    public String sender;
    public String content;
    public String dest;
    public MessageType msgType;
    public MessagePurpose msgPurpose;

    public MessagePacket(String sender, String content, String dest, MessageType msgType, MessagePurpose msgPurpose){
        this.sender = sender;
        this.content = content;
        this.dest = dest;
        this.msgPurpose = msgPurpose;
        this.msgType = msgType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public MessageType getMsgType() {
        return msgType;
    }

    public void setMsgType(MessageType msgType) {
        this.msgType = msgType;
    }

    public MessagePurpose getMsgPurpose() {
        return msgPurpose;
    }

    public void setMsgPurpose(MessagePurpose msgPurpose) {
        this.msgPurpose = msgPurpose;
    }
}


