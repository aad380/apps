package com.mkyong.helloworld.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.server.ServerEndpoint;

/**
 * ChatBot.java
 * http://programmingforliving.com
 */
@ServerEndpoint("/date-time-native")
public class DateTimeNativeHandler {
    private final Logger logger = LoggerFactory.getLogger(DateTimeNativeHandler.class);
    Session userSession = null;

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession
     *            the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession
     *            the userSession which is getting closed.
     */
    @OnClose
    public void onClose(Session userSession) {
        this.userSession = null;
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a
     * client send a message.
     *
     * @param message
     *            The text message
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("NATIVE MESSAGE: '{}'", message);
        sendMessage("PONG: " + message);
    }


    /**
     * Send a message.
     *
     * @param message
     */
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }


}
