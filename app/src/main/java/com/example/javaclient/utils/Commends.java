package com.example.javaclient.utils;

public enum Commends {
    CREATE("User has been created!"),
    LOAD_MESSAGES("Messages has been loaded!"),
    LOAD_CONTACTS("Contacts has been loaded!"),
    LOGIN("You have logged in!"),
    REGISTER("User has been registered!"),
    MESSAGE(" Message has been sent!"),
    LOG_OUT("Bye");

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
