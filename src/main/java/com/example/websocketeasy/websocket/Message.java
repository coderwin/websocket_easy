package com.example.websocketeasy.websocket;

import lombok.Getter;

@Getter
public class Message {

    private String from;
    private String to;
    private String content;

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
