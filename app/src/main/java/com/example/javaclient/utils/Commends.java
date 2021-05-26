package com.example.javaclient.utils;

public enum Commends {
    CREATE("User has been created!"),
    LOAD_MESSAGES("Messages has been loaded!"),
    LOAD_CHATS("Chats has been loaded!"),
    UPDATE("Data has been updated!"),
    DELETE("Data has been deleted!"),
    SHUTDOWN("Shutdown!"),
    LOGIN("You have logged in!"),
    REGISTER("User has been registered!"),
    MESSAGE(" Message has been sent!"),
    CHAT(" Chat has been created!");

    private String message;

    Commends(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
