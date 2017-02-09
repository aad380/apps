package com.mkyong.helloworld.web;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Data;

/**
 * Created by o.dudarenko on 08.02.2017.
 */
public class TankControlHandler extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(DateTimeHandler.class);

    private Map<String, TankClient> clients = new ConcurrentHashMap<>();

    public TankControlHandler() {
        super();
        Thread runner = new Thread (new TankRunner(clients));
        runner.setDaemon(true);
        runner.start();
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        TankClient tankClient = clients.get(session.getId());
        if (tankClient == null) {
            logger.error("handleTextMessage: unknown client session with id=" + session.getId());
            return;
        }
        String cmd = message.getPayload().toUpperCase();
        if ("AUTORUN".equals(cmd)) {
            tankClient.setAutorun(!tankClient.isAutorun());
            return;
        }
        TankClient.Direction direction;
        try {
             direction = TankClient.Direction.valueOf(cmd);
        } catch (Exception ex) {
            logger.error ("handleTextMessage: incorrect command="+cmd);
            return;
        }
        tankClient.setDirection(direction);
        tankClient.setAutorun(false);
        try {
            session.sendMessage(new TextMessage(direction.name()));
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        logger.error("error occured at sender " + session, throwable);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info(String.format("Session %s closed because of %s", session.getId(), status.getReason()));
        clients.remove(session.getId());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Connected ... " + session.getId());
        TankClient tankClient = clients.get(session.getId());
        if (tankClient != null) {
            tankClient.getSession().close(CloseStatus.GOING_AWAY);
        }
        tankClient = new TankClient(session, TankClient.Direction.UP, false);
        clients.put(session.getId(), tankClient);
    }

}

@Data
@AllArgsConstructor
class TankClient {
    public enum Direction {UP,DOWN,RIGHT,LEFT};

    private WebSocketSession session;
    private Direction direction;
    private boolean autorun = false;
}

class TankRunner implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(DateTimeHandler.class);

    private Map<String, TankClient> clients;
    private final boolean endless = true;

    public TankRunner (Map<String, TankClient> clients) {
        this.clients = clients;
    }

    @Override
    public void run () {
        while(!Thread.currentThread().isInterrupted()) {
            clients.forEach((String id, TankClient tankClient) -> {
                if (tankClient.isAutorun()) {
                    try {
                        tankClient.getSession().sendMessage(new TextMessage(tankClient.getDirection().name()));
                    } catch (IOException ex) {
                        logger.error("send message: " + ex.getMessage(), ex);
                    }
                }
            });
            try {Thread.sleep(500L);} catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

}