package org.cs.mazetanks.web;

//import org.eclipse.jetty.websocket.api.WebSocketBehavior;
//import org.eclipse.jetty.websocket.api.WebSocketPolicy;
//import org.eclipse.jetty.websocket.server.WebSocketServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.ExceptionWebSocketHandlerDecorator;
//import org.springframework.web.socket.server.jetty.JettyRequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * Created by aad on 06.02.2017.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ExceptionWebSocketHandlerDecorator(tankControlHandler()), "/pipe")
                .addHandler(new ExceptionWebSocketHandlerDecorator(dateTimeHandler()), "/date-time")
                .setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor());
                // .setHandshakeHandler(handshakeHandler());
    }

    @Bean
    public WebSocketHandler dateTimeHandler() {
        return new DateTimeHandler();
    }

    @Bean
    public WebSocketHandler tankControlHandler() {
        return new TankControlHandler();
    }

    // Tomcat, WildFly, and GlassFish
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        container.setMaxSessionIdleTimeout(600_000L);
        return container;
    }


    /*
    // for jetty
    @Bean
    public DefaultHandshakeHandler handshakeHandler() {
        WebSocketPolicy policy = new WebSocketPolicy(WebSocketBehavior.SERVER);
        policy.setInputBufferSize(8192);
        policy.setIdleTimeout(600_000);
        return new DefaultHandshakeHandler(
                new JettyRequestUpgradeStrategy(new WebSocketServerFactory(policy)));
    }
    */

}
