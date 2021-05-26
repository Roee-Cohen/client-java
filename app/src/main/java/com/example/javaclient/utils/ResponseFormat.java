package com.example.javaclient.utils;

public class ResponseFormat {

    public Status status;
    public String data;

    public ResponseFormat(Status status, String data) {
        this.status = status;
        this.data = data;
    }
}
