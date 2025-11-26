package com.yusufemrebilgin.boot.websocket.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Optional;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public WebSocketEventListener(SimpMessageSendingOperations simpMessageSendingOperations) {
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Optional.ofNullable(headerAccessor.getSessionAttributes())
                .map(attributes -> (String) attributes.get("username"))
                .ifPresent(username -> {
                    logger.info("User disconnected: {}", username);
                    ChatMessage message = new ChatMessage(null, username, ChatMessage.MessageType.LEAVE);
                    simpMessageSendingOperations.convertAndSend("/topic/public", message);
                });
    }

}
