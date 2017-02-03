package com.ua.example.kif.kyrsi_17_http_firebase;

/**
 * Created by Kif on 30.01.2017.
 */

public class Message {
    private String message;
    private long time;

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
