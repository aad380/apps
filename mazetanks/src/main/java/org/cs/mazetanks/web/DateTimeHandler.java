package org.cs.mazetanks.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by aad on 05.02.2017.
 */
public class DateTimeHandler extends TextWebSocketHandler  {
    private final Logger logger = LoggerFactory.getLogger(DateTimeHandler.class);

    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private Thread t;

    public DateTimeHandler() {
        super();
        logger.info("========= DateTimeHandler WAS CREATED");
        t = new Thread(() -> {
            StringBuilder sb = new StringBuilder();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");
            while(true) {
                sessions.forEach((String id, WebSocketSession session) -> {
                    try {
                        sb.delete(0, sb.length());
                        sb.append("id=").append(id).append(" ");
                        sb.append("date=").append(dateFormat.format(new Date()));
                        session.sendMessage(new TextMessage(sb.toString()));
                    } catch (IOException ex) {
                        logger.error("send message: " + ex.getMessage());
                    }
                });
                try {Thread.sleep(100L);} catch (InterruptedException ex) {}
            }
        });
        t.start();
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        logger.info("MESSAGE: length={}, message='{}'", message.getPayloadLength(), message.getPayload());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        logger.error("error occured at sender " + session, throwable);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info(String.format("Session %s closed because of %s", session.getId(), status.getReason()));
        sessions.remove(session.getId());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Connected ... " + session.getId());
        WebSocketSession oldSession = sessions.get(session.getId());
        if (oldSession != null) {
            oldSession.close(CloseStatus.GOING_AWAY);
        }
        sessions.put(session.getId(), session);
    }

}
