package com.yusufemrebilgin.boot.websocket.messaging;

public record ChatMessage(String content, String sender, MessageType type) {

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }

}
