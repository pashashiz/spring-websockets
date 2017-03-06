package com.ps.tutorial;

public class GeneralMessage {

    private String message;

    public GeneralMessage() {}

    public GeneralMessage(String name) {
        this.message = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
