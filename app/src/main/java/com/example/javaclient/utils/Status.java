package com.example.javaclient.utils;

public enum Status{
    OK(201),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    BAD_REQUEST(402),
    NOTFOUND(404);

    private int status;

    Status(int s){
        this.status = s;
    }

    public int getStatus() {
        return status;
    }
}
