package com.thetrooper.health.healthapp.Chat;

/**
 * Created by manik on 1/1/18.
 */

public class FriendlyMessage {

    private String text;
    private boolean user;

    public FriendlyMessage() {
    }

    public FriendlyMessage(String text, boolean user) {
        this.text = text;
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getUser() {
        return user;
    }

    public void setName(boolean name) {
        this.user = name;
    }
}