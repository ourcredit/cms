package com.monkey.web.controller.dtos;


public class WebSocketMessage {
    public WebSocketMessage() {
    }

    public WebSocketMessage(String to, Object data, Integer type) {
        this.to = to;
        this.type = type;
        this.data = data;
    }

    public String to;
    public Object data;
    public Integer type;
}
