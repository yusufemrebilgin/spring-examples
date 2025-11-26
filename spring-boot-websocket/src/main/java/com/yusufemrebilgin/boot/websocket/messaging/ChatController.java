package com.yusufemrebilgin.boot.websocket.messaging;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat/send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage message) {
        return message;
    }

    @MessageMapping("/chat/join")
    @SendTo("/topic/public")
    public ChatMessage joinChannel(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
        var attributes = headerAccessor.getSessionAttributes();
        if (attributes != null) {
            attributes.put("username", message.sender());
        }
        return message;
    }

}
