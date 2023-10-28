//정용준: 이 코드는 WebSocket을 구성하고 웹 소켓 핸들러를 등록하여 /chating/{roomNumber}와 같은 URL로
//       웹 소켓 연결을 처리합니다. 이것을 통해 실시간 통신 및 채팅과 같은 웹 소켓 기능을 구현할 수 있게 됩니다.

package com.momento.config;

import com.momento.handler.SocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

    @Autowired
    SocketHandler socketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler, "/chating/{roomNumber}");
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(50000000);
        container.setMaxBinaryMessageBufferSize(50000000);
        return container;
    }
}