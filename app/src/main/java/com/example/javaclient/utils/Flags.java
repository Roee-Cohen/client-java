package com.example.javaclient.utils;

public enum Flags {
    CREATE("User has been created!"),
    READ("Data has been read!"),
    UPDATE("Data has been updated!"),
    DELETE("Data has been deleted!"),
    SHUTDOWN("Shutdown!"),
    LOGIN("You have logged in!"),
    REGISTER("User has been registered!");

    private String message;

    Flags(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
