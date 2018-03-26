package org.noahnyy.demo.websocket.config;

import org.noahnyy.demo.websocket.handler.WebSocketErrorHandler;
import org.noahnyy.demo.websocket.listener.WebSocketConnectListener;
import org.noahnyy.demo.websocket.listener.WebSocketDisconnectListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

/**
 * WebSocket 配置
 *
 * @author niuyy
 * @date 2018/3/23
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.setErrorHandler(this.webSocketHandler())
                .addEndpoint("/endpointNiu")
                .setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setUserDestinationPrefix("/user");
    }

    /**
     * WebSocket Error 处理
     *
     * @return WebSocket Error 处理器
     */
    @Bean
    public StompSubProtocolErrorHandler webSocketHandler() {
        return new WebSocketErrorHandler();
    }
}